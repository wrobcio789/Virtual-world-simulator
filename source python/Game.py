from Application.World import World, WorldMode
import tkinter as tk
from tkinter import filedialog
from tkinter import messagebox
from tkinter import simpledialog
import os
import pygame
from Application.Utilities.Savingsystem import *
from Application.Utilities import Colors


class Game:

    SAVE_EXTENSION = '.vwsave'

    def __init__(self):
        pygame.init()
        pygame.display.set_caption('Virtual world')
        self.__window = pygame.display.set_mode((600, 600))
        self.__isRunning = True
        self.__turnsCount = 0
        self.__world = World(4, 4, WorldMode.HEXAGON, self)

    def __del__(self):
        pygame.quit()

    def Run(self):
        self.Reset()
        self.Display()

        clock = pygame.time.Clock()
        while self.__isRunning:
            self.Input()
            self.Display()
            clock.tick(30)

        return 0

    def Reset(self):
        window = tk.Tk()
        window.withdraw()

        answer = tk.messagebox.askyesno("Board type", "Hexagons - yes, Squares - no")
        if answer:
            worldMode = WorldMode.HEXAGON
        else:
            worldMode = WorldMode.SQUARE
        height = tk.simpledialog.askinteger("Board's height", "Board's height: ", parent=window, minvalue=0, maxvalue=28)
        width = tk.simpledialog.askinteger("Board's width", "Board's width ", parent=window, minvalue=0, maxvalue=28)
        if height is None:
            height = 20
        if width is None:
            width = 20
        self.__world.Reset(width, height, worldMode)
        self.__world.SpawnOrganisms(int(max(1, width*height/12)), int(max(1, width*height/16)))
        self.__turnsCount = 0

    def Input(self):
        event = pygame.event.poll()
        if event.type == pygame.QUIT:
            self.__isRunning = False
        elif event.type == pygame.KEYDOWN:
            if event.key == pygame.K_n:
                self.NextTurn()
            elif event.key == pygame.K_ESCAPE:
                self.__isRunning = False
            elif event.key == pygame.K_r:
                self.Reset()
            elif event.key == pygame.K_s:
                self.Save()
            elif event.key == pygame.K_l:
                self.Load()
        elif event.type == pygame.MOUSEBUTTONUP:
            self.HandleClick(pygame.mouse.get_pos())

    def NextTurn(self):
        self.__turnsCount += 1
        print("Turn number " + str(self.__turnsCount))
        self.__world.Act()

    def Display(self):
        self.__window.fill(Colors.WHITE)
        self.__world.Display(self.__window)
        pygame.display.flip()

    def Save(self):
        my_filetypes = [(Game.SAVE_EXTENSION + ' files', Game.SAVE_EXTENSION), ('all files', '.*')]
        window = tk.Tk()
        window.withdraw()
        answer = tk.filedialog.asksaveasfilename(parent=window,
                                               initialdir=os.getcwd(),
                                               title="Choose file to save:",
                                               filetypes=my_filetypes)
        print(answer)
        if answer is None or answer == '':
            return
        self.__world.Save(Saver(answer))
        window.quit()
        print(answer)

    def Load(self):
        my_filetypes = [(Game.SAVE_EXTENSION + ' files', Game.SAVE_EXTENSION), ('all files', '.*')]
        window = tk.Tk()
        window.withdraw()
        answer = tk.filedialog.askopenfilename(parent=window,
                                                 initialdir=os.getcwd(),
                                                 title="Choose file to load:",
                                                 filetypes=my_filetypes)
        window.quit()
        if answer is None or answer == '':
            return

        self.__turnsCount = 0
        self.__world.Load(Loader(answer))
        print(answer)

    def GetKey(self):
        event = pygame.event.poll()
        while event.type != pygame.KEYDOWN:
            if event.type == pygame.QUIT:
                exit(0)
            event = pygame.event.poll()
        return event.key

    def HandleClick(self, pos):
        self.__world.HandleClick(pos)


def main():
    game = Game()
    return game.Run()


if __name__ == '__main__':
    main()