package snackademy;

import javax.swing.*;
import java.awt.*;

/**
 * Shows the main menu before starting the Snackademy game.
 */
public class StartMenuScreen extends JPanel {

    private final JFrame parentFrame;

    public StartMenuScreen(JFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Snackademy");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.CENTER);

        // Start button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.addActionListener(e -> startGame());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /** Start the game when the button is clicked */
    private void startGame() {
        // Close the start menu frame
        parentFrame.dispose();

        // Launch game UI
        UILayout ui = new UILayout();
        new GameController(ui);
    }
}
