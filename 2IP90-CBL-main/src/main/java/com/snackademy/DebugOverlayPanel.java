package snackademy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import javax.swing.JPanel;


/** Creates an overlay over where the hitboxes are supposed to be. */
public class DebugOverlayPanel extends JPanel {

    private Player player;
    private Bookshelf bookshelf;

    /** Idk really what it does. It doesn't work without this method. */
    public DebugOverlayPanel(Player player, Bookshelf bookshelf) {
        this.player = player;
        this.bookshelf = bookshelf;
        setOpaque(false); // Make sure it's transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw player bounding box
        Rectangle playerBox = player.getRectangleBounds();
        g2d.setColor(Color.RED);
        // g2d.draw(playerBox);

        // Draw bookshelf hitbox
        Polygon shelfBox = bookshelf.getPolygonBounds(
            bookshelf.getLabel().getX(), bookshelf.getLabel().getY());
        g2d.setColor(Color.BLUE);
        // g2d.drawPolygon(shelfBox);
    }
    
}