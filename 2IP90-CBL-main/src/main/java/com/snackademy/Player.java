package snackademy;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Represents the player character in the Snackademy game.
 *
 * The player is represented by a movable {@code JLabel} containing an image.
 * The player can move horizontally and vertically within the game window using
 * the arrow keys or on-screen buttons.
 *
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

    private Icon[] imageIcons;

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

        loadAnimationIcons();
    }

    /**
     * Loads and scales the player's image.
     *
     * @return the loaded and scaled ImageIcon
     * @throws RuntimeException if the image cannot be found or read
     */
    private ImageIcon loadIcon() {
        java.net.URL url = getClass()
                .getResource("/snackademy/resources/Standing.png");

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
     * Loads the animation for movement.
     * 
     * @throws RuntimeException if the image can't be found or read.
     */

    public void loadAnimationIcons() {
        String[] imageNames = { // The files I want to load
            "SnacksLeftFoot.png",
            "SnacksRightFoot.png",
            "SnacksStanding.png",
            "LeftFoot.png",
            "RightFoot.png",
            "Standing.png"
        };

        imageIcons = new Icon[imageNames.length]; // The array of animationstates

        for (int i = 0; i < imageNames.length; i++) {
            // Uses concatination to get the proper dress of the images
            java.net.URL url = getClass().getResource("/snackademy/resources/" + imageNames[i]);
            if (url == null) {
                throw new RuntimeException("Player image not found: " + imageNames[i]);
            }

            try {
                Image img = javax.imageio.ImageIO.read(url);
                // Using Elines scaling function blindly 
                // (might end up with a slenderman or a dwarf)
                imageIcons[i] = new ImageIcon(
                    img.getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH));
                // movingAnimation();
            } catch (Exception ex) {
                throw new RuntimeException("Failed to load player image: " + imageNames[i], ex);
            }
        }
    }

    /** Sets the image icon of the player. */
    public void movingAnimation(int imageIndex) {
        label.setIcon(imageIcons[imageIndex]);
    }

    /** The walking animation of the player. */
    public void movingAnimation() {
        // Frame order: left foot, right foot, standing
        int[] frames = {0, 2, 1, 2};
        final int frameDelay = 150; // time between frames in ms
        final int[] currentFrame = {0}; // mutable index for inner class

        // Stop any previous animation timer to avoid overlap
        // if (label.getClientProperty("animationTimer") instanceof Timer oldTimer) {
        //     oldTimer.stop();
        // }

        Timer timer = new Timer(frameDelay, e -> {
            label.setIcon(imageIcons[frames[currentFrame[0]]]); // show frame
            currentFrame[0]++;

            if (currentFrame[0] >= frames.length) {
                ((Timer) e.getSource()).stop(); // stop animation
                label.setIcon(imageIcons[2]); // return to standing
            }
        });

        // Gives us the opportunity to stop prematurely
        label.putClientProperty("animationTimer", timer);

        timer.setInitialDelay(0);
        timer.start();

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

    /**
     * Moves the player immediately to the specified coordinates.
     *
     * @param newX the x coordinate
     * @param newY the y coordinate
     */
    public void moveTo(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        updateLabel();
    }

}
