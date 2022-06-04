
/* Block class defines block elements of the game (including characters, the ground, etc).  

child of Rectangle because that makes it easy to draw and check for collision
*/
import java.awt.*;

public class Block extends Rectangle {
    public static final int S = 30; // side length of square

    protected Color color; // color of block's square

    // x and y are initial coordinates of the block
    public Block(int x, int y, Color color) {
        super(x, y, S, S);
        this.color = color;
    }

    // called frequently from the Panel class
    // draws the current location of the block to the screen
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}
