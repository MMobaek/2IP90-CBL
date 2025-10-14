package snackademy;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

/**
 * Builds the visual environment of the Snackademy game.
 * Constructs the JFrame, adds the player, librarian, and a movable text label.
 */
public class UILayout extends JFrame {

    /** Player instance. */
    private final Player player;

    /** Librarian instance. */
    private final Librarian librarian;

    /** Movable text label. */
    private final JLabel movableText;

    /** Left movement button. */
    private final JButton leftArrow;

    /** Right movement button. */
    private final JButton rightArrow;

    /** Panel where player, librarian, and text are drawn. */
    private final GamePanel gamePanel;

    /** Movement step in pixels for the text. */
    private static final int TEXT_STEP = 10;

    /**
     * Constructs the Snackademy layout using BorderLayout.
     */
    public UILayout() {
        this.setTitle("Snackademy");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLayout(new BorderLayout());

        // Create player and librarian
        player = new Player();
        librarian = new Librarian();

        // Create movable text
        movableText = new JLabel("Move with the letters AWSD or the arrows" +
                " but do not get caught!");
        movableText.setFont(new Font("Arial", Font.BOLD, 24));
        movableText.setForeground(Color.BLACK);
        movableText.setBounds(50, 20, 1200, 40); // initial position

        // Game panel in the center
        gamePanel = new GamePanel();
        gamePanel.setLayout(null); // absolute positioning
        this.add(gamePanel, BorderLayout.CENTER);

        // Add player JLabel and movable text
        gamePanel.add(player.getLabel());
        gamePanel.add(movableText);

        // Panel for buttons at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        leftArrow = new JButton("<-");
        rightArrow = new JButton("->");

        leftArrow.setFocusable(false);
        rightArrow.setFocusable(false);

        buttonPanel.add(leftArrow);
        buttonPanel.add(rightArrow);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Button actions to move the text
        leftArrow.addActionListener(e -> moveText(-TEXT_STEP));
        rightArrow.addActionListener(e -> moveText(TEXT_STEP));

        // Repaint and resize images when window changes size
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gamePanel.repaint();
            }
        });

        this.setVisible(true);
    }

    /** Move the text horizontally by dx pixels. */
    private void moveText(int dx) {
        int newX = movableText.getX() + dx;
        newX = Math.max(0, Math.min(newX, gamePanel.getWidth() - movableText.getWidth()));
        movableText.setLocation(newX, movableText.getY());
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

    /** Returns the main game panel where items are drawn. */
    public JComponent getGamePanel() {
        return gamePanel;
    }

    /** Inner class for the center panel that draws the librarian. */
    private class GamePanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int panelWidth = getWidth();
            int panelHeight = getHeight();

            // Draw librarian on the left
            int librarianWidth = panelWidth / 6;
            int librarianHeight = panelHeight / 3;
            Image librarianImg = librarian.getIcon().getImage()
                    .getScaledInstance(librarianWidth, librarianHeight, Image.SCALE_SMOOTH);
            g.drawImage(librarianImg, 10, panelHeight - librarianHeight - 10, this);
        }
    }
}
