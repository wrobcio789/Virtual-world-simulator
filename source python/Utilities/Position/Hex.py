import random
import pygame
from Application.Utilities.Position.Point import Point


class Hex:

    def __init__(self, x=0, y=0, z=None):
        if z is None:
            self.x = x
            self.z = y
            self.y = -self.x - self.z
        else:
            self.x, self.y, self.z = x, y, z

    def ToPoint(self):
        return Point(self.x, self.z)

    def __hash__(self):
        return int((self.x + self.z) * (self.x + self.z + 1) / 2 + self.z)

    def __str__(self):
        return "Hex (" + str(self.x) + ", " + str(self.y) + ", " + str(self.z) + ")"

    def __eq__(self, other):
        if not isinstance(other, Hex):
            return False
        return (self.x, self.y, self.z) == (other.x, other.y, other.z)

    def __ne__(self, other):
        return not (self == other)

    def __add__(self, other):
        return Hex(self.x + other.x, self.y + other.y, self.z + other.z)

    @staticmethod
    def GetRandom(width, height):
        return Hex(random.randint(0, width - 1), random.randint(0, height - 1))

    def Distance(self, hex):
        return (abs(self.x - hex.x) + abs(self.y - hex.y) + abs(self.z - hex.z)) / 2

    def IsInBoundary(self, width, height):
        return self.x >= 0 and self.x < width and self.z >= 0 and self.z < height

    def GetRandomNeighbour(self):
        return self + Hex.Neighbours()[random.randint(0, len(Hex.Neighbours()) - 1)]

    def GetAllNeighbours(self):
        absoluteNeighbours = Hex.Neighbours()
        result = list()
        for absoluteNeighbour in absoluteNeighbours:
            result.append(self + absoluteNeighbour)
        return result

    def GetNeighbouringCloserPosition(self, position):
        distance = self.Distance(position)
        if distance == 0:
            return self

        neighbours = self.GetAllNeighbours()
        for neighbour in neighbours:
            currentDistance = neighbour.Distance(position)
            if currentDistance < distance:
                return neighbour
        return self

    def GetMovement(self, key):
        if key == pygame.K_w:
            if self.z % 2 == 0:
                return self + Hex(-1, -1)
            return self + Hex(0, 1, -1)
        elif key == pygame.K_e:
            if self.z % 2 == 0:
                return self + Hex(0, -1)
            return self + Hex(1, 0, -1)
        elif key == pygame.K_d:
            return self + Hex(1, -1, 0)
        elif key == pygame.K_x:
            if self.z % 2 == 0:
                return self + Hex(0, 1)
            return self + Hex(1, 1)
        elif key == pygame.K_z:
            if self.z % 2 == 0:
                return self + Hex(-1, 1)
            return self + Hex(0, 1)
        elif key == pygame.K_a:
            return self + Hex(-1, 1, 0)
        else:
            return None


    @staticmethod
    def Neighbours():
        if not hasattr(Hex.Neighbours, "neighbours"):
            Hex.Neighbours.__neighbours = [Hex(0, -1), Hex(1, -1), Hex(1, 0), Hex(0, 1), Hex(-1, 1), Hex(-1, 0)]
        return Hex.Neighbours.__neighbours

    def Save(self, saver):
        saver.Insert(self.x)
        saver.Insert(self.y)
        saver.Insert(self.z)

    @staticmethod
    def Load(loader):
        return Hex(int(loader.Next()), int(loader.Next()), int(loader.Next()))





