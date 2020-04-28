#include "Guarana.h"



Guarana::Guarana(int x, int y, World & world)
	:	 Plant(constants::GUARANA_STRENGTH, x, y, world)
{
}

Guarana::Guarana(Loader & loader, World & world)
	:	Plant(loader, world)
{
}

Plant::ColisionResult Guarana::Collide(Organism & organism)
{
	organism.SetStrength(organism.GetStrength() + constants::GUARANA_ADDED_STRENGTH);
	return PLANT;
}

std::string Guarana::ToString()
{
	return "Guarana";
}

char Guarana::GetLetter()
{
	return 'g';
}


Guarana::~Guarana()
{
}


bool Guarana::Spread()
{
	return SpreadTemplate<Guarana>();
}
