#pragma once
#include <conio.h>
#include "Animal.h"


namespace constants
{
	const int HUMAN_STRENGTH = 5;
	const int HUMAN_INITIATIVE = 4;
	const int HUMAN_ABILITY_FIRST_PHASE_LASTING_TIME = 3;
	const int HUMAN_ABILITY_SECOND_PHASE_LASTING_TIME = 5;
	const int HUMAN_ABILITY_SECOND_PHASE_CHANCE = 50;
	const int HUMAN_ABILITY_WAITING_TIME = 10;
	const int HUMAN_ABILITY_MOVES = 2;
}

class Human : public Animal
{
public:
	Human(int x, int y, World & world);
	Human(Loader & loader, World & world);
	virtual ~Human() override;

	virtual void Act() override;
	virtual void Save(Saver & saver) override;
	virtual std::string ToString() override;
	virtual char GetLetter() override;

protected:
	virtual bool Copulate(Organism & organism) override;

private:
	int abilityTurnsCount_;
	int turnsToWaitForAbility_;

	bool ActivateSpecialAbility();
	void IncreaseAbilityCounts();
};


