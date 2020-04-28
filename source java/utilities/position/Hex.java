package utilities.position;

import com.sun.glass.events.KeyEvent;
import utilities.savingsystem.Loader;
import utilities.savingsystem.Saver;

public class Hex extends Position
{
    private static Hex[] neighbours_ = {
        new Hex(0, -1), new Hex(1, -1), new Hex(1, 0),
        new Hex(0, 1), new Hex(-1, 1), new Hex(-1, 0)};
    
    public int x;
    public int y;
    public int z;
    
    public Hex()
    {
        x = y = z = 0;
    }
    
    public Hex(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Hex(int x, int y)
    {
        ToHex(x, y);
    }
    
    private void ToHex(int x, int y)
    {
        this.x = x;
        this.z = y;
        this.y = -this.x - this.z;
    }
    
    public Point ToPoint()
    {
        return new Point(x, z);
    }
    
    @Override
    public int Distance(Position other) 
    {
        if(!(other instanceof Hex))
            return -1;
        
        Hex otherHex = (Hex)other;
        
        return (Math.abs(x - otherHex.x) + Math.abs(y - otherHex.y) + Math.abs(z - otherHex.z)) / 2;
    }

    @Override
    public Hex Add(Position other) 
    {
        Hex hex = (Hex)(other);
        return new Hex(x + hex.x, y + hex.y, z + hex.z);
    }

    @Override
    public void Save(Saver saver) {
        saver.Print(Integer.toString(x));
        saver.Print(Integer.toString(y));
        saver.Print(Integer.toString(z));
    }

    @Override
    public Position Load(Loader loader) {
        x = loader.NextInt();
        y = loader.NextInt();
        z = loader.NextInt();
        return this;
    }

    @Override
    public Hex[] GetNeighbours() {
        Hex neighbours[] = new Hex[neighbours_.length];
        for(int i = 0; i < neighbours_.length; i++)
            neighbours[i] = this.Add(neighbours_[i]);
        return neighbours;
    }

    @Override
    public Position GetNeighbouringCloserPosition(Position destination) 
    {
        if(!(destination instanceof Hex))
                System.out.println("Gdzieś popełniłem bląd");
        
        int distance = this.Distance(destination);
        
        for(Hex neighbour : neighbours_)
        {
            int newDistance = (this.Add(neighbour)).Distance(destination);
            if(distance > newDistance)
                return this.Add(neighbour);
        }
        
        return this;
    }

    @Override
    public boolean IsInBoundaries(int width, int height) {
        return ToPoint().IsInBoundaries(width, height);
    }

    @Override
    public Hex SetRandom(int width, int height) {
        Point p =  (new Point()).SetRandom(width, height);
        ToHex(p.x, p.y);
        return this;
    }

    @Override
    public Hex GetMovement(int keyCode) {      
        switch(keyCode)
        {
            
            
            case KeyEvent.VK_W:
                if(this.ToPoint().y%2==0)
                    return this.Add(new Hex(-1, -1));
                return this.Add(new Hex(0, 1, -1));
            case KeyEvent.VK_E:
                if(this.ToPoint().y%2==0)
                    return this.Add(new Hex(0, -1));
                return this.Add(new Hex(1, 0, -1));
            case KeyEvent.VK_D:
                return this.Add(new Hex(1, -1, 0));
            case KeyEvent.VK_X:
                if(this.ToPoint().y%2==0)
                    return this.Add(new Hex(0, 1));
                return this.Add(new Hex(1, 1));
            case KeyEvent.VK_Z:
                if(this.ToPoint().y%2==0)
                    return this.Add(new Hex(-1, 1));
                return this.Add(new Hex(0, 1));
            case KeyEvent.VK_A:
                return this.Add(new Hex(-1, 1, 0));
        }
        
        return null;
    }

    @Override
    public Hex GetRandomNeighbour() {
        return this.Add(neighbours_[generator.nextInt(neighbours_.length)]);
    }

    @Override
    public int hashCode() 
    {
        return this.ToPoint().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Hex other = (Hex) obj;
        return (other.x == this.x && other.y == this.y && other.z == this.z);
    }

    @Override
    public String toString() {
        return ToPoint().toString();
        //return "Hex (" + x + ", " + y + ", " + z + ")";
    }

}
