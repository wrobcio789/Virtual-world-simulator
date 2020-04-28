package utilities.readyclasses;

import Interfaces.Requirements;
import organism.Organism;

public class IsEmptyRequirement implements Requirements
{
    @Override
    public boolean IsOk(Organism organism)
    {
        return organism == null;
    }
}
