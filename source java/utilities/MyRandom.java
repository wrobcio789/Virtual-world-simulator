package utilities;

import java.util.Random;

public class MyRandom extends Random
{
    public int nextInt(int a, int b)    //a,b inclusive
    {
        return nextInt(b - a + 1) + a;
    }
    
    public boolean Chance(int percent)
    {
        return nextInt(100) < percent;
    }
}
