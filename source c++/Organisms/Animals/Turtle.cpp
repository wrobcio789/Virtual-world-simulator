#include "Turtle.h"
#include "../Plants/Plant.h"



Turtle::Turtle(int x, int y, World & world)
	: Animal(constants::TURTLE_STRENGTH, constants::TURTLE_INITIATIVE, x, y, world)
{
}

Turtle::Turtle(Loader & loader, World & world)
	:	 Animal(loader, world)
{
}


void Turtle::Act()
{
	if (Randomizer::GetChance(constants::CHANCE_TO_NOTHING))
		return;
	MoveInRange(1);
}

bool Turtle ::Copulate(Organism & organism)
{
	return CopulateTemplate<Turtle>(organism);
}

Animal::ColisionResult Turtle::Collide(Organism & organism)
{
	if (dynamic_cast<Plant*>(&organism) != nullptr || organism.GetStrength() >= 5)
		return FIGHT;

	return STOP;
}

std::string Turtle::ToString()
{
	return "Zolw";
}

char Turtle::GetLetter()
{
	return 'Z';
}

Turtle::~Turtle()
{
}
