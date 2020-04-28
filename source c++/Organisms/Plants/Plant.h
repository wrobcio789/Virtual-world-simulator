#pragma once
#include "../Organism.h"
#include "../../World.h"

namespace constants
{
	const int CHANCE_TO_SPREAD = 8;
}

class Plant : public Organism
{
public:
	Plant(int strenght, int x, int y, World & world);
	Plant(Loader & loader, World & world);
	virtual ~Plant();

	virtual void Act() override;
	virtual ColisionResult Collide(Organism & organism) override;

protected:
	template <typename T> bool SpreadTemplate();
	virtual bool Spread() = 0;
};

template<typename T>
inline bool Plant::SpreadTemplate()
{
	if (!Randomizer::GetChance(constants::CHANCE_TO_SPREAD))
		return false;

	struct Cmp
	{
		bool operator()(const Organism * organism)
		{
			return organism == nullptr;
		}
	};

	utils::Point childPosition;
	if (!GetWorld().GetRandomPlaceInRange(GetPosition(), childPosition, 1, Cmp()))
		return true;

	GetWorld().CreateOrganism<T>(childPosition);
	GetWorld().AddToLogs("Roslina gatunku " + ToString() + " rozmnozyla sie.");
	return true;
}
