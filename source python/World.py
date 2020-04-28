from Application.Utilities import Colors
from Application.Utilities.Position.Point import Point
from Application.Utilities.Position.Hex import Hex
from Application.Organisms import *
from Application.Requirements import NoRequirements
from Application.Utilities import Savingsystem
import math
import random
import pygame.freetype
import pygame
import tkinter as tk
from tkinter import simpledialog
from enum import Enum


class WorldMode(Enum):

    SQUARE = 1
    HEXAGON = 2


class World:

    BOARD_X = 20
    BOARD_Y = 20
    SQUARE_TILE_SIZE = 20
    HEX_TILE_SIZE = 12

    DEFAULT_COLOR = Colors.SILVER

    def __init__(self, width, height, worldMode, gamePtr):
        self.__gamePtr = gamePtr
        self.Reset(width, height, worldMode)

    def Reset(self, width, height, worldMode):
        self.__width = width
        self.__height = height
        self.__worldMode = worldMode
        self.__organisms = {}
        self.__removalSet = set()

    def IsInBoundary(self, position):
        return position.IsInBoundary(self.__width, self.__height)

    def Act(self):
        organisms = list(self.__organisms.values())
        organisms.sort(key=lambda organism: organism.GetInstanceNumber())
        organisms.sort(key=lambda organism: organism.GetInitiative(), reverse=True)

        for organism in organisms:
            if not organism.IsAlive() or organism in self.__removalSet:
                continue
            organism.Act()

        self.__removalSet.clear()

    def Display(self, window):

        if self.__worldMode == WorldMode.SQUARE:
            font = pygame.freetype.SysFont('calibri', 17)
            baseRect = pygame.Rect(World.BOARD_X, World.BOARD_Y, World.SQUARE_TILE_SIZE, World.SQUARE_TILE_SIZE)
            for i in range(self.__height):
                for j in range(self.__width):
                    rect = baseRect.move(j * World.SQUARE_TILE_SIZE, i * World.SQUARE_TILE_SIZE)

                    position = Point(j, i)
                    if position in self.__organisms:
                        color = self.__organisms.get(position).GetColor()
                    else:
                        color = Colors.SILVER

                    pygame.draw.rect(window, color, rect)
                    pygame.draw.rect(window, Colors.DARK_GRAY, rect, 1)

                    if position in self.__organisms:
                        textRect = font.get_rect(self.__organisms.get(position).GetLetter())
                        font.render_to(window, rect.move((World.SQUARE_TILE_SIZE - textRect.width) / 2, (World.SQUARE_TILE_SIZE - textRect.height) / 2), self.__organisms.get(position).GetLetter())

        elif self.__worldMode == WorldMode.HEXAGON:
            font = pygame.freetype.SysFont('calibri', 17)
            w = int(World.HEX_TILE_SIZE * math.sqrt(3))
            h = World.HEX_TILE_SIZE * 2
            basePolygon = [(-w/2, -h/4), (0, -h/2), (w/2, -h/4), (w/2, h/4), (0, h/2), (-w/2, h/4)]
            polygon = [(0, 0)] * 6

            for i in range(self.__height):
                for j in range(self.__width):
                    xShift = World.BOARD_X + j*w + (w/2 if i % 2 == 1 else 0)
                    yShift = World.BOARD_Y + i*3*h / 4

                    for k in range(6):
                        polygon[k] = (basePolygon[k][0] + xShift, basePolygon[k][1] + yShift)

                    position = Hex(j, i)
                    if position in self.__organisms:
                        color = self.__organisms.get(position).GetColor()
                    else:
                        color = Colors.SILVER

                    pygame.draw.polygon(window, color, polygon)
                    pygame.draw.polygon(window, Colors.DARK_GRAY, polygon, 1)

                    if position in self.__organisms:
                        textRect = font.get_rect(self.__organisms.get(position).GetLetter())
                        font.render_to(window, (xShift - w / 2 + (w - textRect.width) / 2, yShift - h / 4), self.__organisms.get(position).GetLetter())


    def CreateOrganism(self, position, instance):
        if position in self.__organisms:
            return
        self.__organisms[position] = instance.__class__(position, self)

    def SpawnOrganisms(self, animalsCount, plantsCount):
        if animalsCount + plantsCount > self.__width * self.__height:
            return False

        animals = Animal.__subclasses__()
        animals.remove(Human)
        position = self.__GetPosition().GetRandom(self.__width, self.__height)
        self.__organisms[position] = Human(position, self)

        for i in range(animalsCount - 1):
            while True:
                position = self.__GetPosition().GetRandom(self.__width, self.__height)
                if not position in self.__organisms:
                    self.__organisms[position] = animals[random.randint(0, len(animals) - 1)](position, self)
                    break

        plants = Plant.__subclasses__()
        for i in range(plantsCount):
            while True:
                position = self.__GetPosition().GetRandom(self.__width, self.__height)
                if not position in self.__organisms:
                    self.__organisms[position] = plants[random.randint(0, len(plants) - 1)](position, self)
                    break

    def FindNearestPosition(self, position, requirements):
        nearestPosition = None
        lowestDistance = 2 * (self.__width + self.__height)

        if isinstance(requirements, NoRequirements):
            return position.GetRandomNeighbour()

        for i in range(self.__height):
            for j in range(self.__width):
                tmpPosition = self.__GetPosition()(j, i)

                if tmpPosition == position:
                    continue

                currentDistance = position.Distance(tmpPosition)
                if requirements.IsOk(self.__organisms.get(tmpPosition)) and currentDistance < lowestDistance:
                    nearestPosition = tmpPosition
                    lowestDistance = currentDistance

        return nearestPosition

    def GetRandomPlaceInNeighbourhood(self, position, requirements):
        nearestPosition = self.FindNearestPosition(position, requirements)
        if nearestPosition is None or nearestPosition.Distance(position) > 1:
            return None

        while True:
            result = position.GetRandomNeighbour()
            if self.IsInBoundary(result) and requirements.IsOk(self.__organisms.get(result)) and result != position:
                break

        return result

    def MoveInternally(self, organism, position):
        oldPosition = organism.GetPosition()
        if oldPosition == position:
            return

        organism.SetPosition(position)
        self.__organisms.pop(oldPosition, None)
        self.__organisms.pop(position, None)
        self.__organisms[position] = organism

    def MoveAnimal(self, animal, newPosition):
        if not self.IsInBoundary(newPosition):
            print("This should never happen")
            return

        if self.__organisms.get(newPosition) is None or (animal.RunCollision(self.__organisms.get(newPosition)) and animal.IsAlive()):
            self.MoveInternally(animal, newPosition)

    def AddToRemovalSet(self, organism):
        self.__removalSet.add(organism)
        self.__organisms.pop(organism.GetPosition(), None)

    def GetOrganism(self, position):
        return self.__organisms.get(position)

    def GetKey(self):
        return self.__gamePtr.GetKey()

    def __GetPosition(self):
        if self.__worldMode == WorldMode.SQUARE:
            return Point
        elif self.__worldMode == WorldMode.HEXAGON:
            return Hex
        return None

    def HandleClick(self, pos):
        position = self.__GetPositionByScreenCoord(pos)

        if not self.IsInBoundary(position):
            return

        if self.__organisms.get(position) is None:
            window = tk.Tk()
            window.withdraw()

            organisms = Animal.__subclasses__() + Plant.__subclasses__()
            organisms.remove(Human)

            prompt = "Wybierz numer: \n"
            i = 0
            for organismClass in organisms:
                prompt += str(i) + ". " + organismClass.__str__() + "\n"
                i += 1

            answer = simpledialog.askinteger("Tworzenie organizmu", prompt, parent=window, minvalue=0, maxvalue=len(organisms))
            if answer is not None:
                self.__organisms[position] = organisms[answer](position, self)
                self.__gamePtr.Display()
            window.quit()

    def __GetPositionByScreenCoord(self, pos):
        if self.__worldMode == WorldMode.SQUARE:
            return Point(int((pos[0] - World.BOARD_X) / World.SQUARE_TILE_SIZE), int((pos[1] - World.BOARD_Y) / World.SQUARE_TILE_SIZE))
        elif self.__worldMode == WorldMode.HEXAGON:
            w = int(World.HEX_TILE_SIZE * math.sqrt(3))
            h = World.HEX_TILE_SIZE * 2
            x = int(pos[0] - World.BOARD_X + w/2)
            y = int(pos[1] - World.BOARD_Y + h/2)
            h = int(3 * h / 4)

            row = int(y/h)
            if row % 2 == 1:
                column = int((x-int(w/2))/w)
            else:
                column = int(x / w)

            relY = float(y - row*h)
            if row % 2 == 1:
                relX = float(x - column * w - w / 2)
            else:
                relX = float(x - column * w)

            m = float(math.tan(math.radians(30)))
            c = float(m * w / 2)

            if relY < - m*relX + c:
                if row % 2 == 0:
                    column -= 1
                row -= 1
            elif relY < m*relX - c:
                if row % 2 == 1:
                    column += 1
                row -= 1

            return Hex(column, row)

    def Save(self, saver):
        saver.Insert("#WIDTH")
        saver.Insert(self.__width)
        saver.Insert("#HEIGHT")
        saver.Insert(self.__height)
        saver.Insert("#WORLDMODE")
        saver.Insert(self.__worldMode.name)
        saver.Insert("#ORGANISMS_NUMBER")
        saver.Insert(len(self.__organisms))

        saver.Insert("#ORGANISMS")
        for i in range(self.__height):
            for j in range(self.__width):
                position = self.__GetPosition()(j, i)
                if self.__organisms.get(position) is None:
                    continue
                position.Save(saver)
                organism = self.__organisms.get(position)
                saver.Insert(str(organism))
                organism.Save(saver)

    def Load(self, loader):
        loader.GoTo("#WIDTH")
        width = int(loader.Next())
        loader.GoTo("#HEIGHT")
        height = int(loader.Next())
        loader.GoTo("#WORLDMODE")
        worldMode = WorldMode[loader.Next()]
        self.Reset(width, height, worldMode)
        loader.GoTo("#ORGANISMS_NUMBER")
        organismNumber = int(loader.Next())
        loader.GoTo("#ORGANISMS")

        organisms = Animal.__subclasses__() + Plant.__subclasses__()

        for i in range(organismNumber):
            position = self.__GetPosition().Load(loader)
            name = loader.Next()
            for organismClass in organisms:
                if organismClass.__str__() == name:
                    self.__organisms[position] = organismClass(position, self, loader=loader)

        self.__gamePtr.Display()
