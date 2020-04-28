#pragma once
#include "Plant.h"


namespace constants
{
	const int SOSNOWSKYSHOGWEED_STRENGTH = 10;
}

class SosnowskysHogweed : public Plant
{
public:
	SosnowskysHogweed(int x, int y, World & world);
	SosnowskysHogweed(Loader & loader, World & world);
	virtual ~SosnowskysHogweed() override;

	virtual void Act() override;
	virtual ColisionResult Collide(Organism & organism) override;
	virtual std::string ToString() override;
	virtual char GetLetter() override;

protected:
	virtual bool Spread() override;
};

