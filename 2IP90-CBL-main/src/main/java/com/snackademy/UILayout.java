package snackademy;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

/**
 * UILayout sets up the main game panel for Snackademy.
 * Contains player, librarian, desk, snackstation, movable text, snack counter,
 * back button with save feature, and debug overlay.
 */
public class UILayout extends JPanel {

    private final Player player;
    private final Librarian librarian;
    private final Desk desk;
    private final Snackstation snackstation;
    private final List<Bookshelf> bookshelves = new ArrayList<>();
    private final JLabel movableText;
    private final JLabel snackCounterLabel;
    private final GamePanel gamePanel;
    private final JButton backButton;
    private final DebugOverlayPanel debugOverlay;
    private final Random random = new Random();
    private int snackCounter = 0;

    private static final int LABEL_HEIGHT = 40;

    /**
     * Constructs the main UILayout panel.
     */
    public UILayout() {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        player = new Player();
        librarian = new Librarian(0, 0);
        desk = new Desk(0, 0);
        snackstation = new Snackstation(0, 0);

        movableText = new JLabel(
            "Move with AWSD or arrows, avoid being caught!"
        );
        styleLabel(movableText);

        snackCounterLabel = new JLabel("Snacks delivered: 0");
        styleLabel(snackCounterLabel);

        gamePanel = new GamePanel();
        gamePanel.setLayout(null);

        gamePanel.add(player.getLabel());
        gamePanel.add(librarian.getLabel());
        gamePanel.add(desk.getLabel());
        gamePanel.add(snackstation.getLabel());
        gamePanel.add(movableText);
        gamePanel.add(snackCounterLabel);

        // Bookshelves
        int numShelves = SettingsScreen.getBookshelfCount();
        for (int i = 0; i < numShelves; i++) {
            Bookshelf shelf = new Bookshelf(0, 0);
            bookshelves.add(shelf);
            gamePanel.add(shelf.getLabel());
        }

        add(gamePanel, BorderLayout.CENTER);

        debugOverlay = new DebugOverlayPanel(player, bookshelves);
        debugOverlay.setBounds(0, 0, getWidth(), getHeight());
        gamePanel.add(debugOverlay);

        backButton = createBackButton();
        gamePanel.add(backButton);

        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent _ignored) {
                positionObjects();
            }
        });

        SwingUtilities.invokeLater(this::positionObjects);
    }

    /**
     * Styles a JLabel with font, colors, border, and alignment.
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
     * Creates the back button with an action listener.
     *
     * @return the JButton for returning to the menu
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
     * @param count the current snack count
     */
    public void updateSnackCounter(int count) {
        snackCounter = count;
        SwingUtilities.invokeLater(() -> 
            snackCounterLabel.setText("Snacks delivered: " + count)
        );
    }

    /**
     * Sets the movable text message.
     *
     * @param message the message to display
     */
    public void setMovableTextMessage(String message) {
        SwingUtilities.invokeLater(() -> movableText.setText(message));
    }

    /**
     * Positions all game objects dynamically based on panel size.
     */
    private void positionObjects() {
        int w = gamePanel.getWidth();
        int h = gamePanel.getHeight();

        // Snackstation
        int snackW = w / 8;
        int snackH = h / 4;
        int snackX = 10;
        int snackY = h / 2 - snackH / 2;
        snackstation.getLabel().setBounds(snackX, snackY, snackW, snackH);
        snackstation.getLabel().setIcon(snackstation.getScaledIcon(snackW, snackH));

        player.resetPosition();
        player.moveTo(snackX, snackY);

        // Desk
        int deskW = w / 8;
        int deskH = h / 4;
        int deskX = w - deskW - 10;
        int deskY = h / 2 - deskH / 2;
        desk.getLabel().setBounds(deskX, deskY, deskW, deskH);
        desk.getLabel().setIcon(desk.getScaledIcon(deskW, deskH));

        // Librarian
        int libW = w / 8;
        int libH = h / 4;
        int libX = w / 2 - libW / 2;
        int libY = 4 * h / 5 - libH / 2;
        librarian.getLabel().setBounds(libX, libY, libW, libH);
        librarian.getLabel().setIcon(
            librarian.getScaledIcon(librarian.getCurrentStateName(), libW, libH)
        );

        // Bookshelves
        int marginX = 150;
        int sw = w - 2 * marginX;
        int bsW = w / 8;
        int bsH = h / 4;
        int marginY = bsH / 14;
        int spacing = sw / (bookshelves.size() + 1);

        int[] yPosition = new int[bookshelves.size()];
        for (int i = 0; i < bookshelves.size(); i++) {
            yPosition[i] = marginY + (h - bsH) * (i + 1) / (bookshelves.size() + 1);
        }

        for (int i = yPosition.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int t = yPosition[i];
            yPosition[i] = yPosition[j];
            yPosition[j] = t;
        }

        for (int i = 0; i < bookshelves.size(); i++) {
            int bsX = marginX + spacing * (i + 1) - bsW / 2;
            int bsY = yPosition[i];
            Bookshelf shelf = bookshelves.get(i);
            shelf.getLabel().setBounds(bsX, bsY, bsW, bsH);
            shelf.getLabel().setIcon(shelf.getScaledIcon(bsW, bsH));
        }

        movableText.setBounds(50, 20, 1200, LABEL_HEIGHT);
        snackCounterLabel.setBounds(w - 260, 20, 250, LABEL_HEIGHT);

        int btnW = 180;
        int btnH = 40;
        backButton.setBounds(20, h - btnH - 20, btnW, btnH);

        debugOverlay.setBounds(0, 0, gamePanel.getWidth(), gamePanel.getHeight());

        updateLayer();
    }

    /**
     * Updates the Z-order of layered components.
     */
    public void updateLayer() {
        List<JComponent> layeredComponents = new ArrayList<>();
        for (Bookshelf shelf : bookshelves) {
            layeredComponents.add(shelf.getLabel());
        }

        layeredComponents.add(player.getLabel());
        Component librarianComponent = librarian.getLabel();
        layeredComponents.add(desk.getLabel());
        layeredComponents.add(snackstation.getLabel());

        layeredComponents.sort((a, b) -> Integer.compare(b.getY(), a.getY()));
        gamePanel.setComponentZOrder(librarianComponent, 0);

        for (int i = 1; i < layeredComponents.size() + 1; i++) {
            gamePanel.setComponentZOrder(layeredComponents.get(i - 1), i);
        }
    }

    /**
     * Handles the back button action.
     *
     * @param _ignored the ActionEvent (unused)
     */
    private void backToMenu(ActionEvent _ignored) {
        GameFrame gameFrame = (GameFrame) SwingUtilities.getWindowAncestor(this);
        if (gameFrame != null) {
            SaveProgressScreen saveDialog = new SaveProgressScreen(gameFrame, snackCounter);
            String name = saveDialog.getPlayerName();
            if (name != null && !name.isEmpty() && gameFrame.frameStartMenu != null) {
                gameFrame.frameStartMenu.leaderboard.add(
                    new ScoreEntry(name, snackCounter)
                );
            }
            gameFrame.showStartMenu();
        }
    }

    // Public getters

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

    public List<Bookshelf> getBookshelves() {
        return bookshelves;
    }

    public DebugOverlayPanel getDebugOverlay() {
        return debugOverlay;
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }

    /**
     * Inner class for the game panel.
     */
    private class GamePanel extends JPanel {

        private final Image background;

        public GamePanel() {
            background = new ImageIcon(
                getClass().getResource("/snackademy/resources/Background.png")
            ).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
