package snackademy;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Represents the player character in the Snackademy game.
 * Handles movement, animation, and snack state.
 */
public class Player {

    private int x;
    private int y;
    private final int startX;
    private final int startY;

    private boolean rightFacing = true;
    private boolean hasSnack = false; // Track if player has snack

    private final JLabel label;
    private final ImageIcon icon;
    private Icon[] imageIcons;

    private static final int SIZE = 100; // Player image size in pixels
    private static final int STEP = 7;  // Movement step size in pixels

    private int[] frames = {3, 5, 4, 5}; // Current animation frames

    /**
     * Constructs a player at default position (0, 0).
     */
    public Player() {
        this(0, 0);
    }

    /**
     * Constructs a player at specified start coordinates.
     *
     * @param startX The initial X coordinate
     * @param startY The initial Y coordinate
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
     * Loads the default standing image.
     *
     * @return ImageIcon of the standing player
     * @throws RuntimeException if the image cannot be found or loaded
     */
    private ImageIcon loadIcon() {
        java.net.URL url = getClass().getResource("/snackademy/resources/Standing.png");
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
     * Loads all animation images for the player.
     *
     * @throws RuntimeException if any animation image cannot be found or loaded
     */
    public void loadAnimationIcons() {
        String[] imageNames = {
            "SnacksLeftFoot.png",
            "SnacksRightFoot.png",
            "SnacksStanding.png",
            "LeftFoot.png",
            "RightFoot.png",
            "Standing.png"
        };

        imageIcons = new Icon[imageNames.length];

        for (int i = 0; i < imageNames.length; i++) {
            java.net.URL url = getClass().getResource(
                "/snackademy/resources/" + imageNames[i]
            );

            if (url == null) {
                throw new RuntimeException("Player image not found: " + imageNames[i]);
            }

            try {
                Image img = javax.imageio.ImageIO.read(url);
                imageIcons[i] = new ImageIcon(
                    img.getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH)
                );
            } catch (Exception ex) {
                throw new RuntimeException(
                    "Failed to load player image: " + imageNames[i], ex
                );
            }
        }
    }

    /**
     * Sets the movement direction of the player.
     *
     * @param right true if the player is facing right, false if left
     */
    public void setRightFacing(boolean right) {
        this.rightFacing = right;
    }

    /**
     * Returns whether the player is facing right.
     *
     * @return true if facing right, false if left
     */
    public boolean isRightFacing() {
        return rightFacing;
    }

    /**
     * Sets whether the player has a snack.
     *
     * @param hasSnack true if player holds a snack, false otherwise
     */
    public void setHasSnack(boolean hasSnack) {
        this.hasSnack = hasSnack;
    }

    /**
     * Returns whether the player currently has a snack.
     *
     * @return true if holding a snack, false otherwise
     */
    public boolean hasSnack() {
        return hasSnack;
    }

    /**
     * Animates the player based on snack state and direction.
     *
     * @param direction 0 if facing right, 1 if facing left
     */
    public void movingAnimation(int direction) {
        if (hasSnack) {
            frames[0] = 0;
            frames[1] = 2;
            frames[2] = 1;
            frames[3] = 2;
        } else {
            frames[0] = 3;
            frames[1] = 5;
            frames[2] = 4;
            frames[3] = 5;
        }

        rightFacing = (direction == 0);

        final int frameDelay = 150;
        final int[] currentFrame = {0};

        Timer timer = new Timer(frameDelay, e -> {
            Icon iconToShow = imageIcons[frames[currentFrame[0]]];

            if (!rightFacing) {
                iconToShow = horizontalFlip(iconToShow);
            }

            label.setIcon(iconToShow);

            currentFrame[0]++;

            if (currentFrame[0] >= frames.length) {
                ((Timer) e.getSource()).stop();

                if (rightFacing) {
                    label.setIcon(imageIcons[frames[3]]);
                } else {
                    label.setIcon(horizontalFlip(imageIcons[frames[3]]));
                }
            }
        });

        label.putClientProperty("animationTimer", timer);
        timer.setInitialDelay(0);
        timer.start();
    }

    /**
     * Flips an ImageIcon horizontally.
     *
     * @param icon The icon to flip
     * @return A horizontally flipped ImageIcon
     * @throws IllegalArgumentException if the icon is not an ImageIcon
     */
    public static ImageIcon horizontalFlip(Icon icon) {
        if (!(icon instanceof ImageIcon)) {
            throw new IllegalArgumentException("Icon must be an ImageIcon");
        }

        Image img = ((ImageIcon) icon).getImage();
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();

        BufferedImage flipped = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = flipped.createGraphics();
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();

        return new ImageIcon(flipped);
    }

    /**
     * Moves the player left by the step size.
     */
    public void moveLeft() {
        x = Math.max(0, x - STEP);
        updateLabel();
    }

    /**
     * Moves the player right by the step size.
     */
    public void moveRight() {
        x += STEP;
        updateLabel();
    }

    /**
     * Moves the player up by the step size.
     */
    public void moveUp() {
        y = Math.max(0, y - STEP);
        updateLabel();
    }

    /**
     * Moves the player down by the step size.
     */
    public void moveDown() {
        y += STEP;
        updateLabel();
    }

    /** Updates the JLabel's position to match current x and y coordinates. */
    private void updateLabel() {
        label.setLocation(x, y);
    }

    /** Resets the player's position to the starting coordinates. */
    public void resetPosition() {
        x = startX;
        y = startY;
        updateLabel();
    }

    /**
     * Returns the current X coordinate of the player.
     *
     * @return The X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the current Y coordinate of the player.
     *
     * @return The Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the JLabel representing the player.
     *
     * @return The player's JLabel
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Returns the default standing icon of the player.
     *
     * @return The standing ImageIcon
     */
    public ImageIcon getIcon() {
        return icon;
    }

    /**
     * Moves the player directly to specified coordinates.
     *
     * @param newX The new X coordinate
     * @param newY The new Y coordinate
     */
    public void moveTo(int newX, int newY) {
        x = newX;
        y = newY;
        updateLabel();
    }
}
