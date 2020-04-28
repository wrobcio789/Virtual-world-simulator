package organism.animals;

import java.awt.Color;
import organism.manager.Organisms;
import utilities.position.Position;
import utilities.savingsystem.Loader;
import virtualworld.World;

public class Wolf extends Animal
{
    private static final int STRENGTH = 9;
    private static final int INITIATIVE = 5;
    
    
    public Wolf(Position position, World world) 
    {
        super(STRENGTH, INITIATIVE, position, world);
    }
    
    public Wolf(Loader loader, World world, Position position)
    {
        super(loader, world, position);
    }
    
     @Override
    public Organisms GetEnum() 
    {
        return Organisms.WOLF;
    }
    
    @Override
    public Color GetColor()
    {
        return Color.darkGray;
    }
    
    @Override
    public String toString()
    {
        return "Wolf";
    }

}
