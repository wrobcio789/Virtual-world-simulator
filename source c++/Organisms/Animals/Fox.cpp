#include "Fox.h"



Fox::Fox(int x, int y, World & world)
	: Animal(constants::FOX_STRENGTH, constants::FOX_INITIATIVE, x, y, world)
{
}

Fox::Fox(Loader & loader, World & world)
	:	Animal(loader, world)
{
}

void Fox::Act()
{
	struct Cmp{
		int strength_;

		Cmp(int strength)
			:	strength_(strength)
		{}

		bool operator()(const Organism* organism)
		{
			if (organism == nullptr)
				return true;
			return organism->GetStrength() <= strength_;
		}
	};

	MoveInRange(1, Cmp(GetStrength()));
}

bool Fox::Copulate(Organism & organism)
{
	return CopulateTemplate<Fox>(organism);
}

std::string Fox::ToString()
{
	return "Lis";
}

char Fox::GetLetter()
{
	return 'L';
}


Fox::~Fox()
{
}
