package mazegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

/**
 * This class creates the rectangle that marks the current location of the player in the maze.
 * @author Kirsten Baker, Todd Locker
 */
@SuppressWarnings("serial")
public class RectangleComponent extends JComponent {
    private final int xStart, yStart, selectedColor;
    private final double length;

    public RectangleComponent(int xS, int yS, double l, int sColor){
        xStart = xS; yStart = yS;
        length = l;
        selectedColor = sColor;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D box = new Rectangle2D.Double((xStart*length)+1, (yStart*length)+1, length-2, length-2);
        switch (selectedColor) {
            case 0: g2.setColor(Color.BLUE); break;
            case 1: g2.setColor(Color.CYAN); break;
            case 2: g2.setColor(Color.GREEN); break;
            case 3: g2.setColor(Color.ORANGE); break;
            case 4: g2.setColor(Color.PINK); break;
            case 5: g2.setColor(Color.MAGENTA); break;
            case 6: g2.setColor(Color.RED);  break;
            case 7: g2.setColor(Color.YELLOW); break;
        }
        g2.fill(box); 
    }
}
