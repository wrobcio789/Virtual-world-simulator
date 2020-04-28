package organism.animals;

import organism.CollisionResult;
import organism.Organism;
import Interfaces.Requirements;
import utilities.position.Position;
import utilities.readyclasses.NoRequirements;
import utilities.savingsystem.Loader;
import virtualworld.World;

public abstract class Animal extends Organism
{
    
    public Animal(int strength, int initiative, Position position, World world)
    {
        super(strength, initiative, position, world);
    }
    
    public Animal(Loader loader, World world, Position position)
    {
        super(loader, world, position);
    }
    
    @Override
    public void Act()
    {
        MoveInNeighbourhood(new NoRequirements());
    }
    
    @Override
    public CollisionResult Collide(Organism organism)
    {
        return CollisionResult.FIGHT;
    }
    
    public boolean RunCollision(Organism organism)
    {
        if(organism == null)
            return true;
        
        if(Copulate(organism))
            return false;
        
        CollisionResult myCollisionResult = Collide(organism);
        CollisionResult organismCollisionResult = organism.Collide(this);
        
        if(myCollisionResult == CollisionResult.ESCAPE)
        {
            GetWorld().AddToLogs(toString() + " runs away from " + organism.toString());
            return false;
        }
        if(myCollisionResult == CollisionResult.STOP || organismCollisionResult == CollisionResult.STOP)
        {
            GetWorld().AddToLogs("Fight beetwen " + organism.toString() + " and " + toString() + " has been stopped");
            return false;
        }
        if(organismCollisionResult == CollisionResult.ESCAPE)
        {
            GetWorld().AddToLogs(organism.toString() + " runs away from " + toString());
            return true;
        }
        
     
        return Fight(organism);
    }
    
    boolean Fight(Organism organism)
    {
        if(organism instanceof Animal && this.GetStrength() < organism.GetStrength())
        {
            GetWorld().AddToLogs(organism.toString() + " defeats " + toString());
            Die();
            return false;
        }
        
        organism.Die();
        GetWorld().AddToLogs(toString() + " defeats/eats " + organism.toString());
        return true;
    }
    
    protected boolean Copulate(Organism organism)
    {
        if(organism.GetEnum() != GetEnum())
            return false;
        
        class Req implements Requirements
        {
            @Override
            public boolean IsOk(Organism organism)
            {
                return organism == null;
            }
        }
       
        Position position = GetWorld().GetRandomPlaceInNeighbourhood(GetPosition(), new Req());
        if(position == null)
            return true;
        
        GetWorld().CreateOrganism(GetEnum(), position);
        return true;
    }
    
    protected boolean MoveInNeighbourhood(Requirements req)
    {
        Position newPosition = GetWorld().GetRandomPlaceInNeighbourhood(GetPosition(), req);
        if(newPosition == null)
            return false;
        GoTo(newPosition);
        return true;
    }
    
    void GoTo(Position position)
    {
        GetWorld().MoveAnimal(this, position);
    }
}
