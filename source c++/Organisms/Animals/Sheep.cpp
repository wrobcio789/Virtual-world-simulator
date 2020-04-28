#include "Sheep.h"



Sheep::Sheep(int x, int y, World & world)
	: Animal(constants::SHEEP_STRENGTH, constants::SHEEP_INITIATIVE, x, y, world)
{
}

Sheep::Sheep(Loader & loader, World & world)
	:	Animal(loader, world)
{
}

bool Sheep::Copulate(Organism & organism)
{
	return CopulateTemplate<Sheep>(organism);
}

std::string Sheep::ToString()
{
	return "Owca";
}

char Sheep::GetLetter()
{
	return 'O';
}


Sheep::~Sheep()
{
}

Sheep::Sheep(int strength, int initiative, int x, int y, World & world)
	:Animal(strength, initiative, x, y, world)
{
}
