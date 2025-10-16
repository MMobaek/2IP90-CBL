package snackademy;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Represents the Snackstation object in the Snackademy game.
 * The snackstation can be scaled dynamically and displayed via a JLabel.
 */
public class Snackstation {

    private static final int DEFAULT_SIZE = 150;
    private final JLabel label;
    private ImageIcon icon;

    /**
     * Constructs a Snackstation at a specific (x, y) position.
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Snackstation(int x, int y) {
        icon = loadIcon("/snackademy/resources/snackstation.png");
        label = new JLabel(icon);
        label.setBounds(x, y, DEFAULT_SIZE, DEFAULT_SIZE);
    }

    /** Loads an ImageIcon from the given resource path. */
    private ImageIcon loadIcon(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            throw new IllegalStateException("Snackstation image not found: " + path);
        }
        return new ImageIcon(url);
    }

    /**
     * Returns the JLabel used to display the Snackstation.
     *
     * @return JLabel representing the Snackstation
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Returns a scaled ImageIcon for dynamic resizing.
     *
     * @param width  new width
     * @param height new height
     * @return scaled ImageIcon
     */
    public ImageIcon getScaledIcon(int width, int height) {
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
