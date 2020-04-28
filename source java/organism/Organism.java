package organism;

import java.awt.Color;
import java.util.Comparator;
import organism.manager.Organisms;
import utilities.position.Position;
import utilities.savingsystem.Loader;
import utilities.savingsystem.Saver;
import virtualworld.World;

public abstract class Organism
{
    private static int instancesCount = 0;
    
    private boolean isAlive_;
    private final int instanceNumber_;
    private int strength_;
    private int initiative_;
    private Position position_;
    private final World world_;
    
    public Organism(int strength, int initiative, Position position, World world)
    {   
        this.strength_ = strength;
        this.initiative_ = initiative;
        this.position_ = position;
        this.world_ = world;
        
        isAlive_ = true;
        instanceNumber_ = ++instancesCount;
    }
    
    public Organism(Loader loader, World world, Position position)
    {
        position_ = position;
        isAlive_ = loader.NextBoolean();
        instanceNumber_ = loader.NextInt();
        strength_ = loader.NextInt();
        initiative_ = loader.NextInt();
        world_ = world;
        
        if(instanceNumber_ > instancesCount)
            instancesCount = instanceNumber_;
    }
    
    public abstract void Act();
    public abstract CollisionResult Collide(Organism organism);
    public abstract Organisms GetEnum();
    
    public void Die()
    {
        isAlive_ = false;
        GetWorld().AddToRemovalSet(this);
    }
    
    public void SetStrength(int strength)
    {
        strength_ = strength;
    }
    
    public void SetInitiative(int initiative)
    {
        initiative_ = initiative;
    }
    
    public void SetPosition(Position newPosition)
    {
        position_ = newPosition;
    }
    
    public int GetStrength()
    {
        return strength_;
    }
    
    public int GetInitiative()
    {
        return initiative_;
    }
    
    public Position GetPosition()
    {
        return position_;
    }
    
    public int GetInstanceNumber()
    {
        return instanceNumber_;
    }
    
    public boolean isAlive()
    {
        return isAlive_;
    }
    
    protected World GetWorld()
    {
        return world_;
    } 
    
    public Color GetColor()
    {
        return Color.green;
    }

    public void Save(Saver saver) 
    {
       saver.Print(Boolean.toString(isAlive_));
       saver.Print(Integer.toString(instanceNumber_));
       saver.Print(Integer.toString(strength_));
       saver.Print(Integer.toString(initiative_));
    }
    
    public static Comparator<Organism> OrganismInitiativeComparator = new Comparator<Organism>(){

        @Override
        public int compare(Organism a, Organism b) 
        {
            if(a.GetInitiative() == b.GetInitiative())
                return Integer.compare(b.GetInstanceNumber(), a.GetInstanceNumber());
            return Integer.compare(b.GetInitiative(), a.GetInitiative());
        }  
    };
}
    
            
   
