#pragma once
#include <string>
#include <fstream>

class Loader
{
public:
	Loader();
	Loader(const std::string & filepath);
	~Loader();

	bool IsOk();
	int NextInt();
	std::string NextString();
	bool OpenFile(const std::string & filepath);
	bool GoTo(const std::string & linePrefix);

private:
	std::ifstream file_;
};

