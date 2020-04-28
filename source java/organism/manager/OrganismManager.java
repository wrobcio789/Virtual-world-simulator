package organism.manager;

import organism.Organism;
import organism.animals.Antelope;
import organism.animals.CyberSheep;
import organism.animals.Fox;
import organism.animals.Human;
import organism.animals.Sheep;
import organism.animals.Turtle;
import organism.animals.Wolf;
import organism.plants.Belladonna;
import organism.plants.Dandelion;
import organism.plants.Grass;
import organism.plants.Guarana;
import organism.plants.SosnowskysHogweed;
import virtualworld.World;
import utilities.position.Position;
import utilities.savingsystem.Loader;
import utilities.savingsystem.Saver;


public class OrganismManager 
{
    public Organism CreateOrganism(Organisms orgEnum, World world, Position position)
    {
        switch(orgEnum)
        {
            case ANTELOPE:
                return new Antelope(position, world);
            case CYBERSHEEP:
                return new CyberSheep(position, world);
            case FOX:
                return new Fox(position, world);
            case SHEEP:
                return new Sheep(position, world);
            case TURTLE:
                return new Turtle(position, world);
            case WOLF:
                return new Wolf(position, world);
                
            case BELLADONNA:
                return new Belladonna(position, world);
            case DANDELION:
                return new Dandelion(position, world);
            case GRASS:
                return new Grass(position, world);
            case GUARANA:
                return new Guarana(position, world);
            case SOSNOWSKYS_HOGWEED:
                return new SosnowskysHogweed(position, world);
                
            case HUMAN:
                return new Human(position, world);
                
        }
        
        System.out.println("Null returned in organism manager create method.");
        return null;
    }

    public void SaveOrganism(Organism organism, Saver saver) 
    {
        saver.Print(organism.GetEnum().toString());
        organism.Save(saver);
    }

    public Organism LoadOrganism(Loader loader, World world, Position position) 
    {
        Organisms orgEnum = Organisms.valueOf(loader.NextString());
        switch(orgEnum)
        {
            case ANTELOPE:
                return new Antelope(loader, world, position);
            case CYBERSHEEP:
                return new CyberSheep(loader, world, position);
            case FOX:
                return new Fox(loader, world, position);
            case SHEEP:
                return new Sheep(loader, world, position);
            case TURTLE:
                return new Turtle(loader, world, position);
            case WOLF:
                return new Wolf(loader, world, position);
                
            case BELLADONNA:
                return new Belladonna(loader, world, position);
            case DANDELION:
                return new Dandelion(loader, world, position);
            case GRASS:
                return new Grass(loader, world, position);
            case GUARANA:
                return new Guarana(loader, world, position);
            case SOSNOWSKYS_HOGWEED:
                return new SosnowskysHogweed(loader, world, position);
                
            case HUMAN:
                return new Human(loader, world, position);
        }
        
        System.out.println("Null returned in organism manager loading method.");
        return null;
    }
}
