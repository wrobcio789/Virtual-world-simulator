package organism.animals;

import java.awt.Color;
import organism.manager.Organisms;
import utilities.position.Position;
import utilities.savingsystem.Loader;
import virtualworld.World;


public class Sheep extends Animal
{
    private static final int STRENGTH = 4;
    private static final int INITIATIVE = 4;
    
    public Sheep(Position position, World world)
    {
        super(STRENGTH, INITIATIVE, position, world);
    }
    
    protected Sheep(int strength, int initiative, Position position, World world)
    {
        super(strength, initiative, position, world);
    }
    
    public Sheep(Loader loader, World world, Position position)
    {
        super(loader, world, position);
    }
    
    @Override
    public Organisms GetEnum() 
    {
        return Organisms.SHEEP;
    }
    
    @Override
    public Color GetColor()
    {
        return Color.white;
    }
    
    @Override
    public String toString()
    {
        return "Sheep";
    }

}
