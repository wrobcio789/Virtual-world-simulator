package utilities.readyclasses;

import Interfaces.Requirements;
import organism.Organism;

public class NoRequirements implements Requirements
{
    @Override
    public boolean IsOk(Organism organism)
    {
        return true;
    }
}
