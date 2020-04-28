package organism.manager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public enum Organisms 
{
    HUMAN,
    
    ANTELOPE,
    FOX,
    SHEEP,
    CYBERSHEEP,
    TURTLE,
    WOLF,
    
    BELLADONNA,
    DANDELION,
    GRASS,
    GUARANA,
    SOSNOWSKYS_HOGWEED;
   
    private static final List<Organisms> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    private static final Organisms[] VALUES_MINUS_HUMAN = Arrays.copyOfRange(values(), 1, values().length);
    
    public static Organisms GetRandomOrganism()
    {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
    
    public static Organisms[] GetValuesMinusHuman()
    {
        return VALUES_MINUS_HUMAN;
    }
}
