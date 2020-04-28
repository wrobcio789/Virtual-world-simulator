from Application.Organisms.Animals.Animal import Animal
from Application.Utilities import Colors


class Sheep(Animal):

    STRENGTH = 4
    INITIATIVE = 4

    def __init__(self, position, world, **kwargs):
        super().__init__(Sheep.STRENGTH, Sheep.INITIATIVE, position, world, **kwargs)

    @staticmethod
    def __str__():
        return "Sheep"

    def GetColor(self):
        return Colors.WHITE

    def GetLetter(self):
        return "O"

