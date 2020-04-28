#pragma once
#include <typeinfo>
#include <cstdlib>
#include "../Organism.h"

class Animal : public Organism
{
public:
	Animal(int strength, int initiative, int x, int y, World& world);
	Animal(Loader & loader, World & world);
	virtual ~Animal() override;

	virtual void Act() override;
	virtual ColisionResult Collide(Organism & organism) override;

protected:
	void GoTo(int x, int y);
	void Move(int dx, int dy);

	virtual bool Copulate(Organism & organism) = 0;
	template <typename T> bool CopulateTemplate (Organism & organism);		//returns false if animals are not same type
	template <typename Cmp = utils::TrueCmp> bool MoveInRange(int range, Cmp requirements = utils::TrueCmp());

private:
	bool DetectColision(const utils::Point position);	//Returns true if Animal eventually can move to the position
	bool Fight(Organism & organism);

};

template<typename T>
inline bool Animal::CopulateTemplate(Organism & organism)
{
	if (typeid(*this) != typeid(organism))
		return false;

	struct Cmp
	{
		bool operator()(const Organism * organism)
		{
			return organism == nullptr;
		}
	};

	utils::Point childPosition;
	if (!GetWorld().GetRandomPlaceInRange(GetPosition(), childPosition, 1, Cmp())
		|| !GetWorld().GetRandomPlaceInRange(organism.GetPosition(), childPosition, 1, Cmp()))
		return true;

	GetWorld().CreateOrganism<T>(childPosition);
	GetWorld().AddToLogs("Zwierzeta gatunku " + ToString() + " kopuluja.");

	return true;
}

template<typename Cmp>
inline bool Animal::MoveInRange(int range, Cmp requirements)
{
	utils::Point newPosition(-1, -1);
	if(GetWorld().GetRandomPlaceInRange(GetPosition(), newPosition, range, requirements))
	{
		GoTo(newPosition.x, newPosition.y);
		return true;
	}

	return false;
}

