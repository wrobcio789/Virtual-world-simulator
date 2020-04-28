package utilities.position;

import utilities.MyRandom;
import utilities.savingsystem.Loader;
import utilities.savingsystem.Saver;

public abstract class Position 
{
    protected static MyRandom generator = new MyRandom();
    
    public abstract int Distance(Position other);
    public abstract Position Add(Position other);
    
    public abstract void Save(Saver saver);
    public abstract Position Load(Loader loader);
    
    public abstract Position[] GetNeighbours();
    public abstract Position GetNeighbouringCloserPosition(Position destination);
    public abstract boolean IsInBoundaries(int width, int height);
    public abstract Position SetRandom(int width, int height);
    public abstract Position GetMovement(int keyCode);
    public abstract Position GetRandomNeighbour();
    
    
    @Override
    public abstract int hashCode();
    @Override
    public abstract boolean equals(Object obj);
    @Override
    public abstract String toString();
    
}
