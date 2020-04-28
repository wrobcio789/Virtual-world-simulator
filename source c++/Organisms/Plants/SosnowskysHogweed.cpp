#include "SosnowskysHogweed.h"
#include "../Animals/CyberSheep.h"



SosnowskysHogweed::SosnowskysHogweed(int x, int y, World & world)
	:	Plant(constants::SOSNOWSKYSHOGWEED_STRENGTH, x, y, world)
{
}

SosnowskysHogweed::SosnowskysHogweed(Loader & loader, World & world)
	:	Plant(loader, world)
{
}


void SosnowskysHogweed::Act()
{
	const utils::Point moves[4] = { {-1, 0}, {0, 1}, {1, 0}, {0, -1} };
	for(int i = 0; i < 4; i++)
		if (GetWorld().IsInBoundary(moves[i] + GetPosition()))
		{
			Organism* organism = GetWorld().GetOrganism(GetPosition() + moves[i]);
			if (organism == nullptr || dynamic_cast<CyberSheep*>(organism) != nullptr
				|| dynamic_cast<SosnowskysHogweed*>(organism) != nullptr || dynamic_cast<Plant*>(organism) != nullptr)
				continue;
			organism->Die();
			GetWorld().AddToLogs("Obcowanie z barszczem Sosnowskiego wykancza zwierze " + organism->ToString() + ".");
		}

	Spread();
}

Plant::ColisionResult SosnowskysHogweed::Collide(Organism & organism)
{
	if (dynamic_cast<CyberSheep*>(&organism) == nullptr)
		organism.Die();
	return PLANT;
}

std::string SosnowskysHogweed::ToString()
{
	return "Barszcz Sosnowskiego";
}

char SosnowskysHogweed::GetLetter()
{
	return 'b';
}

SosnowskysHogweed::~SosnowskysHogweed()
{
}

bool SosnowskysHogweed::Spread()
{
	return SpreadTemplate<SosnowskysHogweed>();
}

