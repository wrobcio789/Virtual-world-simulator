#include "LogSystem.h"



LogSystem::LogSystem(int logMaxLength)
	:	logs_(new std::string[logMaxLength]),
		logsLength_(0),
		logsMaxLength_(logMaxLength)
{
}


LogSystem::~LogSystem()
{
	delete[] logs_;
}

void LogSystem::Log(const std::string & message)
{
	if (logsLength_ == logsMaxLength_)
	{
		for (int i = 1; i < logsLength_; i++)
			logs_[i - 1] = std::move(logs_[i]);
		logs_[logsMaxLength_ - 1] = message;
	}
	else
		logs_[logsLength_++] = message;
}


void LogSystem::Display(int x, int y, Console & console)
{
	for (int i = 0; i < logsLength_; i++)
	{
		console.GoToXY(x, y + i);
		console.ClearEol();
		console.Print(logs_[i]);
	}
}

void LogSystem::Clear()
{
	logsLength_ = 0;
}
