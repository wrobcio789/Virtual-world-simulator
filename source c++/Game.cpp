#include "Game.h"



Game::Game()
	:	world_(1, 1, console_),
		turnsCount_(0),
		shouldEnd_(false)
{
	console_.SetCursorVisibility(false);
}

Game::~Game()
{
	console_.ClearScreen();
}

bool Game::Run()
{
	Init();
	Display();

	shouldEnd_ = false;
	while (!shouldEnd_)
	{
		Input();
		Display();
	}

	return true;
}

void Game::Input()
{
	while (true)
	{
		char c = _getch();
		switch (c)
		{
		case 't':
			NextTurn();
			return;
		case 's':
			Save();
			return;
		case 'q':
			shouldEnd_ = true;
			return;
		case 'l':
			Load();
			return;
		}
	}
}

void Game::Init()
{
	console_.SetCursorVisibility(true);
	int width, height;
	console_.Print("Podaj szerokosc swiata: ");
	console_.Read(width);
	console_.ClearEol();

	console_.Print("Podaj wysokosc swiata: ");
	console_.Read(height);
	console_.ClearEol();

	int animals, plants;
	do {
		console_.Print("Podaj liczbe zwierzat: ");
		console_.Read(animals);
		console_.ClearEol();

		console_.Print("Podaj liczbe roslin: ");
		console_.Read(plants);
		console_.ClearEol();
	} while (animals + plants > width * height);

	console_.ClearScreen();
	world_.Reset(width, height);
	world_.SpawnOrganisms(animals, plants);

	console_.SetCursorVisibility(false);
}

void Game::NextTurn()
{
	turnsCount_++;
	world_.AddToLogs("RUNDA NR " + std::to_string(turnsCount_));
	world_.Act();
}

void Game::Display()
{
	console_.ClearScreen();
	world_.Draw();
	console_.GoToXY(constants::SIGNATURE_X, constants::SIGNATURE_Y);
	console_.Print("Virtual world console version");
}

void Game::Save()
{
	std::string filename = WriteAndRead("Podaj nazwe pliku do zapisu: ");
	Saver saver(filename + ".vwsave");
	saver.InsertPrefixAndGo("#TURN");
	saver.Push(turnsCount_);
	world_.Save(saver);
}

void Game::Load()
{
	Loader loader;
	while (loader.OpenFile(WriteAndRead("Podaj nazwe pliku do wczytania: ") + ".vwsave") == false);
	loader.GoTo("#TURN");
	turnsCount_ = loader.NextInt();
	world_.Load(loader);
}

std::string Game::WriteAndRead(const std::string & text)
{
	console_.SetCursorVisibility(true);

	console_.GoToXY(constants::WRITE_X, constants::WRITE_Y);
	console_.Print(text);

	std::string input;
	console_.Read(input);

	console_.GoToXY(constants::WRITE_X, constants::WRITE_Y);
	console_.ClearEol();

	console_.SetCursorVisibility(false);
	return input;
}
