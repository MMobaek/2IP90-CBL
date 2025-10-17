package snackademy;

import java.awt.*;
import javax.swing.*;

/**
 * Full-screen overlay using an image for "YOU ARE CAUGHT!"
 * with a Retry button.
 * 
 * [THIS DOES NOT WORK]
 */
public class CaughtScreen {

    private final JPanel overlayPanel;

    /**
     * @param parent  The main JFrame to cover
     * @param onRetry Callback to execute when retry is pressed
     */
    public CaughtScreen(JFrame parent, Runnable onRetry) {
        // Load the image icon
        ImageIcon icon = new ImageIcon(getClass().getResource(
                "/snackademy/resources/caught_screen.jpg"));

        overlayPanel = new JPanel();
        overlayPanel.setLayout(new GridBagLayout());
        overlayPanel.setBounds(0, 0, parent.getWidth(), parent.getHeight());

        // JLabel that holds the full-screen image
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setLayout(new GridBagLayout()); // so button can be added on top
        overlayPanel.add(imageLabel, new GridBagConstraints());

        // Retry button
        JButton retryButton = new JButton("Retry");
        retryButton.setFont(new Font("Arial", Font.BOLD, 32));
        retryButton.addActionListener(e -> removeOverlay(parent, onRetry));

        // Position button in the center of the image
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        imageLabel.add(retryButton, gbc);

        // Add overlay to the top layer
        parent.getLayeredPane().add(overlayPanel, JLayeredPane.MODAL_LAYER);
        overlayPanel.revalidate();
        overlayPanel.repaint();
    }

    /** Removes overlay and calls retry callback. */
    private void removeOverlay(JFrame parent, Runnable onRetry) {
        parent.getLayeredPane().remove(overlayPanel);
        parent.getLayeredPane().repaint();
        if (onRetry != null) {
            onRetry.run();
        }
    }
}
