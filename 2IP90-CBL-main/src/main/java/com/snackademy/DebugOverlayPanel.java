package snackademy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.List;
import javax.swing.JPanel;

/**
 * A transparent overlay panel for visualizing hitboxes in the game.
 * <p>
 * Draws the player's rectangle hitbox in red and all bookshelf polygon hitboxes in blue.
 * This panel is intended for debugging collision detection and layout.
 */
public class DebugOverlayPanel extends JPanel {

    // -------------------------------------------------------------------------
    // Instance Variables
    // -------------------------------------------------------------------------

    /** The player whose hitbox is visualized. */
    private Player player;

    /** List of bookshelves whose hitboxes are visualized. */
    private final List<Bookshelf> bookshelves;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Constructs a new DebugOverlayPanel.
     * 
     * @param player the player object
     * @param bookshelves the list of bookshelves
     */
    public DebugOverlayPanel(Player player, List<Bookshelf> bookshelves) {
        this.player = player;
        this.bookshelves = bookshelves;

        // Set panel transparent so it overlays without hiding the game
        setOpaque(false);
    }

    // -------------------------------------------------------------------------
    // Painting Methods
    // -------------------------------------------------------------------------

    /**
     * Paints the debug hitboxes for the player and bookshelves.
     * <p>
     * Player hitbox is drawn in red, bookshelf hitboxes in blue.
     * 
     * @param g the Graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast Graphics to Graphics2D for enhanced drawing control
        Graphics2D g2d = (Graphics2D) g;

        // Draw player's rectangle hitbox in red
        g2d.setColor(Color.RED);
        g2d.draw(player.getRectangleBounds());

        // Draw each bookshelf's polygon hitbox in blue
        g2d.setColor(Color.BLUE);
        for (Bookshelf shelf : bookshelves) {
            Polygon bounds = shelf.getPolygonBounds(
                shelf.getLabel().getX(),
                shelf.getLabel().getY()
            );
            g2d.drawPolygon(bounds);
        }
    }
}
