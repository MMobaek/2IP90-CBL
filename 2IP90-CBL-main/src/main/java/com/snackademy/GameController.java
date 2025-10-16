package snackademy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GameController connects the UILayout, player, and librarian.
 * It handles player movement and updates the movable text when caught.
 */
public class GameController implements ActionListener {

    /** Reference to the user interface. */
    private final UILayout ui;

    /** The player controlled by the user. */
    private final Player player;

    /** The librarian who monitors the player. */
    private final Librarian librarian;

    /** Handles keyboard-based player movement. */
    private final MovingPlayer movingPlayer;

    /**
     * Constructor sets up the game controller and initializes the game loop.
     *
     * @param ui the user interface layout
     */
    public GameController(UILayout ui) {

        this.ui = ui;
        this.player = ui.getPlayer();
        this.librarian = ui.getLibrarian();

        // Listen to button clicks for movement
        ui.getLeftArrow().addActionListener(this);
        ui.getRightArrow().addActionListener(this);

        // Initialize MovingPlayer with keyboard input
        this.movingPlayer = new MovingPlayer(this.player, ui.getGamePanel());

        // Set callback to update movable text if caught
        movingPlayer.setOnMoveCallback(() -> {
            if (librarian.isAttentive()) {
                ui.setMovableTextMessage("Oh no, you are caught!");
            }
        });

        // Start librarian update loop
        startGameLoop();
    }

    /**
     * Starts a background thread that updates the librarian's status.
     */
    private void startGameLoop() {
        Thread thread = new Thread(() -> {
            while (true) {
                librarian.updateStatus();

                try {
                    Thread.sleep(100L);
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
     * @param e the action event from the button
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source == ui.getLeftArrow()) {
            player.moveLeft();
            if (librarian.isAttentive()) {
                ui.setMovableTextMessage("you are caught");
            }
        } else if (source == ui.getRightArrow()) {
            player.moveRight();
            if (librarian.isAttentive()) {
                ui.setMovableTextMessage("you are caught");
            }
        }
    }
}
