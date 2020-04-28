from Application.Organisms.Organism import *
from Application.Requirements import NoRequirements

class Animal(Organism):

    def __init__(self, strength, initiative, position, world, **kwargs):
        super().__init__(strength, initiative, position, world, **kwargs)

    def Act(self):
        self._MoveInNeighbourhood(NoRequirements())

    def Collide(self, organism):
        return ColisionResult.FIGHT

    def RunCollision(self, organism):   
        if organism is None:
            return True

        if self._Copulate(organism):
            return False

        myCollisionResult = self.Collide(organism)
        organismCollisionResult = organism.Collide(self)

        if myCollisionResult == ColisionResult.ESCAPE:
            print(str(self) + " runs from " + str(organism))
            return False    
        if myCollisionResult == ColisionResult.STOP or organismCollisionResult == ColisionResult.STOP:
            print("Fight beetwen " + str(self) + " and " + str(organism) + " has been stopped")
            return False
        if organismCollisionResult == ColisionResult.ESCAPE:
            print(str(organism) + " runs from " + str(self))
            return True
        
        return self.__Fight(organism)

    def _GoTo(self, position):
        self.GetWorld().MoveAnimal(self, position)

    def _Move(self, vector):
        self._GoTo(self.GetPosition() + vector)

    def _MoveInNeighbourhood(self, requirements):
        newPosition = self.GetWorld().GetRandomPlaceInNeighbourhood(self.GetPosition(), requirements)
        if newPosition is None:
            return False

        self._GoTo(newPosition)
        return True

    def _Copulate(self, organism):
        if type(self) is not type(organism):
            return False

        position = self.GetWorld().GetRandomPlaceInNeighbourhood(self.GetPosition(), NoRequirements())
        if position is None:
            return True
            
        self.GetWorld().CreateOrganism(position, self)
        return True

    def __Fight(self, organism):
        if isinstance(organism, Animal) and self.GetStrength() < organism.GetStrength():
            print(str(organism) + " defeats " + str(self))
            self.Die()
            return False

        print(str(self) + " defeats/eats " + str(organism))
        organism.Die()
        return True

    

