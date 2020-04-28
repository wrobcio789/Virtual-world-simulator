#pragma once
#include "Animal.h"

namespace constants
{
	const int FOX_STRENGTH = 3;
	const int FOX_INITIATIVE = 4;
}

class Fox :	public Animal
{
public:
	Fox(int x, int y, World & world);
	Fox(Loader & loader, World & world);
	virtual ~Fox() override;

	virtual void Act() override;
	virtual std::string ToString() override;
	virtual char GetLetter() override;

protected:
	virtual bool Copulate(Organism & organism) override;
};

