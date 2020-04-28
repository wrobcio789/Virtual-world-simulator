from Application.Organisms.Animals.Animal import Animal
from Application.Utilities import Colors
from Application.Organisms.Plants.SosnowskysHogweed import SosnowskysHogweed


class CyberSheep(Animal):
    STRENGTH = 11
    INITIATIVE = 4

    def __init__(self, position, world, **kwargs):
        super().__init__(CyberSheep.STRENGTH, CyberSheep.INITIATIVE, position, world, **kwargs)

    def Act(self):
        class CyberSheepRequirements:
            def IsOk(self, organism):
                return isinstance(organism, SosnowskysHogweed)

        sosnowskysPosition = self.GetWorld().FindNearestPosition(self.GetPosition(), CyberSheepRequirements())
        if sosnowskysPosition is None:
            return super().Act()
        else:
            self._GoTo(self.GetPosition().GetNeighbouringCloserPosition(sosnowskysPosition))
    
    def GetColor(self):
        return Colors.PINK

    @staticmethod
    def __str__():
        return "Cybersheep"

    def GetLetter(self):
        return "C"
