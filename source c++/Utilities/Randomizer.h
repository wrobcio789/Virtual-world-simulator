#pragma once
#include <cstdlib>
#include <ctime>

class Randomizer
{
public:
	static int GetInt(int a, int b);
	static bool GetBool();
	static bool GetChance(int percent);
private:
	static int Rand();
};

