#pragma once
#define NOMINMAX
#include <utility>
#include <cmath>

namespace utils
{
	struct Point
	{
		int x, y;

		Point()
			:	x(0),
				y(0)
		{}

		Point(int x, int y)
			:	x(x),	
				y(y)
		{}

		int Distance(const Point & point) const		//Returns distance as abs(x) + abs(y)
		{
			return abs(x - point.x) + abs(y - point.y);
		}

		Point operator+(const Point & other) const
		{
			return Point(x + other.x, y + other.y);
		}

		bool operator==(const Point & other) const
		{
			return x == other.x && y == other.y;
		}
	};

	template <typename T>
	void swap(T& a, T& b)
	{
		T temp = std::move(a);
		a = std::move(b);
		b = std::move(temp);
	}


	template <typename T>
	T abs(T value)
	{
		return value < 0 ? -value : value;
	}

	struct TrueCmp
	{
		template <typename T>
		constexpr bool operator() (const T & ref)
		{
			return true;
		}
	};
}