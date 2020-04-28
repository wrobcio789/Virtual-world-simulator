from Application.Organisms.Animals.Animal import Animal
from Application.Utilities import Colors


class Wolf(Animal):
    STRENGTH = 9
    INITIATIVE = 5

    def __init__(self, position, world, **kwargs):
        super().__init__(Wolf.STRENGTH, Wolf.INITIATIVE, position, world, **kwargs)

    @staticmethod
    def __str__():
        return "Wolf"

    def GetColor(self):
        return Colors.GRAY

    def GetLetter(self):
        return "W"
