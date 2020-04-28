#pragma once
#include <windows.h>
#include <iostream>
#include <string>
#include <locale.h>


class Console
{
public:
	Console();
	~Console();

	void Print(const std::string & text);
	void Print(const char character);
	void PrintLine(int x, int y, std::string text);
	void SetCursorVisibility(bool visibility);
	template <typename T> void Read(T & value);

	void ClearEol();
	void ClearScreen();

	int WhereX();
	int WhereY();
	void GoToXY(int x, int y);

private:
	HANDLE handle_;

	void ClearLine(int y);
	COORD GetCursorCoord();
};

template<typename T>
inline void Console::Read(T & value)
{
	std::cin >> value;
}
