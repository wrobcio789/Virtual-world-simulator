package organism.plants;

import Interfaces.Requirements;
import organism.CollisionResult;
import organism.Organism;
import utilities.MyRandom;
import utilities.position.Position;
import utilities.savingsystem.Loader;
import virtualworld.World;

public abstract class Plant extends Organism
{
    private static final int CHANCE_TO_SPREAD = 8;
    private static final int INITIATIVE = 0;
    
    public Plant(int strength, Position position, World world) {
        super(strength, INITIATIVE, position, world);
    }
    
    public Plant(Loader loader, World world, Position position)
    {
        super(loader, world, position);
    }

    @Override
    public void Act() 
    {
        Spread();
    }

    @Override
    public CollisionResult Collide(Organism organism) 
    {
        return CollisionResult.PLANT;
    }
    
    protected boolean Spread()
    {
        MyRandom generator = new MyRandom();
        if(!generator.Chance(CHANCE_TO_SPREAD))
            return false;
        
        Position position = GetWorld().GetRandomPlaceInNeighbourhood(GetPosition(), new Requirements()
        {
            @Override
            public boolean IsOk(Organism organism)
            {
                return organism == null;
            }
        });
        
        if(position == null)
            return true;
        
        GetWorld().CreateOrganism(GetEnum(), position);
        return true;
    }

}
