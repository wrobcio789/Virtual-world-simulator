package utilities.savingsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Loader
{
    private static Scanner scanner;
    private static File file;

    public Loader(String filepath) throws FileNotFoundException
    {
        file = new File(filepath);
        scanner = new Scanner(file);
    }
    
    public int NextInt()
    {
        return scanner.nextInt();
    }
    
    public String NextString()
    {
        return scanner.next();
    }
    
    public boolean NextBoolean()
    {
        return scanner.nextBoolean();
    }
    
    public boolean GoTo(String linePrefix)
    {
        try{
            scanner = new Scanner(file);
        }
        catch(Throwable e)
        {   System.out.println("Jak ta linijka się wydrukuje to ja jestem święty"); }
        
        
        while(scanner.hasNextLine())
        {
            if(linePrefix.equals(scanner.next()))
                return true;
        }
        return false;
    }
    
}
