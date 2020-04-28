#pragma once
#include <string>
#include "Console.h"


class LogSystem
{
public:
	LogSystem(int logsMaxLength);
	~LogSystem();

	void Log(const std::string & message);
	void Display(int x, int y, Console & console);
	void Clear();

private:
	std::string* logs_;
	int logsLength_;
	int logsMaxLength_;
};

