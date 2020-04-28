from Application.Organisms.Plants.Plant import Plant
from Application.Organisms.Organism import ColisionResult
from Application.Utilities import Colors


class Guarana(Plant):

    STRENGTH = 0
    ADDED_STRENGTH = 3

    def __init__(self, position, world, **kwargs):
        super().__init__(Guarana.STRENGTH, position, world, **kwargs)

    def Collide(self, organism):
        organism.SetStrength(organism.GetStrength() + Guarana.ADDED_STRENGTH)
        return ColisionResult.PLANT

    @staticmethod
    def __str__():
        return "Guarana"

    def GetColor(self):
        return Colors.VIOLET

    def GetLetter(self):
        return "G"
