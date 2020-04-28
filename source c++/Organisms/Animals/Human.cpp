#include "Human.h"



Human::Human(int x, int y, World & world)
	:	Animal(constants::HUMAN_STRENGTH, constants::HUMAN_INITIATIVE, x, y, world),
		turnsToWaitForAbility_(0),
		abilityTurnsCount_(constants::HUMAN_ABILITY_WAITING_TIME + 1)
{
}

Human::Human(Loader & loader, World & world)
	:	Animal(loader, world)
{
	abilityTurnsCount_ = loader.NextInt();
	turnsToWaitForAbility_ = loader.NextInt();
}

void Human::Save(Saver & saver)
{
	Animal::Save(saver);
	saver.Push(abilityTurnsCount_);
	saver.Push(turnsToWaitForAbility_);
}


void Human::Act()
{

	int moves = 1;
	if (abilityTurnsCount_ < constants::HUMAN_ABILITY_FIRST_PHASE_LASTING_TIME 
		|| (abilityTurnsCount_ < constants::HUMAN_ABILITY_SECOND_PHASE_LASTING_TIME && Randomizer::GetChance(constants::HUMAN_ABILITY_SECOND_PHASE_CHANCE)))
		moves = constants::HUMAN_ABILITY_MOVES;

	int c;
	while (moves && isAlive())
	{
		c = _getch();
		if (c == 0 || c == 224)
		{
			c = _getch();
			switch (c)
			{
			case 72:	//Arrow up
				Move(0, -1);
				moves--;
				break;

			case 75:	//Arrow left
				Move(-1, 0);
				moves--;
				break;

			case 77:	//Arrow right
				Move(1, 0);
				moves--;
				break;

			case 80:	//Arrow down
				Move(0, 1);
				moves--;
				break;
			}
		}

		if (c == 's' && ActivateSpecialAbility())
			moves += constants::HUMAN_ABILITY_MOVES - 1;

		if (moves > 1 && isAlive())
			GetWorld().Draw();
	}

	IncreaseAbilityCounts();
}


bool Human::Copulate(Organism & organism)
{
	return CopulateTemplate<Human>(organism);
}

std::string Human::ToString()
{
	return "Czlowiek";
}

char Human::GetLetter()
{
	return 'H';
}

Human::~Human()
{
}

bool Human::ActivateSpecialAbility()
{
	if (turnsToWaitForAbility_ == 0)
	{
		abilityTurnsCount_ = 0;
		turnsToWaitForAbility_ = constants::HUMAN_ABILITY_WAITING_TIME - 1;

		GetWorld().AddToLogs(ToString() + " aktywowal specjalna umiejetnosc.");
		return true;
	}
	return false;
}

void Human::IncreaseAbilityCounts()
{
	turnsToWaitForAbility_ = std::max(turnsToWaitForAbility_ - 1, 0);
	abilityTurnsCount_++;
}
