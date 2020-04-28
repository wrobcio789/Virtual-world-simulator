#include "Plant.h"



Plant::Plant(int strength, int x, int y, World & world)
	:	Organism(strength, 0, x, y, world)
{
}

Plant::Plant(Loader & loader, World & world)
	:	Organism(loader, world)
{
}

void Plant::Act()
{
	Spread();
}

Plant::ColisionResult Plant::Collide(Organism & organism)
{
	return PLANT;
}

Plant::~Plant()
{
}
