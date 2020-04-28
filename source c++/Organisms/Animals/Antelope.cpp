#include "Antelope.h"
#include "../Plants/Plant.h"


Antelope::Antelope(int x, int y, World & world)
	: Animal(constants::ANTELOPE_STRENGTH, constants::ANTELOPE_INITIATIVE, x, y, world)
{
}

Antelope::Antelope(Loader & loader, World & world)
	:	Animal(loader, world)
{
}

void Antelope::Act()
{
	MoveInRange(constants::ANTELOPE_MOVE_RANGE);
}

std::string Antelope::ToString()
{
	return "Antylopa";
}

char Antelope::GetLetter()
{
	return 'A';
}

bool Antelope::Copulate(Organism & organism)
{
	return CopulateTemplate<Antelope>(organism);
}

Animal::ColisionResult Antelope::Collide(Organism & organism)
{
	if (dynamic_cast<Plant*>(&organism) != nullptr)
		return FIGHT;

	struct Cmp
	{
		bool operator() (const Organism * organism)
		{
			return organism == nullptr;
		}
	};

	if (Randomizer::GetChance(constants::ANTELOPE_CHANCE_TO_ESCAPE) && MoveInRange(1, Cmp()))
		return ESCAPE;
	return FIGHT;
}

Antelope::~Antelope()
{
}
