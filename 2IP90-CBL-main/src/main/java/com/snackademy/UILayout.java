package snackademy;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

/**
 * UILayout sets up the main game window for Snackademy.
 * It contains the player, librarian, desk, snack station, and movable text.
 * All movement is handled via keyboard (WASD + arrows).
 */
public class UILayout extends JFrame {

    private final Player player;
    private final Librarian librarian;
    private final Desk desk;
    private final Snackstation snackstation;
    private final JLabel movableText;
    private final GamePanel gamePanel;
    private final JLabel snackCounterLabel;

    private static final int TEXT_STEP = 10;
    private static final int LABEL_HEIGHT = 40;

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

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                positionObjects();
            }
        });

        SwingUtilities.invokeLater(this::positionObjects);
        setVisible(true);
    }

    /** Styles the movable text and snack counter labels. */
    private void styleTextLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.YELLOW);
        label.setOpaque(true);
        label.setBackground(new Color(139, 0, 0)); // dark red
        label.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 0), 3)); // dark yellow outline
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }

    /** Updates the snack transfer counter displayed on the UI. */
    public void updateSnackCounter(int count) {
        SwingUtilities.invokeLater(() -> snackCounterLabel.setText("Snacks delivered: " + count));
    }

    /** Updates the movable text displayed at the top of the game screen. */
    public void setMovableTextMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            movableText.setText(message);
            movableText.revalidate();
            movableText.repaint();
        });
    }

    /** Positions all game objects. */
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

        // Movable text (top-left)
        movableText.setBounds(50, 20, 1200, LABEL_HEIGHT);

        // Snack counter (top-right)
        int counterWidth = 250;
        snackCounterLabel.setBounds(panelWidth - counterWidth - 10, 20, counterWidth, LABEL_HEIGHT);
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

    public JComponent getGamePanel() {
        return gamePanel;
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
