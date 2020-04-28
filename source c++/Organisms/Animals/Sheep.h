#pragma once
#include "Animal.h"


namespace constants
{
	const int SHEEP_STRENGTH = 4;
	const int SHEEP_INITIATIVE = 4;
}

class Sheep : public Animal
{
public:
	Sheep(int x, int y, World & world);
	Sheep(Loader & loader, World & world);
	virtual ~Sheep() override;

	virtual std::string ToString() override;
	virtual char GetLetter() override;

protected:
	Sheep(int strength, int initiative, int x, int y, World& world);
	virtual bool Copulate(Organism & organism) override;
};

