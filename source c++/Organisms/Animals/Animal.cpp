#include "Animal.h"



Animal::Animal(int strength, int initiative, int x, int y, World& world)
	:	Organism(strength, initiative, x, y, world)
{
}

Animal::Animal(Loader & loader, World & world)
	:	Organism(loader, world)
{
}


Animal::~Animal()
{
}

void Animal::Act()
{
	MoveInRange(1);
}

Animal::ColisionResult Animal::Collide(Organism & organism)
{
	return FIGHT;
}

bool Animal::Fight(Organism & organism)
{
	if (dynamic_cast<Animal*>(&organism) != nullptr && this->GetStrength() < organism.GetStrength())
	{	
		GetWorld().AddToLogs("Zwierze " + organism.ToString() + " pokonuje " + ToString());
		Die();
		return false;
	}

	organism.Die();
	GetWorld().AddToLogs("Zwierze " + ToString() + " pokonuje/zjada " + organism.ToString());
	return true;
}

void Animal::GoTo(int x, int y)
{
	if (!GetWorld().IsInBoundary(utils::Point(x, y)))
		return;

	if (DetectColision(utils::Point(x, y)) == false)
		return;

	GetWorld().MoveOrganism(GetX(), GetY(), x, y);
	SetPosition(utils::Point(x, y));
}

void Animal::Move(int dx, int dy)
{
	GoTo(GetX() + dx, GetY() + dy);
}

bool Animal::DetectColision(const utils::Point position)
{
	Organism* organism = GetWorld().GetOrganism(position);
	if (organism == nullptr)
		return true;

	if (Copulate(*organism))
		return false;

	Animal::ColisionResult myColisionResult = Collide(*organism);
	Animal::ColisionResult organismsColisionResult = organism->Collide(*this);

	if (myColisionResult == ESCAPE)
	{
		GetWorld().AddToLogs("Zwierze " + ToString() + " ucieka przed walka z " + organism->ToString() + ".");
		return false;
	}

	if (organismsColisionResult == STOP || myColisionResult == STOP)
	{
		GetWorld().AddToLogs("Walka " + ToString() + " z " + organism->ToString() + " zostala powstrzymana.");
		return false;
	}

	if (organismsColisionResult == ESCAPE)
	{
		GetWorld().AddToLogs("Zwierze " + organism->ToString() + " ucieka przed walka z " + ToString() + ".");
		return true;
	}


	return Fight(*organism);
}
