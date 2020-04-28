from Application.Organisms.Animals.Animal import Animal
from Application.Utilities import Colors
import pygame
import random


class Human(Animal):
    STRENGTH = 5
    INITIATIVE = 4
    ABILITY_FIRST_PHASE_TIME = 3
    ABILITY_SECOND_PHASE_TIME = 5
    ABILITY_MOVES = 2
    ABILITY_SECOND_PHASE_CHANCE = 50
    ABILITY_WAITING_TIME = 10

    def __init__(self, position, world, **kwargs):
        super().__init__(Human.STRENGTH, Human.INITIATIVE, position, world, **kwargs)
        if "loader" in kwargs:
            loader = kwargs["loader"]
            self.__timeSinceAbilityActivated = int(loader.Next())
        else:
            self.__timeSinceAbilityActivated = Human.ABILITY_WAITING_TIME + 1

    def Act(self):
        self.__timeSinceAbilityActivated += 1

        moves = 1
        if self.__timeSinceAbilityActivated < Human.ABILITY_FIRST_PHASE_TIME or (self.__timeSinceAbilityActivated < Human.ABILITY_SECOND_PHASE_TIME and random.randint(0, 99) < Human.ABILITY_SECOND_PHASE_CHANCE):
            moves = Human.ABILITY_MOVES

        print("Human is waiting for his turn")
        while moves != 0 and self.IsAlive():
            key = self.GetWorld().GetKey()

            if key == pygame.K_s and self.__ActivateSpecialAbility():
                moves += Human.ABILITY_MOVES - 1
            newPosition = self.GetPosition().GetMovement(key)
            if newPosition is None:
                continue
            if self.GetWorld().IsInBoundary(newPosition):
                self._GoTo(newPosition)
            moves -= 1

    def __ActivateSpecialAbility(self):
        if self.__timeSinceAbilityActivated >= Human.ABILITY_WAITING_TIME:
            self.__timeSinceAbilityActivated = 0
            return True

        return False

    @staticmethod
    def __str__():
        return "Human"

    def GetColor(self):
        return Colors.LIME

    def GetLetter(self):
        return "H"

    def Save(self, saver):
        super().Save(saver)
        saver.Insert(self.__timeSinceAbilityActivated)



