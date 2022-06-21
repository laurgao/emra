
/* Character class defines behaviours for the player-controlled character which is a square.  

child of Rectangle because that makes it easy to draw and check for collision
*/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Character extends Block {
    // Convention for velocity and acceleration: down and right is positive
    public static final int SPEED = 3; // velocity of player when moving horizontally
    public static final double G = 9.8; // gravity
    public static final double fallingYAcceleration = G / 20; // Found experimentally to be a good value for the
                                                              // acceleration of the character when falling.
    public double xVelocity;
    public double yVelocity;
    public boolean isFalling; // if true, yVelocity updates according to fallingYAcceleration

    protected boolean isAlive;

    public boolean willIntersectX;

    // x and y are initial coordinates of character
    public Character(int x, int y, Color color) {
        super(x, y, color);
        xVelocity = 0;
        yVelocity = 0;
        isFalling = false;
        isAlive = true;
    }

    // alternate constructor method that allows size of a character to be
    // manipulated
    public Character(int x, int y, int size, Color color) {
        super(x, y, size, color);
        xVelocity = 0;
        yVelocity = 0;
        isFalling = false;
        isAlive = true;
    }

    // alternate constructor method that allows width and height of a character
    // to be manipulated.
    public Character(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        xVelocity = 0;
        yVelocity = 0;
        isFalling = false;
        isAlive = true;
    }

    // called from Level classes when any key is pressed
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            xVelocity = SPEED * -1;
        }

        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            xVelocity = SPEED;
        }

        else if (e.getKeyCode() == KeyEvent.VK_UP && !isFalling) {
            yVelocity = -G;
            isFalling = true;
        }
    }

    // called from Level classes when any key is released
    // Makes the character stop moving in that direction
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
            xVelocity = 0;
        }
    }

    // called frequently from level classes
    // updates the current location of the character
    public void move(ArrayList<Block> blocks) {

        // Moves in a way to stop when colliding with a block in the x direction.
        willIntersectX = false;
        for (Block b : blocks) {
            boolean isCollidingFromLeft = this.willIntersectX(b) && this.x < b.x && this.xVelocity > 0;
            boolean isCollidingFromRight = this.willIntersectX(b) && this.x > b.x && this.xVelocity < 0;
            if (isCollidingFromLeft || isCollidingFromRight) {
                this.x = isCollidingFromLeft ? b.x - this.width : b.x + b.width;
                willIntersectX = true;
                break;
            }
        }
        if (!willIntersectX) {
            this.x += this.xVelocity;
        }

        // Move in the y direction.
        yVelocity = isFalling ? yVelocity + fallingYAcceleration : 0;
        y += yVelocity;
    }

    // Called from the level classes to draw the character to the screen.
    public void draw(Graphics g) {
        if (isAlive) {
            super.draw(g);
        }
    }

    // Called from level classes to draw the character to the screen
    // Alternate method to account for offsets in the character's position due to
    // camera position.
    public void draw(Graphics g, int newHeight, int offsetX, int offsetY) {
        if (isAlive) {
            super.draw(g, newHeight, offsetX, offsetY);
        }
    }

    // returns whether character will intersect rectangle after 1 more move() (but
    // only according to xVelocity)
    public boolean willIntersectX(Rectangle r) {
        return x + xVelocity + width > r.x && x + xVelocity < r.x + r.width && y + height > r.y && y < r.y + r.height;
    }

    // returns whether character will intersect rectangle after 1 more move() (but
    // only according to yVelocity)
    public boolean willIntersectY(Rectangle r) {
        return x + width > r.x && x < r.x + r.width && y + yVelocity + height > r.y && y + yVelocity < r.y + r.height;
    }

    // returns whether character and rectangle are in the same "column" on the
    // screen (whether they intersect in the y-direction)
    public boolean intersectsY(Rectangle r) {
        return y + height > r.y && y < r.y + r.height;
    }

    // Sets character to be dead and no longer visible on the screen.
    public void die() {
        isAlive = false;
    }

    // Returns whether character is alive or not.
    public boolean isAlive() {
        return isAlive;
    }
}
