#pragma once
#include "Organisms.h"


namespace constants
{
	const int PLANTS_ID_SHIFT = 1000;
}

enum ORGANISMS
{
	HUMAN = 0,
	CYBER_SHEEP = 1,
	FOX = 2,
	ANTELOPE = 3,
	SHEEP = 4,
	TURTLE = 5,
	WOLF = 6,

	BELLADONNA = 0,
	DANDELION = 1,
	GRASS = 2,
	GUARANA = 3,
	SOSNOWSKYS_HOGWEED = 4,

	NO_ORGANISM = 2000
};

class World;

class OrganismsManager
{
public:
	OrganismsManager();                                                                          
	~OrganismsManager();

	Plant* CreatePlant(int organismId, int x, int y, World & world);
	Animal* CreateAnimal(int animalId, int x, int y, World & world);
	Organism* LoadOrganism(Loader & loader, World & world);
	void SaveOrganism(Saver & saver, Organism* organism);

	int getAnimalsNumber();
	int getPlantsNumber();
};

