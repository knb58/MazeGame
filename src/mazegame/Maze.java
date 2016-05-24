package mazegame;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.Timer;


/**
 * This class randomly generates a maze and paints it to the frame.
 * @author Kirsten Baker, Todd Locker
 */
@SuppressWarnings("serial")
public class Maze extends JComponent {
	
    private int gameTime = 0;
    private Timer myTimer;
	
    private boolean corners[][], hWalls[][], vWalls[][];
    private boolean north=false, south=false, east=false, west=false;
    private boolean play2=false;
    
    private int xLoc=0, yLoc=0, moveCounter=0, move2Counter=0;
    private int xStart=0, yStart=0, x2Start=0, y2Start=0; 
    private int size, selectedColor, selected2Color, modeSel=0;
    private double xLen=0, length=0;
    
    RectangleComponent us, us2;
    KeyPressListener mover = new KeyPressListener();
    
    Maze(int diff, int col, int col2, int mode, int x) {
    	
        myTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                gameTime++;
            }
        });
    	myTimer.start();
        
        modeSel=mode;
        xLen = x;
        selectedColor = col;
        if(col2!=0){
            play2=true;
            selected2Color=col2-1;
        }
        switch (diff){
            case 0: size=10; break;
            case 1: size=25; break;
            case 2: size=50; break;
            case 3: size=75; break;
            case 4: size=100; break;
        }
        length = x/size;
        graph(size);
        addKeyListener(mover);
        setFocusable(true);
    }
    
    public int rand(int x) {
        Random random = new Random();
        int r = random.nextInt(x);
        return r;
    }

    public void graph(int size) {
        corners = new boolean[size+1][size+1];
        hWalls = new boolean[size+1][size+1];
        vWalls = new boolean[size+1][size+1];

        for (int i = 0; i < size+1; i++) 
            for (int j = 0; j < size+1; j++) {
                corners[i][j] = false;
                hWalls[i][j] = true;
                vWalls[i][j] = true;
            }
        
        yLoc = yStart= y2Start = rand(size);
        corners[0][yLoc] = true;
        int direc = rand(4);
        char d = 0;
        while (xLoc < size-1) {
            switch (direc) {
                case 0: d = 'n'; break;
                case 1: d = 's'; break;
                case 2: d = 'e'; break;
                case 3: d = 'w'; break;
            }
            
            if (d == 'n') {
                if (yLoc > 0 && !corners[xLoc][yLoc-1]) {
                    hWalls[xLoc+1][yLoc] = false;
                    yLoc--;
                    corners[xLoc][yLoc] = true;
                }
            } 
            else if (d == 's') {
                if (yLoc < size-1 && !corners[xLoc][yLoc+1]) {
                    yLoc++;
                    hWalls[xLoc+1][yLoc] = false;
                    corners[xLoc][yLoc]=true;
                }
            } 
            else if (d == 'e') {
                if (xLoc < size-1 && !corners[xLoc+1][yLoc]) {
                    xLoc++;
                    vWalls[xLoc][yLoc + 1] = false;
                    corners[xLoc][yLoc]=true;
                }
            } 
            else if (d == 'w') {
                if (xLoc > 0 && !corners[xLoc-1][yLoc]) {
                    vWalls[xLoc][yLoc + 1] = false;
                    xLoc--;
                    corners[xLoc][yLoc]=true;
                }
            }
            
            if (trapped(xLoc, yLoc, size)) {
                int possX, possY;
                boolean exist=false;
                while (!exist){
                    possY = rand(size);
                    possX = rand(size);
                    if (corners[possX][possY]) {
                        xLoc = possX;
                        yLoc = possY;
                        exist=true;
                    }
                }
            }
            direc = rand(4);
        }
        vWalls[xLoc][yLoc+1]=false;
    }

    public boolean trapped(int xLoc, int yLoc, int size) {
        if (yLoc != 0)
            north = corners[xLoc][yLoc-1];
        if (yLoc < size-1)
            south = corners[xLoc][yLoc+1];
        if (xLoc < size-1)
            east = corners[xLoc+1][yLoc];
        if (xLoc != 0)
            west = corners[xLoc-1][yLoc];
        
        if (xLoc == 0) {
            if (yLoc == 0) {
                if (east && south) 
                    return true;
            } 
            else if (yLoc == size-1) {
                if (north && east) 
                    return true;
            } 
            else {
                if (north && east && south) 
                    return true;
            }
        } 
        else {
            if (yLoc == 0) {
                if (east && south && west)
                    return true;
            } 
            else if (yLoc == size-1){
                if (north && east && west)
                    return true;
            } 
            else {
                if (north && east && south && west)
                    return true;
            }
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Line2D.Double hseg, vseg; 
        Rectangle2D no;
        length = xLen/size;
        
        
        for (int i=size-1; i >= 0; i--)
            for (int j=size; j>= 0; j--) 
                if(!corners[i][j]){
                    no=new Rectangle2D.Double(i*length, j*length, length, length);
                    g2.setColor(Color.BLACK);
                    g2.fill(no);
                }
        //make the horizontal walls and vertical walls
        for (int i=0; i < size+1; i++)
            for (int j=0; j < size+1; j++) {
                if(hWalls[i][j]) {
                    hseg= new Line2D.Double(i*length, j*length, (i-1)*length, j*length); 
                    g2.setStroke(new BasicStroke(2));
                    g2.draw(hseg);
                }   
                if(vWalls[i][j]) {
                    vseg= new Line2D.Double(i*length, j*length, i*length, (j-1)*length);
                    g2.draw(vseg);
                }              
            } 
        //create the blackout and flashlight
        if (modeSel > 0) {
            Area a = new Area(new Rectangle2D.Double(0, 0, xLen, xLen));
            if (modeSel == 1) {
                a.subtract(new Area(new Ellipse2D.Double(((xStart - 5.5) * length) - 4 * length, ((yStart - 5.5) * length) - 4 * length, length * 20, length * 20)));
            } 
            else if (modeSel == 2) {
                a.subtract(new Area(new Ellipse2D.Double(((xStart - 2.5) * length) - 2 * length, ((yStart - 2.5) * length) - 2 * length, length * 10, length * 10)));
            } 
            else {
                a.subtract(new Area(new Ellipse2D.Double(((xStart - 1) * length) - length, ((yStart - 1) * length) - length, length * 5, length * 5)));
            }
            if (play2) {
                if (modeSel == 1) 
                    a.subtract(new Area(new Ellipse2D.Double(((x2Start - 5.5) * length) - 4 * length, ((y2Start - 5.5) * length) - 4 * length, length * 20, length * 20)));
                else if (modeSel == 2) 
                    a.subtract(new Area(new Ellipse2D.Double(((x2Start - 2.5) * length) - 2 * length, ((y2Start - 2.5) * length) - 2 * length, length * 10, length * 10)));
                else 
                    a.subtract(new Area(new Ellipse2D.Double(((x2Start - 1) * length) - length, ((y2Start - 1) * length) - length, length * 5, length * 5)));
            }
            g2.fill(a);
        }
        //create the players
        us=new RectangleComponent(xStart, yStart, length, selectedColor);
        us.paintComponent(g);
        if (play2){
            us2=new RectangleComponent(x2Start, y2Start, length, selected2Color);
            us2.paintComponent(g);
        }
    }
    class KeyPressListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
                new MazeCompleted(moveCounter, -1, -1, gameTime);
                getRootPane().getParent().setVisible(false);
            }
            else{
            String m="";
            String m2="";
            if (!play2){
                switch (event.getKeyCode()) {
                    case 87: m = "n"; moveCounter++; break;
                    case 38: m = "n"; moveCounter++; break;
                    case 83: m = "s"; moveCounter++; break;
                    case 40: m = "s"; moveCounter++; break;
                    case 68: m = "e"; moveCounter++; break;
                    case 39: m = "e"; moveCounter++; break;
                    case 65: m = "w"; moveCounter++; break;
                    case 37: m = "w"; moveCounter++; break;
                    default: break;
                }
                switch(m){
                    case "n": 
                        if(!hWalls[xStart+1][yStart])
                            yStart--;
                        break;
                    case "s": 
                        if(!hWalls[xStart+1][yStart+1])
                            yStart++;
                        break;
                    case "e": 
                        if(!vWalls[xStart+1][yStart+1])
                            xStart++;
                        else if(xStart+1 == size) {
                            getRootPane().getParent().setVisible(false);
                            new MazeCompleted(moveCounter, -1, 0, gameTime);
                        }
                        break;
                    case "w": 
                        if(!vWalls[xStart][yStart+1])
                            xStart--;
                        break; 
                    default: break;
                }
            }
            else{
                switch (event.getKeyCode()) {
                    case 87: m2 = "n"; move2Counter++; break;
                    case 38: m = "n"; moveCounter++; break;
                    case 83: m2 = "s"; move2Counter++; break;
                    case 40: m = "s"; moveCounter++; break;
                    case 68: m2 = "e"; move2Counter++; break;
                    case 39: m = "e"; moveCounter++; break;
                    case 65: m2 = "w"; move2Counter++; break;
                    case 37: m = "w"; moveCounter++; break;
                    default: break;
                }
                switch(m2){
                    case "n": 
                        if(!hWalls[x2Start+1][y2Start])
                            y2Start--;
                        break;
                    case "s": 
                        if(!hWalls[x2Start+1][y2Start+1])
                            y2Start++;
                        break;
                    case "e": 
                        if(!vWalls[x2Start+1][y2Start+1])
                            x2Start++;
                        else if(x2Start+1 == size) {
                            getRootPane().getParent().setVisible(false);
                            new MazeCompleted(moveCounter, move2Counter, 2, gameTime);
                        }
                        break;
                    case "w": 
                        if(!vWalls[x2Start][y2Start+1])
                            x2Start--;
                        break; 
                    default: break;
                }
                switch(m){
                    case "n": 
                        if(!hWalls[xStart+1][yStart])
                            yStart--;
                        break;
                    case "s": 
                        if(!hWalls[xStart+1][yStart+1])
                            yStart++;
                        break;
                    case "e": 
                        if(!vWalls[xStart+1][yStart+1])
                            xStart++;
                        else if(xStart+1 == size) {
                            getRootPane().getParent().setVisible(false);
                            new MazeCompleted(moveCounter, move2Counter, 1, gameTime);
                        }
                        break;
                    case "w": 
                        if(!vWalls[xStart][yStart+1])
                            xStart--;
                        break; 
                    default: break;
                }
            }
           repaint();
        }
        }
        
        public void keyTyped(KeyEvent event) {}
        public void keyReleased(KeyEvent event) {}
    }
}
