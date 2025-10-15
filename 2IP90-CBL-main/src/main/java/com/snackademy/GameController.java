package snackademy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles the game logic and event interactions for Snackademy.
 *
 * Controls button presses, keyboard movement, and updates the librarian’s
 * behavior
 * over time in a background loop.
 */
public class GameController implements ActionListener {

    /** Reference to the UI layout. */
    private final UILayout ui;

    /** Player reference. */
    private final Player player;

    /** Librarian reference. */
    private final Librarian librarian;

    /** MovingPlayer controller for keyboard input. */
    private final MovingPlayer movingPlayer;

    /**
     * Constructs a controller attached to a given UI.
     *
     * @param ui the UILayout object
     */
    public GameController(final UILayout ui) {
        this.ui = ui;
        this.player = ui.getPlayer();
        this.librarian = ui.getLibrarian();

        // Attach button listeners
        ui.getLeftArrow().addActionListener(this);
        ui.getRightArrow().addActionListener(this);

        // Attach keyboard movement using MovingPlayer
        // Assuming gamePanel is accessible via UILayout
        movingPlayer = new MovingPlayer(player, ui.getGamePanel());

        // Start the librarian update loop
        startGameLoop();
    }

    /**
     * Runs the main game loop that updates the librarian.
     */
    private void startGameLoop() {
        Thread loop = new Thread(() -> {
            while (true) {
                librarian.updateStatus();
                try {
                    Thread.sleep(100); 
                    // player.movingAnimation(2);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        loop.setDaemon(true);
        loop.start();
    }

    /**
     * Handles button press events.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        Object src = e.getSource();

        if (src == ui.getLeftArrow()) {
            player.moveLeft();
        } else if (src == ui.getRightArrow()) {
            player.moveRight();
        }
    }
}
