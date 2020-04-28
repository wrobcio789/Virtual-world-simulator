package organism.plants;

import java.awt.Color;
import organism.manager.Organisms;
import utilities.position.Position;
import utilities.savingsystem.Loader;
import virtualworld.World;

public class Dandelion extends Plant
{
    private static final int STRENGTH = 0;
    private static final int SPREAD_TRIES = 3;

    public Dandelion(Position position, World world) 
    {
        super(STRENGTH, position, world);
    }
    
    public Dandelion(Loader loader, World world, Position position)
    {
        super(loader, world, position);
    }
    
    @Override
    public void Act()
    {
        for(int i = 0; i < SPREAD_TRIES; i++)
            Spread();
    }
    
    @Override
    public Organisms GetEnum() 
    {
        return Organisms.DANDELION;
    }
    
    @Override
    public Color GetColor()
    {
        return Color.yellow;
    }
    
    @Override
    public String toString()
    {
        return "Dandelion";
    }
}
