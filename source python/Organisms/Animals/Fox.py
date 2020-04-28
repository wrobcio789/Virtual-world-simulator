from Application.Organisms.Animals.Animal import Animal
from Application.Utilities import Colors


class Fox(Animal):
    STRENGTH = 3
    INITIATIVE = 7

    def __init__(self, position, world, **kwargs):
        super().__init__(Fox.STRENGTH, Fox.INITIATIVE, position, world, **kwargs)

    def Act(self):
        class FoxRequirements:
            def __init__(self, strength):
                self.__strength = strength

            def IsOk(self, organism):
                if organism is None:
                    return True
                return organism.GetStrength() <= self.__strength

        self._MoveInNeighbourhood(FoxRequirements(self.GetStrength()))

    @staticmethod
    def __str__():
        return "Fox"

    def GetColor(self):
        return Colors.ORANGE

    def GetLetter(self):
        return "L"
