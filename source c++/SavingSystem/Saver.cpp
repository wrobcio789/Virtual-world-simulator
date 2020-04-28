#include "Saver.h"




Saver::Saver(const std::string & filepath)
	:	file_(filepath)
{
	if (!file_.is_open())
		__debugbreak();
}

Saver::~Saver()
{
}

void Saver::InsertPrefixAndGo(const std::string & prefix)
{
	file_ << std::endl << prefix;
}
