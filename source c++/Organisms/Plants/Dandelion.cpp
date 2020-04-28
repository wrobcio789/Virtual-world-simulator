#include "Dandelion.h"



Dandelion::Dandelion(int x, int y, World & world)
	:	Plant(constants::DANDELION_STRENGTH, x, y, world)
{
}

Dandelion::Dandelion(Loader & loader, World & world)
	:	Plant(loader, world)
{
}

void Dandelion::Act()
{
	for (int i = 0; i < constants::DANDELION_SPREAD_TIMES; i++)
		Spread();
}

std::string Dandelion::ToString()
{
	return "Mlecz";
}

char Dandelion::GetLetter()
{
	return 'm';
}


Dandelion::~Dandelion()
{
}

bool Dandelion::Spread()
{
	return SpreadTemplate<Dandelion>();
}
