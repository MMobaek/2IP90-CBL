package snackademy;

import java.awt.Polygon;
import java.awt.Rectangle;
import javax.swing.SwingUtilities;

/**
 * GameController connects UILayout, Player, and Librarian.
 * Handles player movement, snacks, caught screens, and sound effects.
 */
public class GameController {

    private final UILayout ui;
    private final Player player;
    private final Librarian librarian;
    private final MovingPlayer movingPlayer;

    private int snackTransfers = 0;
    private int playerX;
    private int playerY;

    private boolean wasAtSnackStation = false;
    private boolean wasAtDesk = false;
    private boolean isCaughtScreenVisible = false;

    public GameController(UILayout ui) {
        this.ui = ui;
        this.player = ui.getPlayer();
        this.librarian = ui.getLibrarian();

        updatePlayerPosition();

        this.movingPlayer = new MovingPlayer(this.player, ui.getGamePanel());
        movingPlayer.setOnMoveCallback(this::handlePlayerMovement);

        startGameLoop();
        // NOTE: Do NOT start background music here; GameFrame handles it
    }

    private void updatePlayerPosition() {
        playerX = player.getX();
        playerY = player.getY();
    }

    public int getPlayerX() { return playerX; }
    public int getPlayerY() { return playerY; }

    private void startGameLoop() {
        Thread thread = new Thread(() -> {
            while (true) {
                librarian.updateStatus();
                try {
                    Thread.sleep(20L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void handlePlayerMovement() {
        updatePlayerPosition();

        Rectangle playerRect = player.getLabel().getBounds();
        Rectangle snackRect = ui.getSnackstation().getLabel().getBounds();
        Rectangle deskRect = ui.getDesk().getLabel().getBounds();
        Rectangle playerBounds = player.getRectangleBounds();

        checkCaughtCondition();

        for (Bookshelf shelf : ui.getBookshelves()) {
            Polygon hitbox = shelf.getPolygonBounds(shelf.getLabel().getX(), shelf.getLabel().getY());
            collidingWithBookshelf(playerBounds, hitbox);
        }

        handleSnackStation(playerRect, snackRect);
        handleDeskInteraction(playerRect, deskRect);

        ui.getDebugOverlay().repaint();

        int direction = player.isRightFacing() ? 0 : 1;
        player.movingAnimation(direction);
        ui.updateLayer();
    }

    private void checkCaughtCondition() {
        if (librarian.getCurrentStateName().equals("ATTENTIVE")
            && !isCaughtScreenVisible && player.hasSnack()) {

            isCaughtScreenVisible = true;
            getMusicPlayer().stopMusic();  // stop background music
            playFailureSound();

            SwingUtilities.invokeLater(() -> {
                CaughtScreen caughtScreen = new CaughtScreen(
                    ui,
                    "You were caught by the librarian!",
                    () -> {
                        resetGame();
                        isCaughtScreenVisible = false;
                    }
                );
                caughtScreen.setVisible(true);
            });
        }
    }

    private void handleSnackStation(Rectangle playerRect, Rectangle snackRect) {
        if (playerRect.intersects(snackRect)) {
            if (!wasAtSnackStation) {
                System.out.println("Player is at the Snack Station!");
                wasAtSnackStation = true;
            }
            player.setHasSnack(true);
        } else {
            wasAtSnackStation = false;
        }
    }

    private void handleDeskInteraction(Rectangle playerRect, Rectangle deskRect) {
        if (playerRect.intersects(deskRect)) {
            if (!wasAtDesk) {
                System.out.println("Player is at the Desk!");
                wasAtDesk = true;
                if (player.hasSnack()) {
                    snackTransfers++;
                    ui.updateSnackCounter(snackTransfers);
                    playPointSound();
                }
            }
            player.setHasSnack(false);
        } else {
            wasAtDesk = false;
        }
    }

    private void collidingWithBookshelf(Rectangle playerBounds, Polygon hitbox) {
        if (hitbox.intersects(playerBounds) && !isCaughtScreenVisible) {
            System.out.println("Collision with bookshelf!");
            isCaughtScreenVisible = true;
            getMusicPlayer().stopMusic(); // stop background music
            playFailureSound();

            SwingUtilities.invokeLater(() -> {
                CaughtScreen caughtScreen = new CaughtScreen(
                    ui,
                    "You walked into a bookshelf!",
                    () -> {
                        resetGame();
                        isCaughtScreenVisible = false;
                    }
                );
                caughtScreen.setVisible(true);
            });
        }
    }

    // ------------------- AUDIO -------------------

    private MusicPlayer getMusicPlayer() {
        return ((GameFrame) SwingUtilities.getWindowAncestor(ui)).getMusicPlayer();
    }

    private void playPointSound() {
        getMusicPlayer().playSound("src/main/java/com/snackademy/resources/point.wav");
    }

    private void playFailureSound() {
        getMusicPlayer().playSound("src/main/java/com/snackademy/resources/failure.wav");
    }

    // ------------------- RESET -------------------

    private void resetGame() {
        player.resetPosition();
        int startX = ui.getSnackstation().getLabel().getX();
        int startY = ui.getSnackstation().getLabel().getY();
        player.moveTo(startX, startY);

        snackTransfers = 0;
        ui.updateSnackCounter(snackTransfers);

        player.setHasSnack(false);
        ui.setMovableTextMessage("Move with the letters AWSD or the arrows but do not get caught!");

        // Restart game background music (only one track)
        ((GameFrame) SwingUtilities.getWindowAncestor(ui)).playGameMusic();
    }
}
