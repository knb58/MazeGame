package mazegame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * This class creates the GUI that appears after the user completes the maze.
 * User is shown stats and has the option to choose to play again.
 * @author Todd Locker, Kirsten Baker
 */
public class MazeCompleted {
    public MazeCompleted(int movesMade, int moves2Made, int playerWon, int timeTaken) {
        
        final JFrame endFrame = new JFrame();
        endFrame.setTitle("Maze Completed");
        endFrame.setSize(300, 250);
        endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endFrame.setLocationRelativeTo(null);
        endFrame.setResizable(false);
        
        JPanel win=new JPanel();
        JLabel winner= new JLabel();
        winner.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        win.setAlignmentX(CENTER_ALIGNMENT);
        if (playerWon==1)
            winner.setText("PLAYER 1 WINS!!!");
        else if(playerWon==2)
            winner.setText("PLAYER 2 WINS!!!");
        else if(playerWon == -1)
            winner.setText("You Quit!");
        else
            winner.setText("YOU WON!!!");
        win.add(winner);
            
        // Creating a panel for the info
        JPanel info= new JPanel(new GridLayout(3,2));
        
        JLabel movesLabel = new JLabel("     Moves Made: ");
        JTextField moves = new JTextField(10);
        moves.setBorder(new LineBorder(new Color(0,0,0,0)));
        moves.setText(movesMade + "");
        moves.setEditable(false);
        
        if(moves2Made>-1){
            JLabel moves1Label= new JLabel("     Player 1 Moves Made: ");
            moves1Label.setAlignmentX(CENTER_ALIGNMENT);
            JLabel moves2Label= new JLabel("     Player 2 Moves Made: ");
            moves2Label.setAlignmentX(CENTER_ALIGNMENT);
            JTextField moves2 = new JTextField(10);
            moves2.setAlignmentX(CENTER_ALIGNMENT);
            moves.setAlignmentX(CENTER_ALIGNMENT);
            moves2.setBorder(new LineBorder(new Color(0,0,0,0)));
            moves2.setText(moves2Made+ "");
            moves2.setEditable(false);
            info.add(moves1Label);
            info.add(moves);
            info.add(moves2Label);
            info.add(moves2);
        }
        else{
            info.add(movesLabel);
            info.add(moves);
        }
        
        JLabel timeLabel = new JLabel("     Completion Time: ");
        if (playerWon == -1)
            timeLabel = new JLabel("                   Time: ");
        info.add(timeLabel);
        JTextField time = new JTextField(10);
        time.setBorder(new LineBorder(new Color(0,0,0,0)));
        time.setText(timeTaken/60 + " min   "+ timeTaken%60+ " s");
        time.setEditable(false);
        info.add(time);

        // Creating a panel for the replay button and attaching a listener
        JPanel buttonPanel = new JPanel();
        JButton replayButton = new JButton("Play Again");
        replayButton.setBackground(Color.GREEN);
        JButton quitButton= new JButton("Quit");
        quitButton.setBackground(Color.RED);
        replayButton.setToolTipText("Click To Play A New Maze");
        quitButton.setToolTipText("Click To Exit Game");
        
        class PAButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                endFrame.dispose();
                MazeGame newGame= new MazeGame();
            }
        }
        class QButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        }
        ActionListener palistener = new PAButtonListener();
        ActionListener qlistener= new QButtonListener();
        replayButton.addActionListener(palistener);
        quitButton.addActionListener(qlistener);
        buttonPanel.add(replayButton);
        buttonPanel.add(quitButton);
		
        // Adding the panels to the frame
        endFrame.add(win, BorderLayout.NORTH);
        endFrame.add(info, BorderLayout.CENTER);
        endFrame.add(buttonPanel, BorderLayout.SOUTH);
        endFrame.setVisible(true);
    }
}
