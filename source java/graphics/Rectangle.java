package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;


public class Rectangle extends JComponent
{
    private final int x_;
    private final int y_;
    private final int width_;
    private final int height_;
    private final Color color_;
    
    public Rectangle(int x, int y, int width, int height, Color color)
    {
        this.x_ = x;
        this.y_ = y;
        this.width_ = width;
        this.height_ = height;
        this.color_ = color;
        System.out.println("New rect created");
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color_);
        g2d.fillRect(x_, y_, width_, height_);
        System.out.println("Rect painted");
        /*g2d.setColor(Color.black);
        g2d.drawString("HELLO WHATS UP", x_, y_);*/
    }
}
