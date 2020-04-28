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

public class Turtle extends Animal
{
    private static final int STRENGTH = 2;
    private static final int INITIATIVE = 1;
    private static final int CHANCE_TO_NOTHING = 75;
    private static final int ENEMY_STRENGTH_TO_STOP = 4;
    
    public Turtle(Position position, World world) 
    {
        super(STRENGTH, INITIATIVE, position, world);
    }
    
    public Turtle(Loader loader, World world, Position position)
    {
        super(loader, world, position);
    }
    
    @Override
    public void Act()
    {
        MyRandom generator = new MyRandom();
        if(!generator.Chance(CHANCE_TO_NOTHING))
            MoveInNeighbourhood(new NoRequirements());
    }
    
    @Override
    public CollisionResult Collide(Organism organism)
    {
        if( !(organism instanceof Plant) && organism.GetStrength() <= ENEMY_STRENGTH_TO_STOP)
            return CollisionResult.STOP;
        return CollisionResult.FIGHT;
    }
    
    @Override
    public Organisms GetEnum() 
    {
        return Organisms.TURTLE;
    }
    
    @Override
    public Color GetColor()
    {
        return Color.green.darker();
    }
    
    @Override
    public String toString()
    {
        return "Turtle";
    }
}
