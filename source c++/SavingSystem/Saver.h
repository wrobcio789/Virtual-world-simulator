#pragma once
#include <string>
#include <fstream>

class Saver
{
public:
	Saver(const std::string & filepath);
	~Saver();

	void InsertPrefixAndGo(const std::string & prefix);
	template <typename T> void Push(const T & data);

private:
	std::ofstream file_;
};

template<typename T>
inline void Saver::Push(const T & data)
{
	file_ << ' ' << data;
}
