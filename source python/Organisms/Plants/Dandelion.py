from Application.Organisms.Plants.Plant import Plant
from Application.Utilities import Colors


class Dandelion(Plant):

    STRENGTH = 0
    SPREAD_TRIES = 3

    def __init__(self, position, world, **kwargs):
        super().__init__(Dandelion.STRENGTH, position, world, **kwargs)

    def Act(self):
        for i in range(Dandelion.SPREAD_TRIES):
            self._Spread()

    @staticmethod
    def __str__():
        return "Dandelion"

    def GetColor(self):
        return Colors.YELLOW

    def GetLetter(self):
        return "M"
