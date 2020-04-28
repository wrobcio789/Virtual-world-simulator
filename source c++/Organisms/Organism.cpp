#include "Organism.h"

int Organism::instancesCount_ = 0;

Organism::Organism(int strength, int initiative, int x, int y, World& world)
	:	strength_(strength),
		initiative_(initiative),
		position_(x, y),
		world_(world),
		isAlive_(true)
{
	instanceNumber_ = ++instancesCount_;
}

Organism::Organism(Loader & loader, World & world)
	:	world_(world)

{
	position_.x = loader.NextInt();
	position_.y = loader.NextInt();
	isAlive_ = loader.NextInt();
	instanceNumber_ = loader.NextInt();
	strength_ = loader.NextInt();
	initiative_ = loader.NextInt();

	instancesCount_ = std::max(instancesCount_, instanceNumber_);
}


Organism::~Organism()
{
}

void Organism::Save(Saver & saver)
{
	saver.Push(position_.x);
	saver.Push(position_.y);
	saver.Push(isAlive_);
	saver.Push(instanceNumber_);
	saver.Push(strength_);
	saver.Push(initiative_);

}

int Organism::GetStrength() const
{
	return strength_;
}

int Organism::GetInitiative() const
{
	return initiative_;
}

int Organism::GetInstanceNumber() const
{
	return instanceNumber_;
}

int Organism::GetX() const
{
	return position_.x;
}

int Organism::GetY() const
{
	return position_.y;
}

bool Organism::isAlive() const
{
	return isAlive_;
}

utils::Point Organism::GetPosition() const
{
	return position_;
}

void Organism::SetStrength(int strength)
{
	strength_ = strength;
}

void Organism::SetInitiative(int initiative)
{
	initiative_ = initiative;
}

void Organism::Draw(Console & console)
{
	console.Print(GetLetter());
}

World& Organism::GetWorld()
{
	return world_;
}

void Organism::SetPosition(utils::Point position)
{
	position_ = position;
}

void Organism::Die()
{
	isAlive_ = false;
	world_.AddToRemovalSet(position_);
}
