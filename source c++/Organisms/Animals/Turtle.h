#pragma once
#include "Animal.h"


namespace constants
{
	const int TURTLE_STRENGTH = 2;
	const int TURTLE_INITIATIVE = 1;
	const int CHANCE_TO_NOTHING = 75;
}

class Turtle : public Animal
{
public:
	Turtle(int x, int y, World & world);
	Turtle(Loader & loader, World & world);
	virtual ~Turtle() override;

	virtual void Act() override;
	virtual ColisionResult Collide(Organism & organism) override;
	virtual std::string ToString() override;
	virtual char GetLetter() override;

protected:
	virtual bool Copulate(Organism & organism) override;
};

