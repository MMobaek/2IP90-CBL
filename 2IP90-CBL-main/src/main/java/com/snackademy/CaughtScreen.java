package snackademy;

import java.awt.*;
import javax.swing.*;

/**
 * Displays a modal screen when the player is caught in the game.
 * <p>
 * This screen shows a custom message (e.g., "You were caught!" or 
 * "You walked into a bookshelf!") and a button to reset the game.
 */
public class CaughtScreen extends JDialog {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    /** Background color of the caught screen (Snackademy yellow). */
    private static final Color BACKGROUND_COLOR = new Color(255, 220, 100);

    /** Background color of the "Try Again" button (red). */
    private static final Color BUTTON_COLOR = new Color(200, 0, 0);

    /** Font for the main message title. */
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);

    /** Font for the "Try Again" button. */
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Creates a new CaughtScreen overlay.
     * 
     * @param ui the current UILayout of the game
     * @param message the message to display to the player
     * @param onReset callback executed when the player presses "Try Again"
     */
    public CaughtScreen(UILayout ui, String message, Runnable onReset) {
        super(
            SwingUtilities.getWindowAncestor(ui), // Parent window for modality
            "Caught!",
            ModalityType.APPLICATION_MODAL // Blocks input to other windows
        );

        // Set layout, size, position, and background
        setLayout(new BorderLayout());
        setSize(400, 200);
        setLocationRelativeTo(ui);
        getContentPane().setBackground(BACKGROUND_COLOR);

        // ---------------------------------------------------------------------
        // Message Label
        // ---------------------------------------------------------------------
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(TITLE_FONT);
        messageLabel.setForeground(Color.RED); // Red text for emphasis
        add(messageLabel, BorderLayout.CENTER);

        // ---------------------------------------------------------------------
        // "Try Again" Button
        // ---------------------------------------------------------------------
        JButton tryAgainButton = new JButton("Try Again");
        tryAgainButton.setFont(BUTTON_FONT);
        tryAgainButton.setBackground(BUTTON_COLOR);
        tryAgainButton.setForeground(Color.WHITE);
        tryAgainButton.setFocusPainted(false); // Removes focus highlight
        tryAgainButton.setBorder(
            BorderFactory.createEmptyBorder(10, 20, 10, 20) // Padding
        );

        // Attach event listener to reset game
        tryAgainButton.addActionListener(action -> handleTryAgain(onReset));

        // Button panel to center the button at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(tryAgainButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // -------------------------------------------------------------------------
    // Private Methods
    // -------------------------------------------------------------------------

    /**
     * Handles the reset logic when the "Try Again" button is pressed.
     * <p>
     * Closes the dialog and executes the provided callback if present.
     * 
     * @param onReset callback to execute reset logic
     */
    private void handleTryAgain(Runnable onReset) {
        dispose(); // Close the modal dialog
        if (onReset != null) {
            onReset.run(); // Execute game reset
        }
    }
}
