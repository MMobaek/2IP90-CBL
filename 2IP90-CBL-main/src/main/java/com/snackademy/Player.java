package snackademy;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Represents the player character in the Snackademy game.
 *
 * <p>
 * The player is represented by a movable {@code JLabel} containing an image.
 * The player can move horizontally and vertically within the game window using
 * the arrow keys or on-screen buttons.
 *
 * <p>
 * Follows the Eindhoven University of Technology 2IP90 Java Coding Standard
 * for readability and consistency.
 * 
 * @author
 *         Eindhoven University of Technology - 2IP90 Programming
 */
public class Player {

    /** The horizontal position of the player. */
    private int x;

    /** The vertical position of the player. */
    private int y;

    /** The starting horizontal position of the player. */
    private final int startX;

    /** The starting vertical position of the player. */
    private final int startY;

    /** The JLabel used to visually represent the player. */
    private final JLabel label;

    /** The player's image icon. */
    private final ImageIcon icon;

    /** The visual size (width and height) of the player image in pixels. */
    private static final int SIZE = 100;

    /** The number of pixels the player moves per step. */
    private static final int STEP = 10;

    /**
     * Constructs a player at the default position (0, 0).
     */
    public Player() {
        this(0, 0);
    }

    /**
     * Constructs a player at the specified starting coordinates.
     *
     * @param startX the initial x position of the player
     * @param startY the initial y position of the player
     */
    public Player(final int startX, final int startY) {
        this.startX = startX;
        this.startY = startY;
        this.x = startX;
        this.y = startY;

        icon = loadIcon();
        label = new JLabel(icon);
        label.setBounds(x, y, SIZE, SIZE);
    }

    /**
     * Loads and scales the player's image.
     *
     * @return the loaded and scaled ImageIcon
     * @throws RuntimeException if the image cannot be found or read
     */
    private ImageIcon loadIcon() {
        java.net.URL url = getClass()
                .getResource("/snackademy/resources/cardboard-box-clipart-lg.png");

        if (url == null) {
            throw new RuntimeException("Player image not found");
        }

        try {
            Image img = javax.imageio.ImageIO.read(url);
            return new ImageIcon(img.getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load player image", e);
        }
    }

    /**
     * Moves the player to the left by {@code STEP} pixels.
     * Ensures the player does not move beyond the left edge of the screen.
     */
    public void moveLeft() {
        x = Math.max(0, x - STEP);
        updateLabel();
    }

    /**
     * Moves the player to the right by {@code STEP} pixels.
     */
    public void moveRight() {
        x += STEP;
        updateLabel();
    }

    /**
     * Moves the player upward by {@code STEP} pixels.
     * Ensures the player does not move beyond the top edge of the screen.
     */
    public void moveUp() {
        y = Math.max(0, y - STEP);
        updateLabel();
    }

    /**
     * Moves the player downward by {@code STEP} pixels.
     */
    public void moveDown() {
        y += STEP;
        updateLabel();
    }

    /**
     * Updates the visual position of the player's JLabel.
     */
    private void updateLabel() {
        label.setLocation(x, y);
    }

    /**
     * Resets the player’s position to the starting coordinates.
     */
    public void resetPosition() {
        x = startX;
        y = startY;
        updateLabel();
    }

    /**
     * Returns the player’s current x position.
     *
     * @return the x coordinate of the player
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the player’s current y position.
     *
     * @return the y coordinate of the player
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the JLabel representing the player.
     *
     * @return the player’s JLabel
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Returns the player’s image icon.
     *
     * @return the player’s ImageIcon
     */
    public ImageIcon getIcon() {
        return icon;
    }
}
