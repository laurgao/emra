
/* Character class defines behaviours for the player-controlled character which is a square.  

child of Rectangle because that makes it easy to draw and check for collision
*/
import java.awt.*;
import java.awt.event.*;

public class Character extends Rectangle {
    public static final int S = 30; // side length of square
    public static final int SPEED = 5; // velocity of player when moving horizontally
    public static final double G = 9.8; // gravity

    private Color color; // color of character's square
    private double xVelocity;
    private double yVelocity;
    private double yAcceleration;

    // x and y are initial coordinates of character
    public Character(int x, int y, Color color) {
        super(x, y, S, S);
        this.color = color;
        xVelocity = 0;
        yVelocity = 0;
    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            xVelocity = SPEED * -1;
            move();
        }

        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            xVelocity = SPEED;
            move();
        }

        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            yAcceleration = -G;
            move();
        }

    }

    // called from GamePanel when any key is released
    // Makes the paddle stop moving in that direction
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
            xVelocity = 0;
            move();
        }
    }

    // called frequently from both Paddle class and GamePanel class
    // updates the current location of the paddle
    public void move() {
        yVelocity += yAcceleration;
        x += xVelocity;
        y += yVelocity;
    }

    // called frequently from the Screen class
    // draws the current location of the paddle to the screen
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}
