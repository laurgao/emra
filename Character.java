
/* Character class defines behaviours for the player-controlled character which is a square.  

child of Rectangle because that makes it easy to draw and check for collision
*/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Character extends Block {
    // Convention for velocity and acceleration: down and right is positive
    private static final int SPEED = 3; // velocity of player when moving horizontally
    private static final double G = 9.8; // gravity
    private static final double fallingYAcceleration = G / 20; // Found experimentally to be a good value for the
                                                               // acceleration of the character when falling.

    public double xVelocity;
    public double yVelocity;
    public boolean isFalling; // if true, yVelocity updates according to fallingYAcceleration

    private boolean isAlive;

    // x and y are initial coordinates of character
    public Character(int x, int y, Color color) {
        super(x, y, color);
        xVelocity = 0;
        yVelocity = 0;
        isFalling = false;
        isAlive = true;
    }

    public Character(int x, int y, int z, Color color) {
        super(x, y, z, color);
        xVelocity = 0;
        yVelocity = 0;
        isFalling = false;
        isAlive = true;
    }

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

    // called from GamePanel when any key is released
    // Makes the paddle stop moving in that direction
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
            xVelocity = 0;
        }
    }

    // called frequently from level classes
    // updates the current location of the character
    public void move(ArrayList<Block> blocks) {
        // Moves in a way to stop when colliding with a block in the x direction.
        boolean willIntersectX = false;
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

    public void draw(Graphics g) {
        if (isAlive) {
            super.draw(g);
        }
    }

    // returns whether character will intersect rectangle after 1 more move() (but
    // only according to xVelocity)
    private boolean willIntersectX(Rectangle r) {
        return x + xVelocity + width > r.x && x + xVelocity < r.x + r.width && y + height > r.y && y < r.y + r.height;
    }

    public boolean willIntersectY(Rectangle r) {
        return x + width > r.x && x < r.x + r.width && y + yVelocity + height > r.y && y + yVelocity < r.y + r.height;
    }

    public void die() {
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
