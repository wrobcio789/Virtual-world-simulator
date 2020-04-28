#pragma once
#include "Animal.h"

namespace constants
{
	const int ANTELOPE_STRENGTH = 4;
	const int ANTELOPE_INITIATIVE = 4;
	const int ANTELOPE_MOVE_RANGE = 2;
	const int ANTELOPE_CHANCE_TO_ESCAPE = 50;
}

class Antelope : public Animal
{
public:
	Antelope(int x, int y, World & world);
	Antelope(Loader & loader, World & world);
	virtual ~Antelope() override;

	virtual void Act() override;
	virtual char GetLetter() override;
	virtual std::string ToString() override;
	virtual ColisionResult Collide(Organism & organism) override;

protected:
	virtual bool Copulate(Organism & organism) override;

};

