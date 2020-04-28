#include "Console.h"



Console::Console()
	:	handle_(GetStdHandle(STD_OUTPUT_HANDLE))
{
}


Console::~Console()
{
	GoToXY(0, 0);
}

void Console::PrintLine(int x, int y, std::string text)
{
	GoToXY(x, y);
	std::cout << text;
}

void Console::ClearScreen()
{
	system("cls");
	GoToXY(0, 0);
}

void Console::ClearEol()
{
	int x = WhereX();
	CONSOLE_SCREEN_BUFFER_INFO info;
	GetConsoleScreenBufferInfo(handle_, &info);
	int width = info.srWindow.Right - info.srWindow.Left + 1;
	for (int i = WhereX(); i < width; i++)
		std::cout << ' ';
	GoToXY(x, WhereY());
}

void Console::ClearLine(int y)
{
	GoToXY(0, y);
	ClearEol();
}

COORD Console::GetCursorCoord()
{
	CONSOLE_SCREEN_BUFFER_INFO screenBufferInfo;
	HANDLE hStd = GetStdHandle(STD_OUTPUT_HANDLE);
	GetConsoleScreenBufferInfo(hStd, &screenBufferInfo);
	return screenBufferInfo.dwCursorPosition;
}

void Console::GoToXY(int x, int y)
{
	COORD coord;
	coord.X = x;
	coord.Y = y;
	SetConsoleCursorPosition(handle_, coord);
}

void Console::SetCursorVisibility(bool visibility)
{
	CONSOLE_CURSOR_INFO info;
	info.dwSize = 10;
	info.bVisible = visibility;
	SetConsoleCursorInfo(handle_, &info);
}

void Console::Print(const std::string & text)
{
	std::cout << text;
}

void Console::Print(const char character)
{
	std::cout << character;
}

int Console::WhereX()
{
	return GetCursorCoord().X;
}

int Console::WhereY()
{
	return GetCursorCoord().Y;
}
