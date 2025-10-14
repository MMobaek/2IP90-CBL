package snackademy;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Adds a start menu, where the user can choose between options.
 */
public class StartMenuScreen extends JPanel {
    /** Adds the main menu screen. */
    public StartMenuScreen(JFrame parentFrame) {
        setLayout(new GridBagLayout()); // center the button nicely
        JButton playButton = new JButton("Start Game");
        add(playButton);

        playButton.addActionListener((ActionEvent e) -> {
            parentFrame.getContentPane().removeAll();
            UILayout ui = new UILayout();
            parentFrame.getContentPane().add(ui);
            new GameController(ui);
            parentFrame.revalidate();
            parentFrame.repaint();
        });
    }
    
}
