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
    private final JLabel snackCounterLabel;

    private static final int TEXT_STEP = 10;

    /** Constructs the UI layout for the Snackademy game. */
    public UILayout() {
        setTitle("Snackademy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        player = new Player();
        librarian = new Librarian(0, 0);
        desk = new Desk(0, 0);
        snackstation = new Snackstation(0, 0);

        movableText = new JLabel(
            "Move with the letters AWSD or the arrows but do not get caught!"
        );
        styleTextLabel(movableText);

        snackCounterLabel = new JLabel("Snacks delivered: 0");
        styleTextLabel(snackCounterLabel);

        gamePanel = new GamePanel();
        gamePanel.setLayout(null);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        add(gamePanel, BorderLayout.CENTER);

        gamePanel.add(player.getLabel());
        gamePanel.add(librarian.getLabel());
        gamePanel.add(desk.getLabel());
        gamePanel.add(snackstation.getLabel());
        gamePanel.add(movableText);
        gamePanel.add(snackCounterLabel);

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
     * Applies consistent styling for counter and movable text.
     *
     * @param label JLabel to style
     */
    
    private void styleTextLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.YELLOW);
        label.setOpaque(true);
        label.setBackground(new Color(139, 0, 0)); // dark red
        label.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 0), 3)); // dark yellow outline
        label.setHorizontalAlignment(SwingConstants.CENTER); // center text horizontally
        label.setVerticalAlignment(SwingConstants.CENTER);   // center text vertically
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

    /**
     * Updates the snack transfer counter displayed on the UI.
     *
     * @param count the number of snacks delivered
     */
    public void updateSnackCounter(int count) {
        SwingUtilities.invokeLater(() -> snackCounterLabel.setText("Snacks delivered: " + count));
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
        player.resetPosition();
        player.moveTo(snackX, snackY);

        // Desk
        int deskWidth = panelWidth / 8;
        int deskHeight = panelHeight / 4;
        int deskX = panelWidth - 10 - deskWidth;
        int deskY = panelHeight / 2 - deskHeight / 2;
        desk.getLabel().setSize(deskWidth, deskHeight);
        desk.getLabel().setIcon(desk.getScaledIcon(deskWidth, deskHeight));
        desk.getLabel().setLocation(deskX, deskY);

        // Librarian
        int librarianWidth = panelWidth / 8;
        int librarianHeight = panelHeight / 4;
        int librarianX = panelWidth / 2 - librarianWidth / 2;
        int librarianY = 4 * panelHeight / 5 - librarianHeight / 2;
        librarian.getLabel().setSize(librarianWidth, librarianHeight);
        librarian.getLabel().setIcon(
            librarian.getScaledIcon(librarian.getCurrentStateName(), librarianWidth, librarianHeight)
        );
        librarian.getLabel().setLocation(librarianX, librarianY);

        // Movable text
        movableText.setBounds(50, 20, 1200, 40);

        // Snack counter on top-right
        int counterWidth = 250;
        int counterHeight = 40;
        snackCounterLabel.setBounds(panelWidth - counterWidth - 10, 10, counterWidth, counterHeight);
    }

    public Desk getDesk() {
        return desk;
    }

    public Snackstation getSnackstation() {
        return snackstation;
    }

    public Player getPlayer() {
        return player;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public JButton getLeftArrow() {
        return leftArrow;
    }

    public JButton getRightArrow() {
        return rightArrow;
    }

    public JComponent getGamePanel() {
        return gamePanel;
    }

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

        private final Image backgroundLibrary;

        public GamePanel() {
            backgroundLibrary = new ImageIcon(getClass().getResource(
                "/snackademy/resources/Background.png")).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundLibrary, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
