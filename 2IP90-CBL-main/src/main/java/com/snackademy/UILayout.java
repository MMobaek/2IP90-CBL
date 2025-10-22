package snackademy;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * UILayout sets up the main game panel for Snackademy.
 * Contains player, librarian, desk, snackstation, movable text, and snack counter.
 */
public class UILayout extends JPanel {

    private final Player player;
    private final Librarian librarian;
    private final Desk desk;
    private final Snackstation snackstation;
    private final Bookshelf bookshelf;
    private final JLabel movableText;
    private final JLabel snackCounterLabel;
    private final GamePanel gamePanel;
    private final JButton backButton;
    private DebugOverlayPanel debugOverlay;

    private static final int LABEL_HEIGHT = 40;

    /**
     * Constructs the UILayout and initializes all game objects and UI elements.
     */
    public UILayout() {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        player = new Player();
        librarian = new Librarian(0, 0);
        desk = new Desk(0, 0);
        snackstation = new Snackstation(0, 0);
        bookshelf = new Bookshelf(0, 0);

        movableText = new JLabel("Move with AWSD or arrows, avoid being caught!");
        styleLabel(movableText);

        snackCounterLabel = new JLabel("Snacks delivered: 0");
        styleLabel(snackCounterLabel);

        gamePanel = new GamePanel();
        gamePanel.setLayout(null);
        gamePanel.add(player.getLabel());
        gamePanel.add(librarian.getLabel());
        gamePanel.add(desk.getLabel());
        gamePanel.add(snackstation.getLabel());
        gamePanel.add(bookshelf.getLabel());
        gamePanel.add(movableText);
        gamePanel.add(snackCounterLabel);

        add(gamePanel, BorderLayout.CENTER);

        debugOverlay = new DebugOverlayPanel(player, bookshelf);
        debugOverlay.setBounds(0, 0, getWidth(), getHeight());
        gamePanel.add(debugOverlay);
        gamePanel.setComponentZOrder(debugOverlay, 0); // Bring to front

        backButton = createBackButton();
        gamePanel.add(backButton);

        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ignored) {
                positionObjects();
            }
        });

        SwingUtilities.invokeLater(this::positionObjects);
    }

    /**
     * Styles a JLabel with red background, yellow text, and border.
     *
     * @param label the JLabel to style
     */
    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.YELLOW);
        label.setOpaque(true);
        label.setBackground(new Color(200, 0, 0));
        label.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 0), 3));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
    }

    /**
     * Creates a styled "Back to Menu" button.
     *
     * @return the JButton configured as a back button
     */
    private JButton createBackButton() {
        JButton back = new JButton("Back to Menu");
        back.setFont(new Font("Arial", Font.BOLD, 16));
        back.setBackground(new Color(200, 0, 0));
        back.setForeground(Color.YELLOW);
        back.setFocusPainted(false);
        back.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));

        back.addActionListener(this::backToMenu);

        return back;
    }

    /**
     * Updates the snack counter label.
     *
     * @param count the number of snacks delivered
     */
    public void updateSnackCounter(int count) {
        SwingUtilities.invokeLater(() ->
            snackCounterLabel.setText("Snacks delivered: " + count)
        );
    }

    /**
     * Updates the movable text label.
     *
     * @param message the message to display
     */
    public void setMovableTextMessage(String message) {
        SwingUtilities.invokeLater(() -> movableText.setText(message));
    }

    /**
     * Dynamically positions all game objects based on panel size.
     */
    private void positionObjects() {
        int w = gamePanel.getWidth();
        int h = gamePanel.getHeight();

        int snackW = w / 8;
        int snackH = h / 4;
        int snackX = 10;
        int snackY = h / 2 - snackH / 2;
        snackstation.getLabel().setBounds(snackX, snackY, snackW, snackH);
        snackstation.getLabel().setIcon(snackstation.getScaledIcon(snackW, snackH));

        player.resetPosition();
        player.moveTo(snackX, snackY);

        int deskW = w / 8;
        int deskH = h / 4;
        int deskX = w - deskW - 10;
        int deskY = h / 2 - deskH / 2;
        desk.getLabel().setBounds(deskX, deskY, deskW, deskH);
        desk.getLabel().setIcon(desk.getScaledIcon(deskW, deskH));

        int libW = w / 8;
        int libH = h / 4;
        int libX = w / 2 - libW / 2;
        int libY = 4 * h / 5 - libH / 2;
        librarian.getLabel().setBounds(libX, libY, libW, libH);
        librarian.getLabel().setIcon(
            librarian.getScaledIcon(librarian.getCurrentStateName(), libW, libH));

        int bsW = w / 8;
        int bsH = h / 4;
        int bsX = 100;
        int bsY = h / 2 - bsH / 2;
        bookshelf.getLabel().setBounds(bsX, bsY, bsW, bsH);
        bookshelf.getLabel().setIcon(bookshelf.getScaledIcon(bsW, bsH));

        movableText.setBounds(50, 20, 1200, LABEL_HEIGHT);
        snackCounterLabel.setBounds(w - 260, 20, 250, LABEL_HEIGHT);

        int btnW = 180;
        int btnH = 40;
        backButton.setBounds(20, h - btnH - 20, btnW, btnH);
    }

    /**
     * Returns the desk object.
     *
     * @return desk
     */
    public Desk getDesk() {
        return desk;
    }

    /**
     * Returns the snackstation object.
     *
     * @return snackstation
     */
    public Snackstation getSnackstation() {
        return snackstation;
    }

    /**
     * Returns the bookshelf object.
     *
     * @return bookshelf
     */
    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    /**
     * Returns the player object.
     *
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the librarian object.
     *
     * @return librarian
     */
    public Librarian getLibrarian() {
        return librarian;
    }

    /**
     * Returns the main game panel.
     *
     * @return gamePanel
     */
    public JComponent getGamePanel() {
        return gamePanel;
    }

    /**
     * Returns to the main menu screen.
     *
     * @param ignored unused ActionEvent
     */
    private void backToMenu(ActionEvent ignored) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new StartMenuScreen(frame));
            frame.revalidate();
            frame.repaint();
        }
    }

    public DebugOverlayPanel getDebugOverlay() {
        return debugOverlay;
    }


    /**
     * Custom JPanel with background image.
     */
    private class GamePanel extends JPanel {

        private final Image background;

        public GamePanel() {
            background = new ImageIcon(getClass()
                .getResource("/snackademy/resources/Background.png")).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
