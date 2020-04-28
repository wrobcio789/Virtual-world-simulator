
class Loader:

    def __init__(self, filepath):
        self.__filepath = filepath
        self.__file = open(filepath, "r")

    def Next(self):
        return self.__file.readline().rstrip()

    def GoTo(self, linePrefix):
        self.__file.close()
        self.__file = open(self.__filepath, "r")
        for line in self.__file:
            line = line.rstrip()
            if line == linePrefix:
                return True
        return False
