#include "Belladonna.h"



Belladonna::Belladonna(int x, int y, World & world)
	:	Plant(constants::BELLADONNA_STRENGTH, x, y , world)
{
}

Belladonna::Belladonna(Loader & loader, World & world)
	:	Plant(loader, world)
{
}

Plant::ColisionResult Belladonna::Collide(Organism & organism)
{
	GetWorld().AddToLogs("Zwierze " + organism.ToString() + " umiera po zjedzeniu jagod.");
	organism.Die();
	return PLANT;
}

std::string Belladonna::ToString()
{
	return "Wilcze Jagody";
}

char Belladonna::GetLetter()
{
	return 'j';
}


Belladonna::~Belladonna()
{
}

bool Belladonna::Spread()
{
	return SpreadTemplate<Belladonna>();
}
