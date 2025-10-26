package snackademy;

import java.awt.Polygon;
import java.awt.Rectangle;
import javax.swing.SwingUtilities;

/**
 * GameController connects UILayout, Player, and Librarian.
 * <p>
 * Handles player movement, interactions with snacks and desks, collisions 
 * with bookshelves, caught screens, and sound effects.
 */
public class GameController {

    // -------------------------------------------------------------------------
    // Instance Variables
    // -------------------------------------------------------------------------

    /** The game's UI layout. */
    private final UILayout ui;

    /** The player controlled by the user. */
    private final Player player;

    /** The librarian NPC in the game. */
    private final Librarian librarian;

    /** Handles smooth movement for the player. */
    private final MovingPlayer movingPlayer;

    /** Tracks the number of snack transfers. */
    private int snackTransfers = 0;

    /** Current X position of the player. */
    private int playerX;

    /** Current Y position of the player. */
    private int playerY;

    /** Flags to track whether player is at certain locations. */
    private boolean wasAtSnackStation = false;
    private boolean wasAtDesk = false;

    /** Flag to indicate if a caught screen is currently visible. */
    private boolean isCaughtScreenVisible = false;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Constructs a GameController and initializes player and librarian interactions.
     *
     * @param ui the UILayout instance for the game
     */
    public GameController(UILayout ui) {
        this.ui = ui;
        this.player = ui.getPlayer();
        this.librarian = ui.getLibrarian();

        // Set initial player position
        updatePlayerPosition();

        // Initialize smooth movement
        this.movingPlayer = new MovingPlayer(this.player, ui.getGamePanel());
        movingPlayer.setOnMoveCallback(this::handlePlayerMovement);

        // Start the background game loop for librarian updates
        startGameLoop();

        // NOTE: Background music is handled by GameFrame
    }

    // -------------------------------------------------------------------------
    // Player Position
    // -------------------------------------------------------------------------

    /** Updates the stored player coordinates. */
    private void updatePlayerPosition() {
        playerX = player.getX();
        playerY = player.getY();
    }

    /** Returns the current X position of the player. */
    public int getPlayerX() {
        return playerX;
    }

    /** Returns the current Y position of the player. */
    public int getPlayerY() {
        return playerY;
    }

    // -------------------------------------------------------------------------
    // Game Loop
    // -------------------------------------------------------------------------

    /** Starts a background thread to update librarian behavior continuously. */
    private void startGameLoop() {
        Thread thread = new Thread(() -> {
            while (true) {
                librarian.updateStatus();
                try {
                    Thread.sleep(20L); // Update every 20ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    // -------------------------------------------------------------------------
    // Player Movement Handling
    // -------------------------------------------------------------------------

    /** Handles all logic triggered when the player moves. */
    private void handlePlayerMovement() {
        updatePlayerPosition();

        Rectangle playerRect = player.getLabel().getBounds();
        Rectangle snackRect = ui.getSnackstation().getLabel().getBounds();
        Rectangle deskRect = ui.getDesk().getLabel().getBounds();
        Rectangle playerBounds = player.getRectangleBounds();

        // Check if player is caught by the librarian
        checkCaughtCondition();

        // Check collisions with all bookshelves
        for (Bookshelf shelf : ui.getBookshelves()) {
            Polygon hitbox = shelf.getPolygonBounds(
                shelf.getLabel().getX(), shelf.getLabel().getY()
            );
            collidingWithBookshelf(playerBounds, hitbox);
        }

        handleSnackStation(playerRect, snackRect);
        handleDeskInteraction(playerRect, deskRect);

        // Update debug overlay
        ui.getDebugOverlay().repaint();

        // Update player animation based on facing direction
        int direction = player.isRightFacing() ? 0 : 1;
        player.movingAnimation(direction);

        // Refresh UI layers
        ui.updateLayer();
    }

    // -------------------------------------------------------------------------
    // Caught Conditions
    // -------------------------------------------------------------------------

    /** Checks if the player has been caught by the librarian. */
    private void checkCaughtCondition() {
        if (librarian.getCurrentStateName().equals("ATTENTIVE")
            && !isCaughtScreenVisible && player.hasSnack()) {

            isCaughtScreenVisible = true;

            // Stop music and play failure sound
            getMusicPlayer().stopMusic();
            playFailureSound();

            // Show caught screen
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

    // -------------------------------------------------------------------------
    // Snack Station & Desk Handling
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Bookshelf Collisions
    // -------------------------------------------------------------------------

    private void collidingWithBookshelf(Rectangle playerBounds, Polygon hitbox) {
        if (hitbox.intersects(playerBounds) && !isCaughtScreenVisible) {
            System.out.println("Collision with bookshelf!");
            isCaughtScreenVisible = true;

            getMusicPlayer().stopMusic();
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

    // -------------------------------------------------------------------------
    // Audio
    // -------------------------------------------------------------------------

    private MusicPlayer getMusicPlayer() {
        return ((GameFrame) SwingUtilities.getWindowAncestor(ui)).getMusicPlayer();
    }

    private void playPointSound() {
        getMusicPlayer().playSound(
            "src/main/java/com/snackademy/resources/point.wav"
        );
    }

    private void playFailureSound() {
        getMusicPlayer().playSound(
            "src/main/java/com/snackademy/resources/failure.wav"
        );
    }

    // -------------------------------------------------------------------------
    // Game Reset
    // -------------------------------------------------------------------------

    /** Resets the game state to initial conditions. */
    private void resetGame() {
        player.resetPosition();

        int startX = ui.getSnackstation().getLabel().getX();
        int startY = ui.getSnackstation().getLabel().getY();
        player.moveTo(startX, startY);

        snackTransfers = 0;
        ui.updateSnackCounter(snackTransfers);

        player.setHasSnack(false);
        ui.setMovableTextMessage(
            "Move with the letters AWSD or the arrows but do not get caught!"
        );

        // Restart background music
        ((GameFrame) SwingUtilities.getWindowAncestor(ui)).playGameMusic();
    }
}
