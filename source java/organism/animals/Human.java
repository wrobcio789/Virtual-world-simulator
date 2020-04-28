package organism.animals;

import java.awt.Color;
import java.awt.event.KeyEvent;
import organism.CollisionResult;
import organism.Organism;
import organism.manager.Organisms;
import utilities.MyRandom;
import utilities.input.KeyPressedListener;
import utilities.input.Keyboard;
import utilities.position.Position;
import utilities.savingsystem.Loader;
import utilities.savingsystem.Saver;
import virtualworld.World;


public class Human extends Animal
{
    static final int STRENGTH = 5;
    static final int INITIATIVE = 4;
    
    static final int ABILITY_WAITING_TIME = 10;
    static final int ABILITY_FIRT_PHASE_TIME = 3;
    static final int ABILITY_SECOND_PHASE_TIME = 5;
    static final int ABILITY_MOVES = 2;
    static final int ABILITY_SECOND_PHASE_CHANCE = 50;
    
    static final int HUMAN_ABILITY_KEY = KeyEvent.VK_S;
    static final int MAX_KEY_PRESSED_COUNT = 3;
    static final int NO_KEY = -1000;
    
    private int timeSinceAbilityActivated_;
    private int keysPressed[] = {NO_KEY, NO_KEY, NO_KEY};
    private int keysPressedCount = 0;

    public Human(Position position, World world) 
    {
        super(STRENGTH, INITIATIVE, position, world);
        timeSinceAbilityActivated_ = ABILITY_WAITING_TIME + 1;
        AddKeyboardListener();
    }
    
    public Human(Loader loader, World world, Position position)
    {
        super(loader, world, position);
        timeSinceAbilityActivated_  = loader.NextInt();
        AddKeyboardListener();
    }
    
    private void AddKeyboardListener()
    {
        Keyboard.AddKeyPressedListener(new KeyPressedListener()
        {
            @Override
            public void keyPressed(KeyEvent e) 
            {
               
               if(keysPressedCount == MAX_KEY_PRESSED_COUNT)
               {
                   for(int i = 1; i<keysPressedCount; i++)
                       keysPressed[i - 1] = keysPressed[i];
                   keysPressed[MAX_KEY_PRESSED_COUNT - 1] = e.getKeyCode(); 
               }
               else
                   keysPressed[keysPressedCount++] = e.getKeyCode();
            }
            
        });
    }

    @Override
    public void Act()
    {
        IncreaseAbilityCounts();
        int moves = 1;
        if (timeSinceAbilityActivated_ < ABILITY_FIRT_PHASE_TIME || (timeSinceAbilityActivated_ < ABILITY_SECOND_PHASE_TIME && (new MyRandom()).Chance(ABILITY_SECOND_PHASE_CHANCE)))
		moves = ABILITY_MOVES;
        
        while(moves != 0 && isAlive())
        {
            int key = GetKey();
            if(key == NO_KEY)
                break;
            
            Position newPosition;
            if(key == HUMAN_ABILITY_KEY && ActivateSpecialAbility())
                moves += ABILITY_MOVES - 1;
            else
            {
                newPosition = GetPosition().GetMovement(key);
                if(newPosition == null)
                    break;

                if(GetWorld().IsInBoundary(newPosition))
                    GoTo(newPosition);
                
                moves--;
            }
        }
        
        keysPressedCount = 0;
    }
    
    @Override
    public CollisionResult Collide(Organism organism)
    {
        return CollisionResult.FIGHT;
    }
    
    @Override
    public void Save(Saver saver)
    {
        super.Save(saver);
        saver.Print(Integer.toString(timeSinceAbilityActivated_));
    }
    
    private boolean ActivateSpecialAbility()
    {
        if (timeSinceAbilityActivated_  >= ABILITY_WAITING_TIME)
	{
            timeSinceAbilityActivated_ = 0;
            GetWorld().AddToLogs(toString() + " activated special ability");
            return true;
	}
        GetWorld().AddToLogs(toString() + " can't activate special ability");
	return false;
    }
    
    private int GetKey()
    {
        if(keysPressedCount == 0)
            return NO_KEY;
        
        int ret = keysPressed[0];
        for(int i = 1; i<keysPressedCount; i++)
           keysPressed[i - 1] = keysPressed[i];
        return ret;
    }
    
    private void IncreaseAbilityCounts()
    {
        timeSinceAbilityActivated_ ++;
    }
    
    @Override
    public Organisms GetEnum() 
    {
        return Organisms.HUMAN;
    }
    
    @Override
    public Color GetColor()
    {
        return Color.ORANGE;
    }
    
    @Override
    public String toString()
    {
        return "Human";
    }

}
