package organism.animals;

import Interfaces.Requirements;
import java.awt.Color;
import organism.Organism;
import organism.manager.Organisms;
import organism.plants.SosnowskysHogweed;
import utilities.position.Position;
import utilities.savingsystem.Loader;
import virtualworld.World;

public class CyberSheep extends Sheep
{
    private static final int STRENGTH = 11;
    private static final int INITIATIVE = 4;
    
    public CyberSheep(Position position, World world) {
        super(STRENGTH, INITIATIVE, position, world);
    }
    
    public CyberSheep(Loader loader, World world, Position position)
    {
        super(loader, world, position);
    }

    @Override
    public void Act()
    {
        Position sosnowskysPosition = GetWorld().FindNearestPosition(GetPosition(), new Requirements()
        {
            @Override
            public boolean IsOk(Organism organism)
            {
                return (organism instanceof SosnowskysHogweed);
            }
        });
        
        if(sosnowskysPosition == null)
            super.Act();
        else
            GoTo(GetPosition().GetNeighbouringCloserPosition(sosnowskysPosition));
    }
    
    @Override
    public Color GetColor()
    {
        return Color.blue.brighter();
    }
    
    @Override
    public Organisms GetEnum()
    {
        return Organisms.CYBERSHEEP;
    }
    
    @Override
    public String toString()
    {
        return "Cybersheep";
    }
    
    
}
