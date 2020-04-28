#pragma once
#include <algorithm>
#include "../Utilities/Utilities.h"
#include "../Utilities/Randomizer.h"
#include "../SavingSystem/Loader.h"
#include "../SavingSystem/Saver.h"
#include "../World.h"
#include "../Console.h"

class Organism
{
protected:

	enum ColisionResult
	{
		ESCAPE,
		STOP,
		FIGHT,
		PLANT
	};

public:
	Organism(int strength, int initiative, int x, int y, World & world);
	Organism(Loader & loader, World & world);
	virtual ~Organism();

	virtual void Save(Saver & saver);
	void Draw(Console & drawingBoard);
	virtual void Act() = 0;
	virtual ColisionResult Collide(Organism & organism) = 0;	
	void Die();
	void SetStrength(int strength);

	virtual std::string ToString() = 0;
	virtual char GetLetter() = 0;
	int GetStrength() const;
	int GetInitiative() const;
	int GetInstanceNumber() const;
	int GetX() const;
	int GetY() const;
	bool isAlive() const;
	utils::Point GetPosition() const;


protected:
	void SetInitiative(int initiative);
	World& GetWorld();
	void SetPosition(utils::Point position);

private:
	bool isAlive_;
	int instanceNumber_;
	int strength_;
	int initiative_;
	utils::Point position_;
	World& world_;

	static int instancesCount_;
};

