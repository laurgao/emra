
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

    // alternative constructor that allows size of block to be manipulated
    public Block(int x, int y, int size, Color color) {
        super(x, y, size, size);
        this.color = color;
    }

    // alternative constructor that allows dimensions (width and height) to be
    // manipulated
    public Block(int x, int y, int width, int height, Color color) {
        super(x, y, width, height);
        this.color = color;
    }

    // called frequently from the Panel class
    // draws the current location of the block to the screen
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    // adjusts size of block and is an overload veriant that allows the setting of
    // camera.
    public void draw(Graphics g, int newHeight, int offsetX, int offsetY) {
        g.setColor(color);
        g.fillRect(x + offsetX, y + offsetY, width, newHeight);
    }

    // overload veriant that allows the setting of camera.
    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(color);
        g.fillRect(x + offsetX, y + offsetY, width, height);
    }
}
