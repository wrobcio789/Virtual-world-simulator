import random
import pygame


class Point:

    def __init__(self, x = 0, y = 0):
        self.x = x
        self.y = y
    
    def __hash__(self):
        return int((self.x + self.y) * (self.x + self.y + 1) / 2 + self.y)

    def __str__(self):
        return "Point (" + str(self.x) + ", " + str(self.y) + ")"

    def __eq__(self, other):
        if not isinstance(other, Point):
            return False
        return (self.x, self.y) == (other.x, other.y)

    def __ne__(self, other):
        return not (self == other)
    
    def __add__(self, other):
        return Point(self.x + other.x, self.y + other.y)

    @staticmethod
    def GetRandom(width, height):
        return Point(random.randint(0, width - 1), random.randint(0, height - 1))
    
    def Distance(self, point):
        return abs(self.x - point.x) + abs(self.y - point.y)

    def IsInBoundary(self, width, height):
        return self.x >= 0 and self.x < width and self.y >= 0 and self.y < height

    def GetRandomNeighbour(self):
        return self + Point.Neighbours()[random.randint(0, len(Point.Neighbours()) - 1)]

    def GetAllNeighbours(self):
        absoluteNeighbours = Point.Neighbours()
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
        if key == pygame.K_UP:
            return self + Point.Neighbours()[2]
        elif key == pygame.K_DOWN:
            return self + Point.Neighbours()[3]
        elif key == pygame.K_LEFT:
            return self + Point.Neighbours()[0]
        elif key == pygame.K_RIGHT:
            return self + Point.Neighbours()[1]
        else:
            return None

    @staticmethod
    def Neighbours():
        if not hasattr(Point.Neighbours, "neighbours"):
            Point.Neighbours.__neighbours = [Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1)]
        return Point.Neighbours.__neighbours

    def Save(self, saver):
        saver.Insert(self.x)
        saver.Insert(self.y)

    @staticmethod
    def Load(loader):
        return Point(int(loader.Next()), int(loader.Next()))







