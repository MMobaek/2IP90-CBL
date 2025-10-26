package snackademy;

import java.awt.*;
import javax.swing.*;

/**
 * Displays a help screen for Snackademy.
 * <p>
 * Provides instructions and tips in a story-like format set in the TU/e Metaforum library.
 */
public class HelpScreen extends JPanel {

    /** Reference to the parent frame to return to the start menu. */
    private final GameFrame parentFrame;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Constructs a HelpScreen with a reference to the parent frame.
     *
     * @param frame the parent GameFrame
     */
    public HelpScreen(GameFrame frame) {
        this.parentFrame = frame;
        initializeUI();
    }

    // -------------------------------------------------------------------------
    // UI Initialization
    // -------------------------------------------------------------------------

    /** Initializes the help screen UI components. */
    private void initializeUI() {
        setBackground(new Color(200, 0, 0)); // red background
        setLayout(new BorderLayout(20, 20));

        // Story-like help text
        JTextArea helpText = new JTextArea(
            "Welcome to Snackademy at the TU/e Metaforum Library!\n\n"
                + "Your mission: sneak delicious snacks into the library without "
                + "getting caught by the vigilant librarian.\n\n"
                + "- Move around using the arrow keys or AWSD.\n"
                + "- Collect snacks at the Snack Station and deliver them to the Desk.\n"
                + "- Watch the librarian carefully: if she is looking, you cannot move.\n"
                + "- Avoid colliding with bookshelves — the librarian will notice!\n"
                + "- Adjust the game speed and number of bookshelves in Settings to "
                + "match your preferred challenge level.\n\n"
                + "Timing, patience, and stealth are key. Can you deliver all the snacks "
                + "without raising the librarian's suspicion? Good luck!"
        );
        helpText.setEditable(false);
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);
        helpText.setForeground(Color.YELLOW);
        helpText.setBackground(new Color(200, 0, 0));
        helpText.setFont(new Font("Arial", Font.BOLD, 20));
        helpText.setMargin(new Insets(20, 20, 20, 20));

        add(helpText, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.YELLOW);
        backButton.setForeground(new Color(200, 0, 0));
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> handleBackAction());

        add(backButton, BorderLayout.SOUTH);
    }

    // -------------------------------------------------------------------------
    // Event Handling
    // -------------------------------------------------------------------------

    /** Returns to the start menu when Back is pressed. */
    private void handleBackAction() {
        parentFrame.showStartMenu();
    }
}
