#include "CyberSheep.h"
#include "../Plants/SosnowskysHogweed.h"


CyberSheep::CyberSheep(int x, int y, World & world)
	:	Sheep(constants::CYBERSHEEP_STRENGTH, constants::CYBERSHEEP_INITIATIVE, x, y, world)
{
}

CyberSheep::CyberSheep(Loader & loader, World & world)
	:	Sheep(loader, world)
{
}

void CyberSheep::Act()
{
	struct Cmp
	{
		bool operator()(const Organism* organism)
		{
			if (dynamic_cast<const SosnowskysHogweed*>(organism) != nullptr)
				return true;
			return false;
		}

	} cmp;

	utils::Point hogweedCoords;
	if (GetWorld().FindNearestPlace(GetPosition(), hogweedCoords, cmp) == false)
		return Sheep::Act();

	int dx = hogweedCoords.x - GetX();
	int dy = hogweedCoords.y - GetY();

	if (utils::abs(dx) >= utils::abs(dy))
		Move(dx > 0 ? 1 : -1, 0);
	else
		Move(0, dy > 0 ? 1 : -1);
}

bool CyberSheep::Copulate(Organism & organism)
{
	return CopulateTemplate<CyberSheep>(organism);
}

std::string CyberSheep::ToString()
{
	return "Cyber Owca";
}

char CyberSheep::GetLetter()
{
	return 'C';
}

CyberSheep::~CyberSheep()
{
}
