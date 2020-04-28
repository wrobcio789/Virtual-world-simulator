package organism.animals;

import Interfaces.Requirements;
import java.awt.Color;
import organism.Organism;
import organism.manager.Organisms;
import utilities.position.Position;
import utilities.savingsystem.Loader;
import virtualworld.World;

public class Fox extends Animal
{
    private static final int STRENGTH = 3;
    private static final int INITIATIVE = 7;
    
    public Fox(Position position, World world) 
    {
        super(STRENGTH, INITIATIVE, position, world);
    }
    
    public Fox(Loader loader, World world, Position position)
    {
        super(loader, world, position);
    }
    
    @Override
    public void Act()
    {
        MoveInNeighbourhood( new Requirements()
        {
            @Override
            public boolean IsOk(Organism organism)
            {
                if(organism == null)
                    return true;
                return organism.GetStrength() > GetStrength();
            }
        });
    }
    
    @Override
    public Organisms GetEnum() 
    {
        return Organisms.FOX;
    }
    
    @Override
    public Color GetColor()
    {
        return Color.orange;
    }
    
    @Override
    public String toString()
    {
        return "Fox";
    }

}
