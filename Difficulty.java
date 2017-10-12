package mazegame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * This class creates the GUI so the user can select a difficulty and color.
 * Opens a Maze frame when the 'Play' JButton is pressed.
 *
 * @author Todd Locker, Kirsten Baker
 */
@SuppressWarnings("serial")
public class Difficulty extends JFrame {

    public Difficulty() {
    	//creates the directions text panel for the GUI
        JPanel directions = new JPanel();

        JLabel ins = new JLabel("TO PLAY...");
        ins.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        ins.setAlignmentX(CENTER_ALIGNMENT);

        JLabel dir = new JLabel("Choose a maze difficulty and your color.");
        JLabel dir2 = new JLabel("Player 1 uses the arrow keys. Player 2 uses WASD.");
        JLabel dir3 = new JLabel("If playing alone, either will suffice.");
        JLabel dir4 = new JLabel("Press 'Play' to start.");
        JLabel dir5 = new JLabel("Press 'Esc' in game to exit.");
        
        Font f = new Font("SANS_SERIF", Font.PLAIN, 16);
        dir.setFont(f);
        dir2.setFont(f);
        dir3.setFont(f);
        center(dir, dir2, dir3, dir4, dir5);

        //add all the directions to the panel
        directions.setLayout(new BoxLayout(directions, BoxLayout.Y_AXIS));
        addToPanel(directions, ins, dir, dir2, dir3, dir4, dir5, Box.createRigidArea(new Dimension(0, 10)));
        
        //creates the difficulty choices text
        JPanel dchoice = new JPanel();

        final JComboBox<String> difficultyCombo = new JComboBox<>();
        addToComboBox(difficultyCombo, 
        		"Super Easy    10x10", 
        		"Easy                25x25",
        		"Medium          50x50",
        		"Hard                75x75",
        		"Super Hard   100x100");

        //add color choices to players
        final JComboBox<String> colorCombo = new JComboBox<>();
        addToComboBox(colorCombo, "Blue","Cyan","Green","Orange","Pink","Purple","Red","Yellow");

        final JComboBox<String> play2Combo = new JComboBox<>();
        addToComboBox(play2Combo, "No Player 2","Blue","Cyan","Green","Orange","Pink","Purple","Red","Yellow");
        

        //add the choices to the panel
        addToPanel(dchoice, difficultyCombo, colorCombo, play2Combo);

        JLabel modeSelect = new JLabel("For those who seek more challenge...");
        center(modeSelect);

        final JRadioButton mode0 = new JRadioButton("No Thanks");
        final JRadioButton mode1 = new JRadioButton("Slightly Extreme Mode");
        final JRadioButton mode2 = new JRadioButton("Slightly More Extreme Mode");
        final JRadioButton mode3 = new JRadioButton("EXTREME MODE");
        mode0.setSelected(true);
        ButtonGroup modes = new ButtonGroup();
        modes.add(mode0);
        modes.add(mode1);
        modes.add(mode2);
        modes.add(mode3);

        //add buttons to panel
        addToPanel(dchoice, modeSelect, mode0, mode1, mode2, mode3);

        //create the play or quit buttons
        JPanel playOrNah = new JPanel();

        JButton play = new JButton("      Play       ");
        play.setBackground(Color.GREEN);
        play.setToolTipText("Click To Play!");
        
        JButton nah = new JButton("Nevermind");
        nah.setBackground(Color.RED);
        nah.setToolTipText("Nah, Exit Game");
        
        addToPanel(playOrNah, play, nah);
        

        class ButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {
            	//create a new frame with the maze
                JFrame frame = new JFrame();
                final int FRAME_WIDTH = 700;
                final int FRAME_HEIGHT = 730;
                frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
                frame.setTitle("Crazy Maze Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                Maze mazeComponent = new Maze(
                		getIndex(difficultyCombo), 
                		getIndex(colorCombo), 
                		getIndex(play2Combo), 
                		modeType(), FRAME_WIDTH);
                frame.add(mazeComponent);
                frame.setResizable(false);
                frame.setVisible(true);

                dispose();  // This is the line that hides the difficulty GUI
            }
            
            int modeType() {
            	if (mode1.isSelected()) 
                    return 1;
                else if (mode2.isSelected()) 
                    return 2;
                else if (mode3.isSelected()) 
                    return 3;
                else            	
                	return 0;
            }
            
            int getIndex(JComboBox<String> a) {
            	return a.getSelectedIndex();
            }
        }
        
        class QButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        }
        
        ActionListener listener = new ButtonListener();
        play.addActionListener(listener);
        ActionListener quit = new QButtonListener();
        nah.addActionListener(quit);

        add(directions, BorderLayout.NORTH);
        add(dchoice, BorderLayout.CENTER);
        add(playOrNah, BorderLayout.SOUTH);
    }
    
    void center(JLabel... labels) {
    	for(JLabel label: labels)
    		label.setAlignmentX(CENTER_ALIGNMENT);
    }
    
    void addToComboBox(JComboBox<String> a, String... args) {
    	for (String arg : args)
    		a.addItem(arg);
    }
    
    void addToPanel(JPanel a, Component...components) {
    	for(Component component: components)
    		a.add(component);
    }
}
