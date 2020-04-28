package organism.plants;

import java.awt.Color;
import organism.manager.Organisms;
import utilities.position.Position;
import utilities.savingsystem.Loader;
import virtualworld.World;

public class Grass extends Plant
{
    private static final int STRENGTH = 0;

    public Grass(Position position, World world) 
    {
        super(STRENGTH, position, world);
    }
    
    public Grass(Loader loader, World world, Position position)
    {
        super(loader, world, position);
    }
    
    @Override
    public Organisms GetEnum() 
    {
        return Organisms.GRASS;
    }
    
    @Override
    public Color GetColor()
    {
        return Color.green;
    }
    
    @Override
    public String toString()
    {
        return "Grass";
    }
}
