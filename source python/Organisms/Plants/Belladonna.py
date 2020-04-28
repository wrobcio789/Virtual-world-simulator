from Application.Organisms.Plants.Plant import Plant
from Application.Organisms.Organism import ColisionResult
from Application.Utilities import Colors


class Belladonna(Plant):

    STRENGTH = 99

    def __init__(self, position, world, **kwargs):
        super().__init__(Belladonna.STRENGTH, position, world, **kwargs)

    def Collide(self, organism):
        organism.Die()
        print(str(organism) + " dies after eating " + str(self))
        return ColisionResult.PLANT

    @staticmethod
    def __str__():
        return "Belladonna"

    def GetColor(self):
        return Colors.RED

    def GetLetter(self):
        return "J"