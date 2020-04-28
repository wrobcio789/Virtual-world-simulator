#include "Wolf.h"


Wolf::Wolf(int x, int y, World & world)
	:Animal(constants::WOLF_STRENGTH, constants::WOLF_INITIATIVE, x, y, world)
{
}

Wolf::Wolf(Loader & loader, World & world)
	:	Animal(loader, world)
{
}

bool Wolf::Copulate(Organism & organism)
{
	return CopulateTemplate<Wolf>(organism);
}

std::string Wolf::ToString()
{
	return "Wilk";
}

char Wolf::GetLetter()
{
	return 'W';
}

Wolf::~Wolf()
{
}
