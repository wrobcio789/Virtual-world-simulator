#pragma once
#include "Plant.h"


namespace constants
{
	const int GUARANA_STRENGTH = 0;
	const int GUARANA_ADDED_STRENGTH = 3;
}

class Guarana :	public Plant
{
public:
	Guarana(int x, int y, World & world);
	Guarana(Loader & loader, World & world);
	virtual ~Guarana();


	virtual std::string ToString() override;
	virtual char GetLetter() override;
	virtual ColisionResult Collide(Organism & organism) override;


protected:
	virtual bool Spread() override;
};

