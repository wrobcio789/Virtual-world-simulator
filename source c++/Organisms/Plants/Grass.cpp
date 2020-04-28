#include "Grass.h"


Grass::Grass(int x, int y, World & world)
	:	Plant(constants::GRASS_STRENGTH, x, y, world)
{
}

Grass::Grass(Loader & loader, World & world)
	:	Plant(loader, world)
{
}

std::string Grass::ToString()
{
	return "Trawa";
}

char Grass::GetLetter()
{
	return 't';
}

Grass::~Grass()
{
}

bool Grass::Spread()
{
	return SpreadTemplate<Grass>();
}

