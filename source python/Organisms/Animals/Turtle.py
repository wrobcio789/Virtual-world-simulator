from Application.Organisms.Animals.Animal import Animal
from Application.Utilities import Colors
from Application.Organisms.Plants import Plant
from Application.Organisms.Organism import ColisionResult
import random


class Turtle(Animal):
    STRENGTH = 2
    INITIATIVE = 1
    CHANCE_TO_NOTHING = 75
    ENEMY_STRENGTH_TO_STOP = 4

    def __init__(self, position, world, **kwargs):
        super().__init__(Turtle.STRENGTH, Turtle.INITIATIVE, position, world, **kwargs)
    
    def Act(self):
        if random.randint(0, 99) < Turtle.CHANCE_TO_NOTHING:
            return
        super().Act()

    def Collide(self, organism):
        if (organism is not Plant) and organism.GetStrength() <= Turtle.ENEMY_STRENGTH_TO_STOP:
            return ColisionResult.STOP
        return ColisionResult.FIGHT

    @staticmethod
    def __str__():
        return "Turtle"

    def GetColor(self):
        return Colors.OLIVE

    def GetLetter(self):
        return "Ż"
