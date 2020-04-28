#include "OrganismsManager.h"



OrganismsManager::OrganismsManager()
{
}


OrganismsManager::~OrganismsManager()
{
}

Plant* OrganismsManager::CreatePlant(int plantId, int x, int y, World & world)
{
	switch (plantId)
	{
	case BELLADONNA:
		return new Belladonna(x, y, world);
	case DANDELION:
		return new Dandelion(x, y, world);
	case GRASS:
		return new Grass(x, y, world);
	case SOSNOWSKYS_HOGWEED:
		return new SosnowskysHogweed(x, y, world);
	case GUARANA:
		return new Guarana(x, y, world);
	case NO_ORGANISM:
		return nullptr;
	}

	return nullptr;
}

Animal* OrganismsManager::CreateAnimal(int animalId, int x, int y, World & world)
{
	switch (animalId)
	{
	case ANTELOPE:
		return new Antelope(x, y, world);
	case CYBER_SHEEP:
		return new CyberSheep(x, y, world);
	case FOX:
		return new Fox(x, y, world);
	case HUMAN:
		return new Human(x, y, world);
	case SHEEP:
		return new Sheep(x, y, world);
	case TURTLE:
		return new Turtle(x, y, world);
	case WOLF:
		return new Wolf(x, y, world);
	case NO_ORGANISM:
		return nullptr;
	}

	return nullptr;
}

Organism* OrganismsManager::LoadOrganism(Loader & loader, World & world)
{
	int id = loader.NextInt();
	
	switch (id)
	{
	case NO_ORGANISM:
		return nullptr;
	case ANTELOPE:
		return new Antelope(loader, world);
	case CYBER_SHEEP:
		return new CyberSheep(loader, world);
	case FOX:
		return new Fox(loader, world);
	case HUMAN:
		return new Human(loader, world);
	case SHEEP:
		return new Sheep(loader, world);
	case TURTLE:
		return new Turtle(loader, world);
	case WOLF:
		return new Wolf(loader, world);
	case BELLADONNA + constants::PLANTS_ID_SHIFT:
		return new Belladonna(loader, world);
	case DANDELION + constants::PLANTS_ID_SHIFT:
		return new Dandelion(loader, world);
	case GRASS + constants::PLANTS_ID_SHIFT:
		return new Grass(loader, world);
	case SOSNOWSKYS_HOGWEED + constants::PLANTS_ID_SHIFT:
		return new SosnowskysHogweed(loader, world);
	case GUARANA + constants::PLANTS_ID_SHIFT:
		return new Guarana(loader, world);
	}

	return nullptr;
}

void OrganismsManager::SaveOrganism(Saver & saver, Organism* organism)
{
	if (organism == nullptr)
	{
		saver.Push(NO_ORGANISM);
		return;
	}
	else if (dynamic_cast<Antelope*>(organism))
		saver.Push(ANTELOPE);
	else if (dynamic_cast<CyberSheep*>(organism))
		saver.Push(CYBER_SHEEP);
	else if (dynamic_cast<Fox*>(organism))
		saver.Push(FOX);
	else if (dynamic_cast<Human*>(organism))
		saver.Push(HUMAN);
	else if (dynamic_cast<Sheep*>(organism))
		saver.Push(SHEEP);
	else if (dynamic_cast<Turtle*>(organism))
		saver.Push(TURTLE);
	else if (dynamic_cast<Wolf*>(organism))
		saver.Push(WOLF);
	else if (dynamic_cast<Belladonna*>(organism))
		saver.Push(BELLADONNA + constants::PLANTS_ID_SHIFT);
	else if (dynamic_cast<Dandelion*>(organism))
		saver.Push(DANDELION + constants::PLANTS_ID_SHIFT);
	else if (dynamic_cast<Grass*>(organism))
		saver.Push(GRASS + constants::PLANTS_ID_SHIFT);
	else if (dynamic_cast<Guarana*>(organism))
		saver.Push(GUARANA + constants::PLANTS_ID_SHIFT);
	else if (dynamic_cast<SosnowskysHogweed*>(organism))
		saver.Push(SOSNOWSKYS_HOGWEED + constants::PLANTS_ID_SHIFT);
	else
		return;

	organism->Save(saver);
	saver.Push('\n');
}

int OrganismsManager::getAnimalsNumber()
{
	return 7;
}

int OrganismsManager::getPlantsNumber()
{
	return 5;
}


