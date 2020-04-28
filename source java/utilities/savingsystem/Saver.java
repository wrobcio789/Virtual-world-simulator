package utilities.savingsystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Saver 
{
    private final BufferedWriter writer;
    
    public Saver(String filepath) throws IOException
    {
        writer = new BufferedWriter(new FileWriter(filepath, false));
    }
    
    public void Print(String string)
    {
        try
        {
            writer.append(' ');
            writer.append(string);
        }
        catch(Exception e){
            System.out.println("Nie powinno się to wypisać");}
    }
    
    public void InsertPrefix(String prefix)
    {
        Print('\n' + prefix);
    }
    
    public void Flush()
    {
        try{
            writer.flush();
        }catch(Exception e){
            System.out.println("Nie powinno się wypisać");}
    }
}
