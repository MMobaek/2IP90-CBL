package snackademy;

import java.awt.*;
import javax.swing.*;

/**
 * Simple Help screen for Snackademy.
 */
public class HelpScreen extends JPanel {

    private final GameFrame parentFrame;

    /**
     * Constructs a HelpScreen with a reference to the parent frame.
     *
     * @param frame the parent GameFrame to return to when clicking "Back"
     */
    public HelpScreen(GameFrame frame) {
        this.parentFrame = frame;
        initializeUI();
    }

    private void initializeUI() {
        setBackground(new Color(200, 0, 0)); // red background
        setLayout(new BorderLayout());

        // Help text
        JTextArea helpText = new JTextArea(
            "Snackademy Help:\n\n"
                + "- Move with arrow keys or AWSD.\n"
                + "- Collect snacks at the Snack Station.\n"
                + "- Deliver snacks to the Desk.\n"
                + "- Avoid being caught by the librarian!"
        );
        helpText.setEditable(false);
        helpText.setForeground(Color.YELLOW);
        helpText.setBackground(new Color(200, 0, 0));
        helpText.setFont(new Font("Arial", Font.BOLD, 20));
        add(helpText, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.YELLOW);
        backButton.setForeground(new Color(200, 0, 0));
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setFocusPainted(false);
        backButton.addActionListener(event -> handleBackAction());

        add(backButton, BorderLayout.SOUTH);
    }

    private void handleBackAction() {
        parentFrame.showStartMenu();
    }
}
