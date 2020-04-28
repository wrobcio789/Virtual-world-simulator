#pragma once
#include "Console.h"
#include "World.h"
#include "Organisms/Organisms.h"
#include "LogSystem.h"

namespace constants
{
	const int SIGNATURE_X = 4;
	const int SIGNATURE_Y = 1;

	const int WRITE_X = 40;
	const int WRITE_Y = 1;
}

class Game
{

public:
	Game();
	bool Run();
	~Game();

private:
	Console console_;
	World world_;
	int turnsCount_;
	bool shouldEnd_;

	void Init();
	void Input();
	void NextTurn();
	void Display();
	void Save();
	void Load();
	std::string WriteAndRead(const std::string & text);
};

