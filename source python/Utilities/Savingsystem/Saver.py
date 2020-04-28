
class Saver:

    def __init__(self, filepath):
        self.__filepath = filepath
        self.__file = open(filepath, "w")

    def Insert(self, object):
        self.__file.write(str(object) + "\n")