from Application.Organisms.Plants.Plant import Plant
from Application.Organisms.Organism import ColisionResult
import Application.Organisms.Animals.CyberSheep
from Application.Utilities import Colors


class SosnowskysHogweed(Plant):

    STRENGTH = 10

    def __init__(self, position, world, **kwargs):
        super().__init__(SosnowskysHogweed.STRENGTH, position, world, **kwargs)

    def Collide(self, organism):
        if not isinstance(organism, Application.Organisms.Animals.CyberSheep.CyberSheep):
            print(str(organism) + " dies after contact with " + str(self))
            organism.Die()
        return ColisionResult.PLANT

    def Act(self):
        neighbours = self.GetPosition().GetAllNeighbours()
        for neighbour in neighbours:
            organism = self.GetWorld().GetOrganism(neighbour)
            if organism is not None and not isinstance(organism, Application.Organisms.Animals.CyberSheep.CyberSheep) and (not isinstance(organism, Plant)):
                organism.Die()
                print(str(organism) + " dies after contact with " + str(self))
        self._Spread()

    @staticmethod
    def __str__():
        return "Sosnowsky's hogweed"

    def GetColor(self):
        return Colors.MAGENTA

    def GetLetter(self):
        return "B"