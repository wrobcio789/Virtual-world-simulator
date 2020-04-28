package utilities.position;

import com.sun.glass.events.KeyEvent;
import utilities.savingsystem.Loader;
import utilities.savingsystem.Saver;

public class Point extends Position
{
    private static Point[] neighbours_ = {new Point(-1, 0), new Point(1, 0), new Point(0, 1), new Point(0, - 1)};
    
    public int x;
    public int y;
    
    public Point()
    {
        this.x = this.y = 0;
    }
    
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public int Distance(Position other)
    {
        if(!(other instanceof Point))
            return -1;
        
        Point otherPoint = (Point)(other);
        //if(metrics == WorldMode.SQUARE)
            return (Math.abs(this.x - otherPoint.x) + Math.abs(this.y - otherPoint.y));
       // else if(metrics == WorldMode.HEXAGONS)
          //  return (Math.abs(this.x - other.x) + Math.abs(this.x + this.y - other.x - other.y) + Math.abs(this.y - other.y)) / 2;
       // else
         //   return -1;
    }
    
    @Override
    public Point Add(Position other)
    {
        Point otherPoint = (Point)(other);
        return new Point(x + otherPoint.x, y + otherPoint.y);
    }
    
    @Override
    public void Save(Saver saver)
    {
        saver.Print(Integer.toString(x));
        saver.Print(Integer.toString(y));
    }
    
    @Override
    public Point Load(Loader loader)
    {
        x = loader.NextInt();
        y = loader.NextInt();
        return this;
    }
    
    @Override
    public int hashCode()
    {
        return (x + y) * (x + y + 1) / 2 + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Point other = (Point) obj;
        return (other.x == this.x && other.y == this.y);
    }
    
    @Override
    public String toString()
    {
        return "Point (" + x + ", " + y + ")";
    }

    @Override
    public Position GetNeighbouringCloserPosition(Position destination) 
    {
        if(!(destination instanceof Point))
                System.out.println("Gdzieś popełniłem bląd");
        
        int distance = this.Distance(destination);
        
        for(Point neighbour : neighbours_)
        {
            int newDistance = (this.Add(neighbour)).Distance(destination);
            if(distance > newDistance)
                return this.Add(neighbour);
        }
        
        return this;
    }

    @Override
    public boolean IsInBoundaries(int width, int height) 
    {
       return x >= 0 && x < width && y >= 0 && y < height;
    }

    @Override
    public Point[] GetNeighbours() 
    {
        Point neighbours[] = new Point[neighbours_.length];
        for(int i = 0; i < neighbours_.length; i++)
            neighbours[i] = this.Add(neighbours_[i]);
        return neighbours;
    }
    
    @Override
    public Point SetRandom(int width, int height)
    {
        x = generator.nextInt(width);
        y = generator.nextInt(height);
        return this;
    }
    
    @Override
    public Point GetMovement(int keyCode)
    {
        switch(keyCode)
        {
            case KeyEvent.VK_UP:
                return this.Add(new Point(0, -1));
            case KeyEvent.VK_DOWN:
                return this.Add(new Point(0, 1));
            case KeyEvent.VK_LEFT:
                return this.Add(new Point(-1, 0));
            case KeyEvent.VK_RIGHT:
                return this.Add(new Point(1, 0));
        }
        
        return null;
    }

    @Override
    public Position GetRandomNeighbour() {
        return this.Add(neighbours_[generator.nextInt(neighbours_.length)]);
    }
}
