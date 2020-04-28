#pragma once
#include <climits>
#include <utility>
#include <algorithm>
#include <vector>
#include <queue>
#include <set>
#include "Utilities/Utilities.h"
#include "Utilities/Randomizer.h"
#include "SavingSystem/Loader.h"
#include "SavingSystem/Saver.h"
#include "LogSystem.h"
#include "Console.h"


namespace constants
{
	const int WORLD_LOGS_LENGTH = 20;

	const char WORLD_BORDER_CHARACTER = '#';
	const int BOARD_CORNER_X = 4;
	const int BOARD_CORNER_Y = 3;
	const int LOGS_X = 4;
	const int LOGS_Y = 3;
}

class Organism;

class World
{
public:
	World(int width, int height, Console & drawingBoard_);
	~World();

	bool IsInBoundary(const utils::Point position) const;
	void MoveOrganism(int fromX, int fromY, int toX, int toY);
	Organism* GetOrganism(const utils::Point & position);
	const Organism* GetOrganism(const utils::Point & position) const;
	void AddToLogs(const std::string & message);
	void AddToRemovalSet(const utils::Point & position);
	void SpawnOrganisms(int animals, int plants);
	

	void Draw();
	void Save(Saver & saver);
	void Load(Loader & loader);
	void Act();
	void Reset(int width, int height);

	template <typename Cmp> bool FindNearestPlace(const utils::Point & position, utils::Point & result, Cmp requirements) const;
	template <typename Cmp> bool GetRandomPlaceInRange(const utils::Point & position, utils::Point & result, int range, Cmp requirements) const;
	template <typename OrganismType> bool CreateOrganism(const utils::Point & position);


private:
	int width_;
	int height_;
	Organism*** organisms_;
	Console & drawingBoard_;
	LogSystem logSystem_;
	std::set<Organism*> RemovalSet_; 

	std::vector<Organism*> GetElementsSortedByInitiaitive();
	void EmptyRemovalSet();
	void DisplayLogs();

};

template<typename Cmp>
bool World::FindNearestPlace(const utils::Point & position, utils::Point & result, Cmp requirements) const
{
	result = utils::Point(-1, -1);
	int lowestDistance = INT_MAX;

	for (int i = 0; i < height_; i++)
		for (int j = 0; j < width_; j++)
		{
			if (position == utils::Point(j, i))
				continue;

			int currentDistance;
			if (requirements(reinterpret_cast<const Organism*>(organisms_[i][j]))
				&& ((currentDistance = position.Distance(utils::Point(j, i))) < lowestDistance))
			{
				lowestDistance = currentDistance;
				result = utils::Point(j, i);
			}
		}



	if (result == utils::Point(-1, -1))
		return false;
	return true;
}


template<typename Cmp>
bool World::GetRandomPlaceInRange(const utils::Point & position, utils::Point & result, int range, Cmp requirements) const
{
	utils::Point nearestPlace(-1, -1);
	if (FindNearestPlace(position, nearestPlace, requirements) == false
		|| position.Distance(nearestPlace) > range)	
		return false;	//Nie ma siê gdzie poruszyæ

	
	do
	{
		int rangeTmp = range;
		result.x = Randomizer::GetInt(-rangeTmp, rangeTmp);
		rangeTmp -= utils::abs(result.x);
		result.y = Randomizer::GetInt(-rangeTmp, rangeTmp);
		result = position + result;
	} while ((!IsInBoundary(result) || !requirements(GetOrganism(result)))
			|| result == position);

	return true;
}

template<typename OrganismType>
inline bool World::CreateOrganism(const utils::Point & position)
{
	if (GetOrganism(position) == nullptr)
	{
		organisms_[position.y][position.x] = new OrganismType(position.x, position.y, *this);
		return true;
	}
	return false;
}
