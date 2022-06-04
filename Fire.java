
/* Block class defines fire elements of the game, which are obstacles that the
 * character must avoid.
 * They are shaped like triangles and have width and height equal to that of blocks.
 */
import java.awt.*;

public class Fire {
    private static final int S = Block.S; // length of width and height
    int x;
    int y;

    // x and y are coordinates of the top left corner of the imaginary square that
    // encloses the triangle-shaped fire
    public Fire(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // called frequently from the Panel class
    // draws the current location of the fire to the screen as a triangle
    public void draw(Graphics g) {
        g.setColor(CustomColor.FIRE);
        g.fillPolygon(new int[] { x + S / 2, x, x + S }, new int[] { y, y + S, y + S }, 3);
    }

    // helper method to check if a character is touching the fire
    public boolean intersects(Rectangle r) {
        return r.intersects(x, y, S, S);
    }
}
