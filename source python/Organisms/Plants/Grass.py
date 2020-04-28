from Application.Organisms.Plants.Plant import Plant
from Application.Utilities import Colors


class Grass(Plant):

    STRENGTH = 0

    def __init__(self, position, world, **kwargs):
        super().__init__(Grass.STRENGTH, position, world, **kwargs)

    @staticmethod
    def __str__():
        return "Grass"

    def GetColor(self):
        return Colors.GREEN

    def GetLetter(self):
        return "T"