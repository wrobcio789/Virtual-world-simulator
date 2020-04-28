package organism.plants;

import java.awt.Color;
import organism.CollisionResult;
import organism.Organism;
import organism.manager.Organisms;
import utilities.position.Position;
import utilities.savingsystem.Loader;
import virtualworld.World;

public class Belladonna extends Plant
{
    private static final int STRENGTH = 99;

    
    public Belladonna(Position position, World world) 
    {
        super(STRENGTH, position, world);
    }
    
    public Belladonna(Loader loader, World world, Position position)
    {
        super(loader, world, position);
    }
    
    @Override
    public CollisionResult Collide(Organism organism)
    {
        organism.Die();
        GetWorld().AddToLogs(organism.toString() + " dies after eating " + toString());
        return CollisionResult.PLANT;
    }
    
    @Override
    public Organisms GetEnum() 
    {
        return Organisms.BELLADONNA;
    }
    
    @Override
    public Color GetColor()
    {
        return Color.red;
    }
    
    @Override
    public String toString()
    {
        return "Belladonna";
    }
}
