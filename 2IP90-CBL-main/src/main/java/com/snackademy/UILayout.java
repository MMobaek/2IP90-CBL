package snackademy;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * UILayout sets up the main game window for Snackademy.
 * It contains the player, the librarian, movable text, and movement buttons.
 */
public class UILayout extends JFrame {

    /** The player object controlled by the user. */
    private final Player player;

    /** The librarian object that observes the player. */
    private final Librarian librarian;

    /** The movable JLabel displaying messages to the player. */
    private final JLabel movableText;

    /** Left movement button. */
    private final JButton leftArrow;

    /** Right movement button. */
    private final JButton rightArrow;

    /** Panel where the player, librarian, and movable text are drawn. */
    private final GamePanel gamePanel;

    /** Movement step in pixels for the text label. */
    private static final int TEXT_STEP = 10;

    /**
     * Constructor sets up the main window, components, and layout.
     */
    public UILayout() {

        // Set window properties
        this.setTitle("Snackademy");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLayout(new BorderLayout());

        // Initialize player and librarian
        player = new Player();
        librarian = new Librarian();

        // Create movable text
        movableText = new JLabel("Move with the letters AWSD or the arrows but do not get caught!");
        movableText.setFont(new Font("Arial", Font.BOLD, 24));
        movableText.setForeground(Color.BLACK);
        movableText.setBounds(50, 20, 1200, 40);

        // Initialize game panel with absolute positioning
        gamePanel = new GamePanel();
        gamePanel.setLayout(null);
        this.add(gamePanel, BorderLayout.CENTER);

        // Add player and movable text to the game panel
        gamePanel.add(player.getLabel());
        gamePanel.add(movableText);

        // Create bottom button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        leftArrow = new JButton("<-");
        rightArrow = new JButton("->");
        leftArrow.setFocusable(false);
        rightArrow.setFocusable(false);
        buttonPanel.add(leftArrow);
        buttonPanel.add(rightArrow);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Add button actions to move text
        leftArrow.addActionListener(e -> moveText(-TEXT_STEP));
        rightArrow.addActionListener(e -> moveText(TEXT_STEP));

        // Repaint game panel on window resize
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gamePanel.repaint();
            }
        });

        // Make the window visible
        this.setVisible(true);
    }

    /**
     * Moves the movable text horizontally by dx pixels.
     *
     * @param dx the number of pixels to move the text
     */
    private void moveText(int dx) {
        int newX = movableText.getX() + dx;
        newX = Math.max(0, Math.min(newX, gamePanel.getWidth() - movableText.getWidth()));
        movableText.setLocation(newX, movableText.getY());
    }

    /**
     * Updates the movable text message displayed at the top of the game screen.
     *
     * @param message the new text to display
     */
    public void setMovableTextMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            movableText.setText(message);
            movableText.revalidate();
            movableText.repaint();
        });
    }

    /** Returns the left arrow button. */
    public JButton getLeftArrow() {
        return leftArrow;
    }

    /** Returns the right arrow button. */
    public JButton getRightArrow() {
        return rightArrow;
    }

    /** Returns the player instance. */
    public Player getPlayer() {
        return player;
    }

    /** Returns the librarian instance. */
    public Librarian getLibrarian() {
        return librarian;
    }

    /** Returns the game panel. */
    public JComponent getGamePanel() {
        return gamePanel;
    }

    /**
     * Inner class GamePanel handles custom painting of the game elements.
     */
    private class GamePanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the librarian on the left side
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int librarianWidth = panelWidth / 6;
            int librarianHeight = panelHeight / 3;
            Image librarianImage = librarian.getIcon().getImage()
                    .getScaledInstance(librarianWidth, librarianHeight, Image.SCALE_SMOOTH);
            g.drawImage(librarianImage, 10, panelHeight - librarianHeight - 10, this);
        }
    }
}
