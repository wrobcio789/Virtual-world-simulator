package organism.plants;

import java.awt.Color;
import organism.CollisionResult;
import organism.Organism;
import organism.animals.CyberSheep;
import organism.manager.Organisms;
import utilities.position.Position;
import utilities.savingsystem.Loader;
import virtualworld.World;

public class SosnowskysHogweed extends Plant
{
    private static final int STRENGTH = 10;

    public SosnowskysHogweed(Position position, World world) 
    {
        super(STRENGTH, position, world);
    }
    
    public SosnowskysHogweed(Loader loader, World world, Position position)
    {
        super(loader, world, position);
    }
    
    @Override
    public void Act()
    {
        Position[] neighbours = GetPosition().GetNeighbours();
        for(Position position : neighbours)
        {
            Organism neighbourOrganism = GetWorld().GetOrganism(position);
            if(neighbourOrganism != null && !(neighbourOrganism instanceof CyberSheep) && !(neighbourOrganism instanceof Plant))
            {
                GetWorld().AddToLogs(neighbourOrganism.toString() + " got urticated.");
                neighbourOrganism.Die();
            }
        }
    }
    
    @Override
    public CollisionResult Collide(Organism organism)
    {
        if(!(organism instanceof CyberSheep))
            organism.Die();
        return CollisionResult.PLANT;
    }
    
    @Override 
    public Organisms GetEnum()
    {
        return Organisms.SOSNOWSKYS_HOGWEED;
    }
    
    @Override
    public Color GetColor()
    {
        return Color.magenta;
    }
    
    @Override
    public String toString()
    {
        return "Sosnowsky's Hogweed";
    }
}
