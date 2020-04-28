#pragma once
#include "Plant.h"

namespace constants
{
	const int DANDELION_STRENGTH = 0;
	const int DANDELION_SPREAD_TIMES = 3;
}

class Dandelion : public Plant
{
public:
	Dandelion(int x, int y, World & world);
	Dandelion(Loader & loader, World & world);
	virtual ~Dandelion() override;

	virtual void Act() override;
	virtual std::string ToString() override;
	virtual char GetLetter() override;

protected:
	virtual bool Spread() override;
};

