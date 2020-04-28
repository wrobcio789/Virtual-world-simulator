#include "Loader.h"


Loader::Loader()
{
}

Loader::Loader(const std::string & filepath)
	:	file_(filepath)
{
}

bool Loader::GoTo(const std::string & linePrefix)
{
	file_.seekg(0, std::ios::beg);

	std::string input;
	while (file_ >> input && input != linePrefix)
		file_.ignore(std::numeric_limits<std::streamsize>::max(), '\n');

	return input == linePrefix;
	
}

bool Loader::IsOk()
{
	return file_.is_open();
}

bool Loader::OpenFile(const std::string & filepath)
{
	if (file_.is_open())
		file_.close();
	file_.open(filepath);
	return file_.is_open();
}


int Loader::NextInt()
{
	int result;
	file_ >> result;
	return result;
}

std::string Loader::NextString()
{
	std::string result;
	file_ >> result;
	return result;
}

Loader::~Loader()
{
	if (file_.is_open())
		file_.close();
}
