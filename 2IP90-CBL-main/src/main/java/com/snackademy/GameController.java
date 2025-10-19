package snackademy;

import java.awt.Rectangle;

/**
 * GameController connects the UILayout, Player, and Librarian.
 * It handles player movement, snack state, and updates the movable text when caught.
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

    /**
     * Updates stored player position variables.
     */
    private void updatePlayerPosition() {
        playerX = player.getX();
        playerY = player.getY();
    }

    /**
     * Gets the current X position of the player.
     *
     * @return X coordinate
     */
    public int getPlayerX() {
        return playerX;
    }

    /**
     * Gets the current Y position of the player.
     *
     * @return Y coordinate
     */
    public int getPlayerY() {
        return playerY;
    }

    /**
     * Background thread that periodically updates the librarian's status.
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
     * Handles player movement, snack station, desk interactions,
     * and updates messages when caught by the librarian.
     */
    private void handlePlayerMovement() {
        updatePlayerPosition();

        Rectangle playerRect = player.getLabel().getBounds();
        Rectangle snackRect = ui.getSnackstation().getLabel().getBounds();
        Rectangle deskRect = ui.getDesk().getLabel().getBounds();

        // Update movable text if the librarian is attentive
        if (librarian.isAttentive()) {
            ui.setMovableTextMessage("Oh no, you are caught!");
        }

        // Snack station logic
        if (playerRect.intersects(snackRect)) {
            if (!wasAtSnackStation) {
                System.out.println("Player is at the Snack Station!");
                wasAtSnackStation = true;
            }
            player.setHasSnack(true); // Pick up snack
        } else {
            wasAtSnackStation = false;
        }

        // Desk logic
        if (playerRect.intersects(deskRect)) {
            if (!wasAtDesk) {
                System.out.println("Player is at the Desk!");
                wasAtDesk = true;

                // Increment snack counter only if player was holding a snack
                if (player.hasSnack()) {
                    snackTransfers++;
                    ui.updateSnackCounter(snackTransfers);
                }
            }
            player.setHasSnack(false); // Drop snack
        } else {
            wasAtDesk = false;
        }

        // Animate player based on facing direction
        int direction = player.isRightFacing() ? 0 : 1;
        player.movingAnimation(direction);
    }
}
