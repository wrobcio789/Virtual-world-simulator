package virtualworld;

import Interfaces.Refreshable;
import Interfaces.Requirements;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import organism.Organism;
import organism.animals.Animal;
import organism.manager.OrganismManager;
import organism.manager.Organisms;
import utilities.MyRandom;
import utilities.position.Hex;
import utilities.position.Point;
import utilities.position.Position;
import utilities.savingsystem.Loader;
import utilities.savingsystem.Saver;

public final class World 
{
    private static final int LOGS_LENGTH = 27;
    
    private static final int BOARD_X = 30;
    private static final int BOARD_Y = 30;
    private static final int TILE_SIZE = 30;
    private static final int HEX_SIZE = 18;
    private static final int BOARD_WIDTH = 700;
    private static final int BOARD_HEIGHT = 700;
    
    private int width_;
    private int height_;
    private WorldMode worldMode_;
    private final HashMap<Position, Organism> organismsPO = new HashMap<>();
    private final HashSet<Organism> RemovalSet = new HashSet<>();
    private final OrganismManager manager = new OrganismManager();
    private final LogSystem logSystem_ = new LogSystem(LOGS_LENGTH);
    
    private Refreshable refreshable_;
    
    public World(int width, int height, WorldMode worldMode)
    {
       Reset(width, height, worldMode);
    }
    
    public void SetRefreshable(Refreshable refreshable)
    {
        refreshable_ = refreshable;
    }
    
    public void Act()
    {
        ArrayList<Organism> organisms = GetElementsSortedByInitiative();
        
        for(Organism organism : organisms)
        {
            
            if(!organism.isAlive() || RemovalSet.contains(organism))
                continue;
            organism.Act();
        }
        
        EmptyRemovalSet();
        
        if(refreshable_ != null)
            refreshable_.Refresh();
    }
    
    public void Reset(int width, int height, WorldMode worldMode)
    {
        organismsPO.clear();
        width_ = width;
        height_ = height;
        worldMode_ = worldMode;
        logSystem_.Clear();
        AddToLogs("Zapis akcji:");
    }
    
    public boolean IsInBoundary(Position position)
    {
        return position.IsInBoundaries(width_, height_);
    }
    
    public Position FindNearestPosition(Position position, Requirements req)
    {
        Position result = null;
        int lowestDistance = Integer.MAX_VALUE;
        
        for(int i = 0; i < height_; i++)
            for(int j = 0; j < width_; j++)
            {
                Position tmpPosition;
                if(worldMode_ == WorldMode.SQUARE)
                    tmpPosition = new Point(j, i);
                else
                    tmpPosition = new Hex(j, i);
                
                if(tmpPosition.equals(position))
                    continue;
                
                int currentDistance = position.Distance(tmpPosition);
                if(req.IsOk(organismsPO.get(tmpPosition)) && currentDistance < lowestDistance)
                {
                    result = tmpPosition;
                    lowestDistance = currentDistance;
                }
            }
        
        return result;
    }
    
    public Position GetRandomPlaceInNeighbourhood(Position position, Requirements req)
    {
        Position NearestPosition = FindNearestPosition(position, req);
        if(NearestPosition == null || NearestPosition.Distance(position) > 1)
            return null;
        
        
        MyRandom generator = new MyRandom();
        Position result;   
        do
        {
            result = position.GetRandomNeighbour();
        }while(!IsInBoundary(result) || !req.IsOk(organismsPO.get(result)) || result.equals(position));
        
        return result;
    }
    
    private void MoveInternally(Organism organism, Position position)
    {
        Position organismOldPosition = organism.GetPosition();
        if(organismOldPosition.equals(position))
            return;
        organism.SetPosition(position);
        organismsPO.remove(organismOldPosition);
        organismsPO.remove(position);
        organismsPO.put(position, organism);
    }
    
    public void MoveAnimal(Animal animal, Position position)
    {
        if(!IsInBoundary(position))
        {
            System.out.println("Trying to move animal to place out of this world.");
            return;
        }
        
        if(organismsPO.get(position) == null || (animal.RunCollision(organismsPO.get(position)) && animal.isAlive()))
            MoveInternally(animal, position);   
    }
    
    public void SpawnOrganisms(int organisms)
    {
        MyRandom generator = new MyRandom();
        
        
        for(int i = 0; i < organisms; i++)
        {  
            Position position;
            if(worldMode_ == WorldMode.SQUARE)
                position = new Point();
            else
                position = new Hex();
            
            do
                position.SetRandom(width_, height_);
            while(organismsPO.get(position) != null);
            
            if(i == 0)
                organismsPO.put(position, manager.CreateOrganism(Organisms.HUMAN, this, position));
            else
            {
                Organisms orgEnum;
                do
                    orgEnum = Organisms.GetRandomOrganism();
                while(orgEnum == Organisms.HUMAN);
                
                Organism newOrganism = manager.CreateOrganism(orgEnum, this, position);
                organismsPO.put(position, newOrganism);
            }
        }
    }
    
    public void AddToRemovalSet(Organism organism)
    {
        RemovalSet.add(organism);
        Position position = organism.GetPosition();
        organismsPO.remove(position);
    }
    
    private void EmptyRemovalSet()
    {
        RemovalSet.clear();
    }
    
    public JPanel GetJPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        
        panel.add(GetJComponent());
        panel.add(logSystem_.GetJComponent());
        
        return panel;
    }
    
    private JComponent GetJComponent()
    {
        JComponent component = new JComponent()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if(worldMode_ == WorldMode.SQUARE)
                {
                    for(int i = 0; i<height_; i++)
                        for(int j = 0; j<width_; j++)
                        {
                            Color color = Color.LIGHT_GRAY;
                            String text = "";
                            Organism organism = organismsPO.get(new Point(j, i));

                            if(organism != null)
                            {
                                color = organism.GetColor();
                                text = organism.toString().substring(0, 2);
                            }

                            g2d.setColor(color);
                            g2d.fillRect(BOARD_X + j * TILE_SIZE, BOARD_Y + i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                            g2d.setColor(Color.GRAY);
                            g2d.drawRect(BOARD_X + j * TILE_SIZE, BOARD_Y + i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                            g2d.drawString(text, BOARD_X + j * TILE_SIZE + 7, BOARD_Y + i * TILE_SIZE + g2d.getFontMetrics().getHeight() + 4);
                        }
                }
                else
                {
                    for(int i = 0; i<height_; i++)
                        for(int j = 0; j<width_; j++)
                        {
                            Color color = Color.LIGHT_GRAY;
                            String text = "";
                            Organism organism = organismsPO.get(new Hex(j, i));
                            
                            if(organism != null)
                            {
                                color = organism.GetColor();
                                text = organism.toString().substring(0, 2);
                            }
                            
                            g2d.setColor(color);
                            
                            int w = (int) ((int) HEX_SIZE * Math.sqrt(3));
                            int h = HEX_SIZE * 2;
                            int xShift = BOARD_X + j*w + (i%2 == 1 ? w/2 : 0);
                            int yShift = BOARD_Y + i*3*h/4;
                            
                            int[] xPoints = new int[6];
                            int[] yPoints = new int[6];
                            xPoints[0] = -w/2;
                            yPoints[0] = -h/4;
                            xPoints[1] = 0;
                            yPoints[1] = -h/2;
                            xPoints[2] = w/2;
                            yPoints[2] = -h/4;
                            xPoints[3] = w/2;
                            yPoints[3] = h/4;
                            xPoints[4] = 0;
                            yPoints[4] = h/2;
                            xPoints[5] = -w/2;
                            yPoints[5] = h/4;
                            for(int k = 0; k < 6; k++)
                            {
                                yPoints[k] += yShift;
                                xPoints[k] += xShift;
                            }
                            
                            g2d.fillPolygon(xPoints, yPoints, 6);
                            g2d.setColor(Color.GRAY);
                            g2d.drawPolygon(xPoints, yPoints, 6);
                            g2d.drawString(text, xShift - 8, yShift + 3);
                            
                        }
                }
            }
        };
        
        component.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        component.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e){
                
                Position position = GetPositionByScreenCoord(e.getPoint());
                if(!IsInBoundary(position))
                    return;
                
                System.out.println(position);
                if(organismsPO.get(position) == null)
                {
                    Organisms orgEnum = (Organisms)JOptionPane.showInputDialog(
                            SwingUtilities.getWindowAncestor(component),
                            "Wybierz organizm do stworzenia:",
                            "Nowy organizm",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            Organisms.GetValuesMinusHuman(), 
                            Organisms.GetValuesMinusHuman()[0]);
                    
                    if(orgEnum == null)
                        return;
                    
                    CreateOrganism(orgEnum, position);
                    if(refreshable_ != null)
                        refreshable_.Refresh();
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            
        });
        return component;
    }
    
    private ArrayList<Organism> GetElementsSortedByInitiative()
    {
        ArrayList<Organism> result = new ArrayList(organismsPO.values());
        result.sort(Organism.OrganismInitiativeComparator);
        return result;
    }
    
    public void CreateOrganism(Organisms orgEnum, Position position)
    {
        Organism newOrganism = manager.CreateOrganism(orgEnum, this, position);
        organismsPO.put(position, newOrganism);
    }
    
    public void Save(Saver saver)
    {
        saver.InsertPrefix("#WIDTH");
        saver.Print(Integer.toString(width_));
        saver.InsertPrefix("#HEIGHT");
        saver.Print(Integer.toString(height_));
        saver.InsertPrefix("#WORLDMODE");
        saver.Print(worldMode_.toString());
        saver.InsertPrefix("#ORGANISMS_NUMBER");
        saver.Print(Integer.toString(organismsPO.size()));
        
        saver.InsertPrefix("#ORGANISMS");
        for(int i = 0; i<height_; i++)
            for(int j = 0; j<width_; j++)
            {
                Position position;
                if(worldMode_ == WorldMode.SQUARE)
                    position = new Point(j, i);
                else
                    position = new Hex(j, i);
                
                if(organismsPO.get(position) == null)
                    continue;
                
                position.Save(saver);
                manager.SaveOrganism(organismsPO.get(position), saver);
                    
            }
    }
    
    public void Load(Loader loader)
    {
        int width, height;
        loader.GoTo("#WIDTH");
        width = loader.NextInt();
        loader.GoTo("#HEIGHT");
        height = loader.NextInt();
        loader.GoTo("#WORLDMODE");
        
        Reset(width, height, WorldMode.valueOf(loader.NextString()));
        
        loader.GoTo("#ORGANISMS_NUMBER");
        int organismsNumber = loader.NextInt();
        
        loader.GoTo("#ORGANISMS");
        for(int i = 0; i < organismsNumber; i++)
        {
            
            Position position;
            if(worldMode_ == WorldMode.SQUARE)
                position = (new Point()).Load(loader);
            else
                position = (new Hex()).Load(loader);
            organismsPO.put(position, manager.LoadOrganism(loader, this, position));
        }
    }
    
    public Organism GetOrganism(Position position)
    {
        return organismsPO.get(position);
    }
    
    public void AddToLogs(String message)
    {
        logSystem_.Log(message);
    }
    
    private Position GetPositionByScreenCoord(java.awt.Point Point)
    {
        if(worldMode_ == WorldMode.SQUARE)
        {
            int x = (Point.x - BOARD_X)/TILE_SIZE;
            int y = (Point.y - BOARD_Y)/TILE_SIZE;
            return new Point(x, y);
        }
             
        int w = (int) (HEX_SIZE * Math.sqrt(3));
        int h = HEX_SIZE * 2;
        int x = Point.x - BOARD_X + w/2;
        int y = Point.y - BOARD_Y + h/2;
        h = 3*h/4;
        
        int row = y/h;
        int column;
        if(row%2 == 1)
            column = (x - w/2)/w;
        else
            column = x / w;
        
        double relY = y - row*h;
        double relX;
        if(row%2 == 1)
            relX = x - column * w - w / 2;
        else
            relX = x - column * w;
        
        double m = Math.tan(Math.toRadians(30));
        double c =  m * w / 2;
        if(relY < (-m * relX) + c)
        {   
            if(row%2 == 0)
                column--;
            row--;
        }
        else if(relY < (m*relX) - c)
        {
            if(row%2 == 1)
                column++;
            row--;
        }
        
        return new Hex(column, row);
    }
}
