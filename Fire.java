
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
        // Convention: touching != intersecting

        // If the rectangle is not in the same row and column as the fire, it is not
        // touching it.
        if (r.y + r.height <= y || r.y >= y + S || r.x + r.width <= x || r.x >= x + S) {
            return false;
        }
        int cx = x + S / 2; // center x coordinate of the fire
        int rrx = r.x + r.width; // right x coordinate of the rectangle
        int rby = r.y + r.height; // bottom y coordinate of the rectangle

        // If rectangle is to the left of the triangle, check if the bottom right corner
        // of the character crosses the right side of the fire. (and vice versa)
        if (rrx < cx) {
            int sy = (cx - rrx) * 2 + y; // y-coordinate of the side of the triangle corresponding to the x coordinate
                                         // of the bottom right corner of the rectangle
            return rby > sy;
        } else if (r.x > cx) {
            int sy = (r.x - cx) * 2 + y;
            return rby > sy;
        }
        return true;
    }
}
