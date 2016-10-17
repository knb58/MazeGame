package mazegame;

import javax.swing.JFrame;

/**
 * Run this to play the maze game.
 * Creates the frame for the Difficulty GUI.
 * @author Kirsten Baker, Todd Locker
 */
public class MazeGame {
    
    public MazeGame(){
        JFrame difficulty = new Difficulty();
        difficulty.setSize(400,320);
        difficulty.setTitle("Maze Setup");
        difficulty.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        difficulty.setLocationRelativeTo(null);
        difficulty.setResizable(false);
        difficulty.setVisible(true);
    }
    
    public static void main(String[] args) {
        MazeGame game= new MazeGame();
    }
}
