package snackademy;

import java.awt.*;
import javax.swing.*;

/**
 * Simple Help screen for Snackademy.
 */
public class HelpScreen extends JPanel {

    private final JFrame parentFrame;

    /**
     * Constructs a HelpScreen with a reference to the parent frame.
     *
     * @param frame the parent JFrame to return to when clicking "Back"
     */
    public HelpScreen(JFrame frame) {
        this.parentFrame = frame;
        initializeUI();
    }

    /**
     * Initializes the UI elements of the Help screen.
     */
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

    /**
     * Handles the Back button action — returns to the Start Menu.
     */
    private void handleBackAction() {
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new StartMenuScreen(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
