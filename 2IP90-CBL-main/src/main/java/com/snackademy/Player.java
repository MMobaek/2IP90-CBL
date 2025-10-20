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
    private static int speed = 5; // Movement step size in pixels

    private int[] frames = {3, 5, 4, 5}; // Current animation frames

    /** 
     * Default constructor initializes the player at (0, 0). 
     */
    public Player() {
        this(0, 0);
    }

    /**
     * Constructor initializes the player at specified coordinates.
     *
     * @param startX initial x coordinate
     * @param startY initial y coordinate
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
     * Sets the movement speed of the player.
     *
     * @param s speed in pixels
     */
    public static void setSpeed(int s) {
        speed = s;
    }

    /**
     * Returns the current movement speed.
     *
     * @return speed in pixels
     */
    public static int getSpeed() {
        return speed;
    }

    // --- Movement methods ---

    /** Moves the player left. */
    public void moveLeft() {
        x = Math.max(0, x - speed);
        updateLabel();
    }

    /** Moves the player right. */
    public void moveRight() {
        x += speed;
        updateLabel();
    }

    /** Moves the player up. */
    public void moveUp() {
        y = Math.max(0, y - speed);
        updateLabel();
    }

    /** Moves the player down. */
    public void moveDown() {
        y += speed;
        updateLabel();
    }

    /**
     * Moves the player to specific coordinates.
     *
     * @param newX target x coordinate
     * @param newY target y coordinate
     */
    public void moveTo(int newX, int newY) {
        x = newX;
        y = newY;
        updateLabel();
    }

    /** Resets the player position to the start coordinates. */
    public void resetPosition() {
        x = startX;
        y = startY;
        updateLabel();
    }

    /** Returns the current x coordinate. */
    public int getX() {
        return x;
    }

    /** Returns the current y coordinate. */
    public int getY() {
        return y;
    }

    /** Returns the JLabel representing the player. */
    public JLabel getLabel() {
        return label;
    }

    /** Returns whether the player is facing right. */
    public boolean isRightFacing() {
        return rightFacing;
    }

    /** Sets the player facing direction. */
    public void setRightFacing(boolean right) {
        this.rightFacing = right;
    }

    /** Returns whether the player has a snack. */
    public boolean hasSnack() {
        return hasSnack;
    }

    /** Sets whether the player has a snack. */
    public void setHasSnack(boolean hasSnack) {
        this.hasSnack = hasSnack;
    }

    /** Returns the default standing icon of the player. */
    public ImageIcon getIcon() {
        return icon;
    }

    /** Updates the position of the JLabel based on current x and y. */
    private void updateLabel() {
        label.setLocation(x, y);
    }

    /** Loads the default standing image of the player. */
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

    /** Loads all walking and snacking animation images. */
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
            java.net.URL url = getClass().getResource("/snackademy/resources/" + imageNames[i]);

            if (url == null) {
                throw new RuntimeException("Player image not found: " + imageNames[i]);
            }

            try {
                Image img = javax.imageio.ImageIO.read(url);
                imageIcons[i] = new ImageIcon(
                    img.getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH));
            } catch (Exception ex) {
                throw new RuntimeException(
                    "Failed to load player image: " + imageNames[i], ex
                );
            }
        }
    }

    /**
     * Animates the player walking.
     *
     * @param direction 0 = right, 1 = left
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
     * Horizontally flips an ImageIcon.
     *
     * @param icon the icon to flip
     * @return horizontally flipped icon
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
}
