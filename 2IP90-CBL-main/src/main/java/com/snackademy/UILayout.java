package snackademy;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

/**
 * UILayout sets up the main game window for Snackademy.
 * It contains the player, librarian, desk, snack station, movable text, and buttons.
 */
public class UILayout extends JFrame {

    private final Player player;
    private final Librarian librarian;
    private final Desk desk;
    private final Snackstation snackstation;
    private final JLabel movableText;
    private final JButton leftArrow;
    private final JButton rightArrow;
    private final GamePanel gamePanel;
    private static final int TEXT_STEP = 10;

    /** Constructs the UI layout for the Snackademy game. */
    public UILayout() {
        setTitle("Snackademy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        player = new Player();
        librarian = new Librarian();
        desk = new Desk(0, 0);
        snackstation = new Snackstation(0, 0);

        movableText = new JLabel(
            "Move with the letters AWSD or the arrows but do not get caught!"
        );
        movableText.setFont(new Font("Arial", Font.BOLD, 24));
        movableText.setForeground(Color.BLACK);
        movableText.setBounds(50, 20, 1200, 40);

        gamePanel = new GamePanel();
        gamePanel.setLayout(null);
        add(gamePanel, BorderLayout.CENTER);

        gamePanel.add(player.getLabel());
        gamePanel.add(librarian.getLabel());
        gamePanel.add(desk.getLabel());
        gamePanel.add(snackstation.getLabel());
        gamePanel.add(movableText);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        leftArrow = new JButton("<-");
        rightArrow = new JButton("->");
        leftArrow.setFocusable(false);
        rightArrow.setFocusable(false);
        buttonPanel.add(leftArrow);
        buttonPanel.add(rightArrow);
        add(buttonPanel, BorderLayout.SOUTH);

        leftArrow.addActionListener(e -> moveText(-TEXT_STEP));
        rightArrow.addActionListener(e -> moveText(TEXT_STEP));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                positionObjects();
            }
        });

        SwingUtilities.invokeLater(this::positionObjects);
        setVisible(true);
    }

    /**
     * Moves the movable text horizontally by dx pixels.
     *
     * @param dx distance to move
     */
    private void moveText(int dx) {
        int newX = movableText.getX() + dx;
        newX = Math.max(0, Math.min(newX, gamePanel.getWidth() - movableText.getWidth()));
        movableText.setLocation(newX, movableText.getY());
    }

    private void positionObjects() {
        int panelWidth = gamePanel.getWidth();
        int panelHeight = gamePanel.getHeight();

        // Snackstation
        int snackWidth = panelWidth / 8;
        int snackHeight = panelHeight / 4;
        int snackX = 10;
        int snackY = panelHeight / 2 - snackHeight / 2;
        snackstation.getLabel().setSize(snackWidth, snackHeight);
        snackstation.getLabel().setIcon(snackstation.getScaledIcon(snackWidth, snackHeight));
        snackstation.getLabel().setLocation(snackX, snackY);

        // Player: set internal position and label to snackstation
        player.resetPosition(); // reset to default start first
        player.moveTo(snackX, snackY); // move internal x,y and label

        // Desk
        int deskWidth = panelWidth / 8;
        int deskHeight = panelHeight / 4;
        desk.getLabel().setSize(deskWidth, deskHeight);
        desk.getLabel().setIcon(desk.getScaledIcon(deskWidth, deskHeight));
        desk.getLabel().setLocation(panelWidth - 10 - deskWidth, panelHeight / 2 - deskHeight / 2);

        // Librarian
        int librarianWidth = panelWidth / 8;
        int librarianHeight = panelHeight / 4;
        librarian.getLabel().setSize(librarianWidth, librarianHeight);
        librarian.getLabel().setIcon(
            librarian.getScaledIcon(librarian.getCurrentStateName(), librarianWidth, librarianHeight)
        );
        librarian.getLabel().setLocation(panelWidth / 2 - librarianWidth / 2,
                                        panelHeight / 2 - librarianHeight / 2);
    }


    public Player getPlayer() { return player; }

    public Librarian getLibrarian() { return librarian; }

    public JButton getLeftArrow() { return leftArrow; }

    public JButton getRightArrow() { return rightArrow; }

    public JComponent getGamePanel() { return gamePanel; }

    /**
     * Updates the movable text displayed at the top of the game screen.
     *
     * @param message new message text
     */
    public void setMovableTextMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            movableText.setText(message);
            movableText.revalidate();
            movableText.repaint();
        });
    }

    /** Custom panel to hold all game elements. */
    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
        }
    }
}
