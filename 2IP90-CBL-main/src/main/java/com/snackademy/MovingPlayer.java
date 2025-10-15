package snackademy;

import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Integrates keyboard controls for the Player.
 * Supports movement in X and Y directions using WASD or arrow keys.
 * Calls onMove() after each movement to allow game logic checks.
 */
public class MovingPlayer {

    /** Reference to the Player object. */
    private final Player player;

    /** The component to which key bindings are added (usually the game panel). */
    private final JComponent component;

    /**
     * Constructs a MovingPlayer controller.
     *
     * @param player    the Player instance to control
     * @param component the Swing component that receives keyboard focus
     */
    public MovingPlayer(final Player player, final JComponent component) {
        this.player = player;
        this.component = component;
        setupKeyBindings();
    }

    /**
     * Called after every movement. Override to add custom behavior (e.g., caught
     * check).
     */
    protected void onMove() {
        // Default does nothing - add player movement
    }

    /**
     * Sets up key bindings for WASD and arrow keys.
     */
    private void setupKeyBindings() {
        InputMap im = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = component.getActionMap();

        // Left movement
        im.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        im.put(KeyStroke.getKeyStroke("A"), "moveLeft");
        am.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.moveLeft();
                player.movingAnimation();
                onMove();
            }
        });

        // Right movement
        im.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        im.put(KeyStroke.getKeyStroke("D"), "moveRight");
        am.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.moveRight();
                player.movingAnimation();
                onMove();
            }
        });

        // Up movement
        im.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        im.put(KeyStroke.getKeyStroke("W"), "moveUp");
        am.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.moveUp();
                player.movingAnimation();
                onMove();
            }
        });

        // Down movement
        im.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        im.put(KeyStroke.getKeyStroke("S"), "moveDown");
        am.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.moveDown();
                player.movingAnimation();
                onMove();
            }
        });
    }
}
