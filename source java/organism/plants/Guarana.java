package organism.plants;

import java.awt.Color;
import organism.CollisionResult;
import organism.Organism;
import organism.manager.Organisms;
import utilities.position.Position;
import utilities.savingsystem.Loader;
import virtualworld.World;

public class Guarana extends Plant
{
    private static final int STRENGTH = 0;
    private static final int ADDED_STRENGTH = 3;
    
    public Guarana(Position position, World world) 
    {
        super(STRENGTH, position, world);
    }
    
    public Guarana(Loader loader, World world, Position position)
    {
        super(loader, world, position);
    }
    
    @Override
    public CollisionResult Collide(Organism organism)
    {
        organism.SetStrength(organism.GetStrength() + ADDED_STRENGTH);
        return CollisionResult.PLANT;
    }
    
    @Override
    public Organisms GetEnum() 
    {
        return Organisms.GUARANA;
    }
    
    @Override
    public Color GetColor()
    {
        return Color.pink;
    }
    
    @Override
    public String toString()
    {
        return "Guarana";
    }

    
}
