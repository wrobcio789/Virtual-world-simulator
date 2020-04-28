package virtualworld;

import Interfaces.Refreshable;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import utilities.input.Keyboard;
import utilities.savingsystem.Loader;
import utilities.savingsystem.Saver;

public class App extends JFrame implements Refreshable
{

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final String SAVE_EXTENTION = ".vwsave";

    private final World world;

    
    public static void main(String[] args) 
    {
        new App().Init();
    }

    public App() {
        super("Wirtualny świat - Maciej Wróblewski");
        world = new World(20, 20, WorldMode.HEXAGONS);

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    private void Init()
    {
        //Main panel added to frame:
        JPanel MainPanel = new JPanel();
        MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));

        //Keyboard support:
        MainPanel.addKeyListener(Keyboard.GetKeyboardListener());
        MainPanel.setFocusable(true);
        MainPanel.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                MainPanel.requestFocusInWindow();
            }

        });

        //World panel:
        JComponent worldPanel = world.GetJPanel();
        MainPanel.add(worldPanel);

        //User interface things:
        JPanel UIPanel = new JPanel();
        UIPanel.setLayout(new BoxLayout(UIPanel, BoxLayout.X_AXIS));
        //Buttons for UI:
        JButton nextTurnButton = new JButton("Next turn");
        UIPanel.add(nextTurnButton);
        JButton saveButton = new JButton("Save");
        UIPanel.add(saveButton);
        JButton loadButton = new JButton("Load");
        UIPanel.add(loadButton);
        JButton resetButton = new JButton("Reset");
        UIPanel.add(resetButton);

        //Setting callbacks:
        nextTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NextTurn();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Save();
            }

        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Load();
            }
        });
        resetButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Reset();
            }
        });
        

        //Adding everything together
        MainPanel.add(UIPanel);
        add(MainPanel);
        setVisible(true);
        
        Reset();
    }

    private void Reset()
    {
        world.SetRefreshable(this);
        world.SpawnOrganisms(1);
        
        WorldMode orgEnum = (WorldMode)JOptionPane.showInputDialog(
                            this,
                            "Wybierz tryb świata:",
                            "Tryb świata",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            WorldMode.values(), 
                            WorldMode.values()[0]);
                    
        if(orgEnum == null)
            orgEnum = WorldMode.SQUARE;
        
        int width, height;
        try{
            width = Integer.parseInt(JOptionPane.showInputDialog(this, "Podaj szerokość świata"));
        }catch(HeadlessException | NumberFormatException e) {
            width = 20;}
        
        try{
            height = Integer.parseInt(JOptionPane.showInputDialog(this, "Podaj wysokość świata"));
        }catch(HeadlessException | NumberFormatException e){
            height = 20;}
        
        if(width > 20 || width == 0)
            width = 20;
        if(height == 0 || height > 20)
            height = 20;
        
        world.Reset(width, height, orgEnum);
        world.SpawnOrganisms(Math.max(2, width*height/8));
        Refresh();
        
    }
    
    @Override
    public void Refresh() 
    {
        SwingUtilities.updateComponentTreeUI(this);
        //repaint();
    }

    private void NextTurn() {
        world.Act();
    }

    private void Save() {
        String filename = JOptionPane.showInputDialog(this, "Podaj nazwę pliku do zapisu") + SAVE_EXTENTION;

        Saver saver;
        try {
            saver = new Saver(filename);
            world.Save(saver);
            saver.Flush();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Wystąpił nieoczekiwany błąd");
        }

    }

    private void Load() {
        String filename = JOptionPane.showInputDialog(this, "Podaj nazwę pliku do wczytania") + SAVE_EXTENTION;

        Loader loader;
        try {
            loader = new Loader(filename);
            world.Load(loader);
            Refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Nie ma takiego pliku");
        }
    }

}
