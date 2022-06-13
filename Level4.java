import java.awt.*;
import java.awt.event.*;

public class Level4 extends Level {

    private static final int SPEED = 3; // velocity of player when moving horizontally

    Character m; // block representing money
    Panel panel;
    boolean hasWon;
    boolean isPressedRight;
    boolean isPressedUp;

    public Level4() {

        // starting x and y coordinates of main character
        int startingX = Panel.W;
        int startingY = Panel.H;
        c = new Character(90, (int) (startingY * 0.35) - 30, CustomColor.PINK);
        m = new Character(startingX - 120, (int) (startingY * 0.75) - 30, CustomColor.MONEY);

        // Create blocks for the floor
        createRectOfBlocks(7, 1, 0, (int) (startingY * 0.35));
        createRectOfBlocks(7, 15, startingX - 210, (int) (startingY * 0.75));
    }

    void resetLevel() {
        int startingX = Panel.W;
        int startingY = Panel.H;
        c = new Character(90, (int) (startingY * 0.35) - 30, CustomColor.PINK);
        m = new Character(startingX - 120, (int) (startingY * 0.75) - 30, CustomColor.MONEY);
    }

    public void keyPressed(KeyEvent e) {

        // if(e.getKeyCode() == KeyEvent.VK_UP && c.yVelocity >0) {
        // c.xVelocity = SPEED-1;
        // c.yVelocity = SPEED * -1;
        // c.isFalling = false;
        // }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            c.xVelocity = SPEED * -1;
        }

        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            c.xVelocity = SPEED;
            isPressedRight = true;
        }

        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (!c.isFalling) {
                c.yVelocity = -9.8;
                c.isFalling = true;
            } else {
                isPressedUp = true;
            }
        }
    }

    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
            c.xVelocity = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            isPressedRight = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            isPressedUp = false;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); // TODO: find better font + standardize across all levels.
        g.drawString("Take any leaps of faith...", 450, 140);

        // draw the characters
        m.draw(g);
        c.draw(g);

        // draw the floor blocks
        for (Block b : blocks) {
            b.draw(g);
        }
    }

    public void move() {
        super.move();

        if (isPressedRight && isPressedUp) {
            c.xVelocity = SPEED - 0.5;
            c.yVelocity = 0.65;
            // c.isFalling = false;
        }

        // If main character reaches money after having killed all the family, go to
        // next level
        if (c.intersects(m) && !hasWon) {
            hasWon = true;
            panel.nextLevel(new Level2());
        }
    }
}
