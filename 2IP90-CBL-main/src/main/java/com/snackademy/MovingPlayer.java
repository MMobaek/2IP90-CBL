package snackademy;

import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Integrates keyboard controls for the Player.
 * Supports movement in X and Y directions using WASD or arrow keys.
 * Calls onMove() after each movement to allow game logic checks.
 */
public class MovingPlayer {

    /** Reference to the Player object that will be moved. */
    private final Player player;

    /** The Swing component that receives keyboard input (usually the game panel). */
    private final JComponent component;

    /** Callback that is triggered after each movement (optional). */
    private Runnable onMoveCallback;

    /**
     * Constructs a MovingPlayer controller.
     *
     * @param player    the Player instance to control
     * @param component the Swing component that receives keyboard focus
     */
    public MovingPlayer(final Player player, final JComponent component) {
        this.player = player;
        this.component = component;
        this.onMoveCallback = null; // default no callback
        setupKeyBindings();
    }

    /**
     * Called after every movement. Can be overridden for custom behavior,
     * e.g., checking if the player is caught.
     */
    protected void onMove() {
        if (onMoveCallback != null) {
            onMoveCallback.run();
        }
    }

    /**
     * Sets a callback to be executed every time the player moves.
     *
     * @param callback a Runnable to run on each movement
     */
    public void setOnMoveCallback(final Runnable callback) {
        this.onMoveCallback = callback;
    }

    /**
     * Configures keyboard bindings for movement using arrow keys and WASD.
     */
    private void setupKeyBindings() {
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();

        // Left movement
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("A"), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.moveLeft();
                player.movingAnimation(0, 1);
                onMove();
            }
        });

        // Right movement
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        inputMap.put(KeyStroke.getKeyStroke("D"), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.moveRight();
                player.movingAnimation(1, 0);
                onMove();
            }
        });

        // Up movement
        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke("W"), "moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.moveUp();
                player.movingAnimation(2, 2);
                onMove();
            }
        });

        // Down movement
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke("S"), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.moveDown();
                player.movingAnimation(2, 2);
                onMove();
            }
        });
    }
}
