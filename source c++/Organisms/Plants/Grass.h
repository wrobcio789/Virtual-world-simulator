#pragma once
#include "Plant.h"


namespace constants
{
	const int GRASS_STRENGTH = 0;
}

class Grass : public Plant
{
public:
	Grass(int x, int y, World & world);
	Grass(Loader & loader, World & world);
	virtual ~Grass();

	virtual std::string ToString() override;
	virtual char GetLetter() override;

protected:
	virtual bool Spread() override;
};

