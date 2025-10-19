package snackademy;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GameController connects the UILayout, Player, and Librarian.
 * It handles player movement, snack state, and updates the movable text when caught.
 */
public class GameController implements ActionListener {

    private final UILayout ui; // The game's user interface
    private final Player player; // The player object
    private final Librarian librarian; // The librarian object
    private final MovingPlayer movingPlayer; // Handles smooth player movement

    private int snackTransfers = 0; // count of snacks delivered


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

        // Add action listeners to arrow buttons
        ui.getLeftArrow().addActionListener(this);
        ui.getRightArrow().addActionListener(this);

        // Initialize MovingPlayer for smooth animations
        this.movingPlayer = new MovingPlayer(this.player, ui.getGamePanel());

        movingPlayer.setOnMoveCallback(() -> {
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

                    // Only increment if player was holding a snack
                    if (player.hasSnack()) {
                        snackTransfers++;
                        ui.updateSnackCounter(snackTransfers); // update the label
                    }
                }
                player.setHasSnack(false); // drop snack
            } else {
                wasAtDesk = false;
            }


            // Animate player based on direction
            int direction = player.isRightFacing() ? 0 : 1;
            player.movingAnimation(direction);
        });

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
     * Starts a background thread that periodically updates the librarian's status.
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
     * Handles arrow button clicks to move the player.
     *
     * @param e The ActionEvent triggered by a button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == ui.getLeftArrow()) {
            movePlayerLeft();
        } else if (source == ui.getRightArrow()) {
            movePlayerRight();
        }
    }

    /**
     * Moves the player to the left and updates facing direction.
     */
    private void movePlayerLeft() {
        player.moveLeft();
        player.setRightFacing(false);

        if (librarian.isAttentive()) {
            ui.setMovableTextMessage("You are caught!");
        }
    }

    /**
     * Moves the player to the right and updates facing direction.
     */
    private void movePlayerRight() {
        player.moveRight();
        player.setRightFacing(true);

        if (librarian.isAttentive()) {
            ui.setMovableTextMessage("You are caught!");
        }
    }
}
