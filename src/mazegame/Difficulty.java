package mazegame;

import java.awt.BorderLayout;
import java.awt.Color;
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
        JPanel directions = new JPanel();

        JLabel ins = new JLabel("TO PLAY...");
        ins.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        ins.setAlignmentX(CENTER_ALIGNMENT);

        JLabel dir = new JLabel("Choose a maze difficulty and your color.");
        JLabel dir2 = new JLabel("Player 1 uses the arrow keys. Player 2 uses WASD.");
        JLabel dir3 = new JLabel("If playing alone, either will suffice.");
        JLabel dir4 = new JLabel("Press 'Play' to start.");
        JLabel dir5 = new JLabel("Press 'Esc' in game to exit.");

        dir.setFont(new Font("SANS_SERIF", Font.PLAIN, 16));
        dir2.setFont(new Font("SANS_SERIF", Font.PLAIN, 16));
        dir3.setFont(new Font("SANS_SERIF", Font.PLAIN, 16));
        dir.setAlignmentX(CENTER_ALIGNMENT);
        dir2.setAlignmentX(CENTER_ALIGNMENT);
        dir3.setAlignmentX(CENTER_ALIGNMENT);
        dir4.setAlignmentX(CENTER_ALIGNMENT);
        dir5.setAlignmentX(CENTER_ALIGNMENT);

        directions.setLayout(new BoxLayout(directions, BoxLayout.Y_AXIS));
        directions.add(ins);
        directions.add(dir);
        directions.add(dir2);
        directions.add(dir3);
        directions.add(dir4);
        directions.add(dir5);
        directions.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel dchoice = new JPanel();

        final JComboBox<String> difficultyCombo = new JComboBox<>();
        difficultyCombo.addItem("Super Easy    10x10");
        difficultyCombo.addItem("Easy                25x25");
        difficultyCombo.addItem("Medium          50x50");
        difficultyCombo.addItem("Hard                75x75");
        difficultyCombo.addItem("Super Hard   100x100");

        final JComboBox<String> colorCombo = new JComboBox<>();
        colorCombo.addItem("Blue");
        colorCombo.addItem("Cyan");
        colorCombo.addItem("Green");
        colorCombo.addItem("Orange");
        colorCombo.addItem("Pink");
        colorCombo.addItem("Purple");
        colorCombo.addItem("Red");
        colorCombo.addItem("Yellow");

        final JComboBox<String> play2Combo = new JComboBox<>();
        play2Combo.addItem("No Player 2");
        play2Combo.addItem("Blue");
        play2Combo.addItem("Cyan");
        play2Combo.addItem("Green");
        play2Combo.addItem("Orange");
        play2Combo.addItem("Pink");
        play2Combo.addItem("Purple");
        play2Combo.addItem("Red");
        play2Combo.addItem("Yellow");

        dchoice.add(difficultyCombo);
        dchoice.add(colorCombo);
        dchoice.add(play2Combo);

        JLabel modeSelect = new JLabel("For those who seek more challenge...");
        modeSelect.setAlignmentX(CENTER_ALIGNMENT);

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

        dchoice.add(modeSelect);
        dchoice.add(mode0);
        dchoice.add(mode1);
        dchoice.add(mode2);
        dchoice.add(mode3);

        JPanel playOrNah = new JPanel();

        JButton play = new JButton("      Play       ");
        play.setBackground(Color.GREEN);
        play.setToolTipText("Click To Play!");
        JButton nah = new JButton("Nevermind");
        nah.setBackground(Color.RED);
        nah.setToolTipText("Nah, Exit Game");
        playOrNah.add(play);
        playOrNah.add(nah);

        class ButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {

                int diff = difficultyCombo.getSelectedIndex();
                int col = colorCombo.getSelectedIndex();
                int col2 = play2Combo.getSelectedIndex();
                int mode = 0;
                if (mode1.isSelected()) 
                    mode = 1;
                else if (mode2.isSelected()) 
                    mode = 2;
                else if (mode3.isSelected()) 
                    mode = 3;

                JFrame frame = new JFrame();
                final int FRAME_WIDTH = 700;
                final int FRAME_HEIGHT = 730;
                frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
                frame.setTitle("Crazy Maze Game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                Maze mazeComponent = new Maze(diff, col, col2, mode, FRAME_WIDTH);
                frame.add(mazeComponent);
                frame.setResizable(false);
                frame.setVisible(true);

                dispose();  // This is the line that hides the difficulty GUI
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
}
