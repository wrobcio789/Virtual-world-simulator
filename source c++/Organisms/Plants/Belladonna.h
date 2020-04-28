#pragma once
#include "Plant.h"


namespace constants
{
	const int BELLADONNA_STRENGTH = 99;
}

class Belladonna : public Plant
{
public:
	Belladonna(int x, int y, World & world);
	Belladonna(Loader & loader, World & world);
	virtual ~Belladonna() override;

	virtual char GetLetter() override;
	virtual std::string ToString() override;
	virtual ColisionResult Collide(Organism & organism) override;

protected:
	virtual bool Spread() override;
};

