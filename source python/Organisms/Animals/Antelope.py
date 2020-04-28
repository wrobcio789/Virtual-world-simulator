from Application.Organisms.Animals.Animal import Animal
from Application.Utilities import Colors
from Application.Organisms.Organism import ColisionResult
from Application.Requirements import NoRequirements
from Application.Organisms.Plants.Plant import Plant
import random


class Antelope(Animal):

    STRENGTH = 4
    INITIATIVE = 4
    CHANCE_TO_ESCAPE = 50
    MOVEMENT_RANGE = 2

    def __init__(self, position, world, **kwargs):
        super().__init__(Antelope.STRENGTH, Antelope.INITIATIVE, position, world, **kwargs)

    def Act(self):
        for i in range(Antelope.MOVEMENT_RANGE):
            self._MoveInNeighbourhood(NoRequirements())

    def Collide(self, organism):
        if isinstance(organism, Plant):
            return ColisionResult.FIGHT

        if random.randint(0, 99) < Antelope.CHANCE_TO_ESCAPE and self._MoveInNeighbourhood(NoRequirements()):
            return ColisionResult.ESCAPE
        return ColisionResult.FIGHT

    @staticmethod
    def __str__():
        return "Antelope"

    def GetColor(self):
        return Colors.BROWN

    def GetLetter(self):
        return "A"