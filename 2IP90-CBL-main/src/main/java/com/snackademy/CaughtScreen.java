package snackademy;

import java.awt.*;
import javax.swing.*;

/**
 * The screen that appears when the player is caught.
 * Displays a message (e.g., "You were caught!" or "You walked into a bookshelf!")
 * and a button to reset the game.
 */
public class CaughtScreen extends JDialog {

    // Color and font constants
    private static final Color BACKGROUND_COLOR = new Color(255, 220, 100); // Snackademy yellow
    private static final Color BUTTON_COLOR = new Color(200, 0, 0); // Red
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);

    /**
     * Creates a caught screen overlay.
     *
     * @param ui the current UILayout of the game
     * @param message the message to display (custom text)
     * @param onReset callback to run when the player presses "Try Again"
     */
    public CaughtScreen(UILayout ui, String message, Runnable onReset) {
        super(
            SwingUtilities.getWindowAncestor(ui),
            "Caught!",
            ModalityType.APPLICATION_MODAL
        );

        setLayout(new BorderLayout());
        setSize(400, 200);
        setLocationRelativeTo(ui);
        getContentPane().setBackground(BACKGROUND_COLOR);

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(TITLE_FONT);
        messageLabel.setForeground(Color.RED);
        add(messageLabel, BorderLayout.CENTER);

        JButton tryAgainButton = new JButton("Try Again");
        tryAgainButton.setFont(BUTTON_FONT);
        tryAgainButton.setBackground(BUTTON_COLOR);
        tryAgainButton.setForeground(Color.WHITE);
        tryAgainButton.setFocusPainted(false);
        tryAgainButton.setBorder(
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        );

        tryAgainButton.addActionListener(action -> handleTryAgain(onReset));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(tryAgainButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Handles the reset action when "Try Again" is pressed.
     *
     * @param onReset callback to execute the reset logic
     */
    private void handleTryAgain(Runnable onReset) {
        dispose();
        if (onReset != null) {
            onReset.run();
        }
    }
}
