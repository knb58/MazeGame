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
    
    // Maze info
    private boolean corners[][], hWalls[][], vWalls[][];
    private boolean north=false, south=false, east=false, west=false;
    private int xLoc=0, yLoc=0;
    private double xLen=0, length=0;
    
    // Player info
    private int Player1X=0, Player1Y=0, Player2X=0, Player2Y=0; 
    private boolean play2=false;
    private int size, selectedColor, selected2Color, modeSel=0;
    Player us, us2;
    PlayerMover mover = new PlayerMover();
     
     // Game complete info
    private int gameTime = 0, moveCounter=0, move2Counter=0;
    private Timer myTimer;
     
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
        
        yLoc = Player1Y= Player2Y = rand(size);
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
            mazeDirection(d);
            
            //if trapped, move to a another location to continue
            if (trapped(xLoc, yLoc, size)) {
                repositionMazeCreator();
            }
            direc = rand(4);
        }
        vWalls[xLoc][yLoc+1]=false;
    }
    
    //move maze creator in given direction
    public void mazeDirection(char d){
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
    }
    
    //if maze creator is trapped, move to another location to coninue
    public void repositionMazeCreator(){
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

    //see if the maze creator is trapped
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
                createBlackout(g2,a,Player1X,Player1Y);
            if (play2) {
                createBlackout(g2, a, Player2X, Player2Y);
            }
            g2.fill(a);
        }
        
        //create the players
        us=new Player(Player1X, Player1Y, length, selectedColor);
        us.paintComponent(g);
        if (play2){
            us2=new Player(Player2X, Player2Y, length, selected2Color);
            us2.paintComponent(g);
        }
    }
    
    //creates the blackout of the screen for the extreme modes
    public void createBlackout(Graphics2D g2, Area a, int playerX, int playerY){
        switch (modeSel) {
            case 1:
                a.subtract(new Area(new Ellipse2D.Double(((playerX - 5.5) * length) - 4 * length, ((playerY - 5.5) * length) - 4 * length, length * 20, length * 20)));
                break;
            case 2:
                a.subtract(new Area(new Ellipse2D.Double(((playerX - 2.5) * length) - 2 * length, ((playerY - 2.5) * length) - 2 * length, length * 10, length * 10)));
                break;
            case 3:
                a.subtract(new Area(new Ellipse2D.Double(((playerX - 1) * length) - length, ((playerY - 1) * length) - length, length * 5, length * 5)));
                break;
            default:
                break;
        }
    }
    
    //moves the player after a direction is pressed
    class PlayerMover implements KeyListener {
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
                    movePlayer(m,"");
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
                movePlayer(m, "");
                movePlayer("",m2);
            }
           repaint();
        }
        }
        
        public void keyTyped(KeyEvent event) {}
        public void keyReleased(KeyEvent event) {}
    }
    
    //move player in the desired direction if allowed
    public void movePlayer(String m, String m2){
        if("".equals(m2)){
            switch(m){
                case "n": 
                    if(!hWalls[Player1X+1][Player1Y])
                        Player1Y--;
                    break;
                case "s": 
                    if(!hWalls[Player1X+1][Player1Y+1])
                        Player1Y++;
                    break;
                case "e": 
                    if(!vWalls[Player1X+1][Player1Y+1])
                        Player1X++;
                    else if(Player1X+1 == size) {
                        getRootPane().getParent().setVisible(false);
                        new MazeCompleted(moveCounter, -1, 0, gameTime);
                    }
                    break;
                case "w": 
                    if(!vWalls[Player1X][Player1Y+1])
                        Player1X--;
                    break; 
                default: break;
            }
        }
        if ("".equals(m)){
            switch(m2){
                    case "n": 
                        if(!hWalls[Player2X+1][Player2Y])
                            Player2Y--;
                        break;
                    case "s": 
                        if(!hWalls[Player2X+1][Player2Y+1])
                            Player2Y++;
                        break;
                    case "e": 
                        if(!vWalls[Player2X+1][Player2Y+1])
                            Player2X++;
                        else if(Player2X+1 == size) {
                            getRootPane().getParent().setVisible(false);
                            new MazeCompleted(moveCounter, move2Counter, 2, gameTime);
                        }
                        break;
                    case "w": 
                        if(!vWalls[Player2X][Player2Y+1])
                            Player2X--;
                        break; 
                    default: break;
                }
        }
    }
}
