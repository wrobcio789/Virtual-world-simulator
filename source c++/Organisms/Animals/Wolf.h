#pragma once
#include "Animal.h"


namespace constants
{
	const int WOLF_STRENGTH = 9;
	const int WOLF_INITIATIVE = 5;
}

class Wolf : public Animal
{
public:
	Wolf(int x, int y, World & world);
	Wolf(Loader & loader, World & world);
	virtual ~Wolf() override;

	virtual std::string ToString() override;
	virtual char GetLetter() override;


protected:
	virtual bool Copulate(Organism & organism) override;
};

