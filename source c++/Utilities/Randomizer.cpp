#include "Randomizer.h"

int Randomizer::GetInt(int a, int b)
{
	return Rand() % (b - a + 1) + a;
}

bool Randomizer::GetBool()
{
	return Rand() % 2;
}

bool Randomizer::GetChance(int percent)
{
	return GetInt(1, 100) <= percent;
}


int Randomizer::Rand()
{
	struct Initializer
	{
		Initializer()
		{
			srand(time(0u));
		}
	};
	static Initializer initializerInstance;

	return rand();
}
