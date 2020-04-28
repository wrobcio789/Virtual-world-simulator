#pragma once
#include "World.h"
#include "Organisms/OrganismsManager.h"



World::World(int width, int height, Console & drawingBoard)
	:	width_(width),
		height_(height),
		drawingBoard_(drawingBoard),
		organisms_(nullptr),
		logSystem_(constants::WORLD_LOGS_LENGTH)
{
	Reset(width, height);
}


World::~World()
{
	if (organisms_ == nullptr)
		return;

	for (int i = 0; i < height_; i++)
		for (int j = 0; j < width_; j++)
			if (organisms_[i][j] != nullptr)
				delete organisms_[i][j];

	for (int i = 0; i < height_; i++)
		delete[] organisms_[i];
	delete[] organisms_;
}

void World::Reset(int width, int height)
{
	if (organisms_ != nullptr)
	{
		for (int i = 0; i < height_; i++)
			for (int j = 0; j < width_; j++)
				if (organisms_[i][j] != nullptr)
					delete organisms_[i][j];

		for (int i = 0; i < height_; i++)
			delete[] organisms_[i];
		delete[] organisms_;
	}

	width_ = width;
	height_ = height;

	organisms_ = new Organism**[height];
	for (int i = 0; i < height; i++)
		organisms_[i] = new Organism*[width];

	for (int i = 0; i < height_; i++)
		for (int j = 0; j < width_; j++)
			organisms_[i][j] = nullptr;

	logSystem_.Clear();
}

bool World::IsInBoundary(const utils::Point position) const
{
	return position.x >= 0 && position.x < width_ && position.y >= 0 && position.y < height_;
}

void World::MoveOrganism(int fromX, int fromY, int toX, int toY)
{
	if (organisms_[toY][toX] != nullptr && !(fromX == toX && fromY == toY))
		AddToRemovalSet(utils::Point(toX, toY));

	utils::swap(organisms_[toY][toX], organisms_[fromY][fromX]);
}

Organism * World::GetOrganism(const utils::Point & position)
{
	return organisms_[position.y][position.x];
}

const Organism* World::GetOrganism(const utils::Point & position) const
{
	return reinterpret_cast<const Organism*>(organisms_[position.y][position.x]);
}

void World::AddToLogs(const std::string & message)
{
	logSystem_.Log(message);
}

void World::AddToRemovalSet(const utils::Point & position)
{
	RemovalSet_.insert(GetOrganism(position));
	organisms_[position.y][position.x] = nullptr;
}

void World::Draw()
{
	drawingBoard_.GoToXY(constants::BOARD_CORNER_X, constants::BOARD_CORNER_Y);
	for (int j = 0; j < width_ + 2; j++)
			drawingBoard_.Print(constants::WORLD_BORDER_CHARACTER);

	for (int i = 0; i < height_; i++)
	{
		drawingBoard_.GoToXY(constants::BOARD_CORNER_X, constants::BOARD_CORNER_Y + i + 1);
		drawingBoard_.Print(constants::WORLD_BORDER_CHARACTER);
		
		for (int j = 0; j < width_; j++)
			if (organisms_[i][j] == nullptr)
				drawingBoard_.Print(' ');
			else
				organisms_[i][j]->Draw(drawingBoard_);
		drawingBoard_.Print(constants::WORLD_BORDER_CHARACTER);	
	}

	drawingBoard_.GoToXY(constants::BOARD_CORNER_X, constants::BOARD_CORNER_Y + height_ + 1);
	for (int j = 0; j < width_ + 2; j++)
			drawingBoard_.Print(constants::WORLD_BORDER_CHARACTER);

	DisplayLogs();
}

void World::Save(Saver & saver)
{
	saver.InsertPrefixAndGo("#WIDTH");
	saver.Push(width_);
	saver.InsertPrefixAndGo("#HEIGHT");
	saver.Push(height_);
	saver.InsertPrefixAndGo("#ANIMALS");

	OrganismsManager manager;
	for (int i = 0; i < height_; i++)
		for (int j = 0; j < width_; j++)
			manager.SaveOrganism(saver, organisms_[i][j]);
		
}

void World::Load(Loader & loader)
{
	loader.GoTo("#WIDTH");
	int width = loader.NextInt();
	loader.GoTo("#HEIGHT");
	int height = loader.NextInt();

	Reset(width, height);

	loader.GoTo("#ANIMALS");
	OrganismsManager manager;
	for (int i = 0; i < height_; i++)
		for (int j = 0; j < width_; j++)
			organisms_[i][j] = manager.LoadOrganism(loader, *this);
}

void World::Act()
{
	std::vector<Organism*> organisms = GetElementsSortedByInitiaitive();
	auto it = organisms.begin();

	while (it != organisms.end())
	{
		Organism* organism = *it;
		if (organism != nullptr && RemovalSet_.find(organism) == RemovalSet_.end())
		{
			if (dynamic_cast<Human*>(organism))
			{
				AddToLogs("Kolej na ruch " + organism->ToString());
				Draw();
			}
			organism->Act();
		}
		it++;
	}

	EmptyRemovalSet();
}

void World::SpawnOrganisms(int animals, int plants)
{
	OrganismsManager factory;

	for (int i = 0; i < animals; i++)
	{
		int x, y;
		do {
			x = Randomizer::GetInt(0, width_ - 1);
			y = Randomizer::GetInt(0, height_ - 1);
		} while (organisms_[y][x] != nullptr);

		if (i == 0)	//Spawning human
		{
			organisms_[y][x] = factory.CreateAnimal(ORGANISMS::HUMAN, x, y, *this);
			continue;
		}
		
		int animal_index;
		do
			animal_index = Randomizer::GetInt(0, factory.getAnimalsNumber());
		while (animal_index == ORGANISMS::HUMAN);

		organisms_[y][x] = factory.CreateAnimal(animal_index, x, y, *this);
	}

	for (int i = 0; i < plants; i++)
	{
		int x, y;
		do {
			x = Randomizer::GetInt(0, width_ - 1);
			y = Randomizer::GetInt(0, height_ - 1);
		} while (organisms_[y][x] != nullptr);

		int plant_index = Randomizer::GetInt(0, factory.getPlantsNumber());
		organisms_[y][x] = factory.CreatePlant(plant_index, x, y, *this);
	}
}

std::vector<Organism*> World::GetElementsSortedByInitiaitive()
{
	std::vector<Organism*> result;
	for (int i = 0; i < height_; i++)
		for (int j = 0; j < width_; j++)
			if (organisms_[i][j] != nullptr)
				result.push_back(organisms_[i][j]);
	struct {
		bool operator()(Organism* a,Organism* b) const
		{
			if(a->GetInitiative() != b->GetInitiative())
				return a->GetInitiative() > b->GetInitiative();
			return a->GetInstanceNumber() > b->GetInstanceNumber();
		}
	} cmp;

	std::sort(result.begin(), result.end(), cmp);
	return result;
}

void World::EmptyRemovalSet()
{
	auto it = RemovalSet_.begin();
	while (it != RemovalSet_.end())
		if (*it != nullptr)
			delete *(it++);
	RemovalSet_.clear();
}

void World::DisplayLogs()
{
	logSystem_.Display(constants::BOARD_CORNER_X + constants::LOGS_X + width_, constants::LOGS_Y, drawingBoard_);
}