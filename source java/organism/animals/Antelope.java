package organism.animals;

import java.awt.Color;
import organism.CollisionResult;
import organism.Organism;
import organism.manager.Organisms;
import organism.plants.Plant;
import utilities.MyRandom;
import utilities.position.Position;
import utilities.readyclasses.NoRequirements;
import utilities.savingsystem.Loader;
import virtualworld.World;

public class Antelope extends Animal
{
    private static final int STRENGTH = 4;
    private static final int INITIATIVE = 4;
    private static final int CHANCE_TO_ESCAPE = 50;
    private static final int MOVEMENT_RANGE = 2;
    
    public Antelope(Position position, World world)
    {
        super(STRENGTH, INITIATIVE, position, world);
    }
    
    public Antelope(Loader loader, World world, Position position)
    {
        super(loader, world, position);
    }
    
    @Override
    public void Act()
    {
        for(int i = 0; i<MOVEMENT_RANGE && isAlive(); i++)
            MoveInNeighbourhood(new NoRequirements());
            
    }
    
    @Override
    public String toString()
    {
        return "Antelope";
    }
    
    @Override
    public Organisms GetEnum() 
    {
        return Organisms.ANTELOPE;
    }
    
    @Override
    public Color GetColor()
    {
        return Color.orange.darker();
    }

    @Override
    public CollisionResult Collide(Organism organism) 
    {
        if(organism instanceof Plant)
            return CollisionResult.FIGHT;
        
        if((new MyRandom()).Chance(CHANCE_TO_ESCAPE) && MoveInNeighbourhood(new NoRequirements()))
            return CollisionResult.ESCAPE;
        return CollisionResult.FIGHT;
    }
}
