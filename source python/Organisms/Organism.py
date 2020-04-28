from abc import ABC, abstractmethod


class Organism(ABC):

    instancesCount = 0

    def __init__(self, strength, initiative, position, world, **kwargs):

        if "loader" in kwargs:
            loader = kwargs.get("loader")
            self.__position = position
            self.__isAlive = bool(loader.Next())
            self.__instanceNumber = int(loader.Next())
            self.__strength = int(loader.Next())
            self.__initiative = int(loader.Next())
            self.__world = world

            if self.__instanceNumber > Organism.instancesCount:
                Organism.instancesCount = self.__instanceNumber
            return


        Organism.instancesCount += 1
        self.__strength = strength
        self.__initiative = initiative
        self.__position = position
        self.__world = world
        self.__instanceNumber = Organism.instancesCount
        self.__isAlive = True

    

    #Virtual methods:
    @abstractmethod
    def Act(self):
        pass
    
    @abstractmethod
    def Collide(self, organism):
        pass
    
    @abstractmethod
    def GetLetter(self):
        pass
    
    @abstractmethod
    def __str__(self):
        pass

    @abstractmethod
    def GetColor(self):
        pass

    #Useful:
    def Die(self):
        self.__isAlive = False
        self.GetWorld().AddToRemovalSet(self)


    #Getters:
    def GetStrength(self):
        return self.__strength

    def GetInitiative(self):
        return self.__initiative
    
    def GetPosition(self):
        return self.__position

    def GetWorld(self):
        return self.__world

    def GetInstanceNumber(self):
        return self.__instanceNumber

    def IsAlive(self):
        return self.__isAlive

    #Setters:
    def SetStrength(self, strength):
        self.__strength = strength

    def SetPosition(self, position):
        self.__position = position

    def Save(self, saver):
        saver.Insert(self.__isAlive)
        saver.Insert(self.__instanceNumber)
        saver.Insert(self.__strength)
        saver.Insert(self.__initiative)



from enum import Enum


class ColisionResult(Enum):
    
    ESCAPE = 1
    STOP = 2
    FIGHT = 3
    PLANT = 4