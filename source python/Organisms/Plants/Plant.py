from Application.Organisms.Organism import *
from Application import Requirements
import random


class Plant(Organism):

    CHANCE_TO_SPREAD = 8
    INITIATIVE = 0

    def __init__(self, strength, position, world, **kwargs):
        super().__init__(strength, Plant.INITIATIVE, position, world, **kwargs)

    def Collide(self, organism):
        return ColisionResult.PLANT

    def Act(self):
        self._Spread()

    def _Spread(self):

        if random.randint(0, 99) >= Plant.CHANCE_TO_SPREAD:
            return False

        position = self.GetWorld().GetRandomPlaceInNeighbourhood(self.GetPosition(), Requirements.NoRequirements())
        if position is None:
            return True

        self.GetWorld().CreateOrganism(position, self)
        return True

