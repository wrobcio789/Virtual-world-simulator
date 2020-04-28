package utilities.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Keyboard 
{
    private static final ArrayList<KeyPressedListener> keyPressedListeners_ = new ArrayList<>();
    private static final KeyListener keyListener_ = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                for(KeyPressedListener listener : keyPressedListeners_)
                    listener.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
               
            }
        };
    
    public static void AddKeyPressedListener(KeyPressedListener listener)
    {
        keyPressedListeners_.add(listener);
    }
    
    public static KeyListener GetKeyboardListener()
    {
        return keyListener_;
    }
}
