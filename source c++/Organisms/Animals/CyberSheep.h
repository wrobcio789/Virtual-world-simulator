#pragma once
#include "Sheep.h"


namespace constants
{
	const int CYBERSHEEP_STRENGTH = 11;
	const int CYBERSHEEP_INITIATIVE = 4;
}

class CyberSheep : public Sheep
{
public:
	CyberSheep(int x, int y, World & world);
	CyberSheep(Loader & loader, World & world);
	virtual ~CyberSheep() override;

	virtual void Act() override;
	virtual std::string ToString() override;
	virtual char GetLetter() override;

protected:
	virtual bool Copulate(Organism & organism) override;
};

