package snackademy;

import java.awt.Rectangle;
import javax.swing.SwingUtilities;

/**
 * GameController connects the UILayout, Player, and Librarian.
 * It handles player movement, snack state, and shows a "caught screen" when the player is caught.
 * Movement is handled entirely via keyboard (WASD + arrows) using MovingPlayer.
 */
public class GameController {

    private final UILayout ui; // The game's user interface
    private final Player player; // The player object
    private final Librarian librarian; // The librarian object
    private final MovingPlayer movingPlayer; // Handles smooth player movement

    private int snackTransfers = 0; // Number of snacks delivered

    // Store player position
    private int playerX;
    private int playerY;

    // Track whether the player was previously at snack station or desk
    private boolean wasAtSnackStation = false;
    private boolean wasAtDesk = false;

    // Prevent multiple caught screens
    private boolean isCaughtScreenVisible = false;

    /**
     * Constructs the GameController and initializes the game.
     *
     * @param ui The UILayout of the game
     */
    public GameController(UILayout ui) {
        this.ui = ui;
        this.player = ui.getPlayer();
        this.librarian = ui.getLibrarian();

        // Initialize player position
        updatePlayerPosition();

        // Initialize MovingPlayer for smooth keyboard-controlled movement
        this.movingPlayer = new MovingPlayer(this.player, ui.getGamePanel());

        movingPlayer.setOnMoveCallback(this::handlePlayerMovement);

        // Start background game loop for librarian updates
        startGameLoop();
    }

    private void updatePlayerPosition() {
        playerX = player.getX();
        playerY = player.getY();
    }

    public int getPlayerX() { return playerX; }
    public int getPlayerY() { return playerY; }

    /**
     * Background thread updating the librarian's state
     * including INATTENTIVE → TRANSITION → ATTENTIVE.
     */
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

    /**
     * Handles player movement, snack station and desk logic,
     * and triggers caught screen after librarian transition → attentive.
     */
    private void handlePlayerMovement() {
        updatePlayerPosition();

        Rectangle playerRect = player.getLabel().getBounds();
        Rectangle snackRect = ui.getSnackstation().getLabel().getBounds();
        Rectangle deskRect = ui.getDesk().getLabel().getBounds();

        // Show caught screen only when librarian is ATTENTIVE
        if (librarian.getCurrentStateName().equals("ATTENTIVE") && !isCaughtScreenVisible) {
            isCaughtScreenVisible = true;
            SwingUtilities.invokeLater(() -> {
                CaughtScreen caughtScreen = new CaughtScreen(ui, () -> {
                    resetGame();
                    isCaughtScreenVisible = false;
                });
                caughtScreen.setVisible(true);
            });
        }

        // Snack station logic
        if (playerRect.intersects(snackRect)) {
            if (!wasAtSnackStation) {
                System.out.println("Player is at the Snack Station!");
                wasAtSnackStation = true;
            }
            player.setHasSnack(true);
        } else {
            wasAtSnackStation = false;
        }

        // Desk logic
        if (playerRect.intersects(deskRect)) {
            if (!wasAtDesk) {
                System.out.println("Player is at the Desk!");
                wasAtDesk = true;

                if (player.hasSnack()) {
                    snackTransfers++;
                    ui.updateSnackCounter(snackTransfers);
                }
            }
            player.setHasSnack(false);
        } else {
            wasAtDesk = false;
        }

        // Animate player based on facing direction
        int direction = player.isRightFacing() ? 0 : 1;
        player.movingAnimation(direction);
    }

    /**
     * Reset the game state when the player is caught.
     */
    private void resetGame() {
        player.resetPosition();
        int startX = ui.getSnackstation().getLabel().getX();
        int startY = ui.getSnackstation().getLabel().getY();
        player.moveTo(startX, startY);

        snackTransfers = 0;
        ui.updateSnackCounter(snackTransfers);

        player.setHasSnack(false);
        ui.setMovableTextMessage("Move with the letters AWSD or the arrows but do not get caught!");
    }
}
