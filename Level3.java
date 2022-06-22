/* Level3 class introduces special feature of "air gliding". Player gains the ability to glide through the air over long distances. 
 * Main message: Pink block is ready to take risks to make money.
*/

import java.awt.*;
import java.awt.event.*;

public class Level3 extends Level {

    private static final int SPEED = 3; // velocity of player when moving horizontally

    private Character m; // block representing money
    private boolean isPressedRight;
    private boolean isPressedUp;

    // Constructor method, initializes all characters and blocks
    public Level3(Panel panel) {
        this.panel = panel;
        hasWon = false;

        int panelW = Panel.W;
        int panelH = Panel.H;

        // Initializes charcters
        c = new Character(90, (int) (panelH * 0.35) - 30, CustomColor.PINK);
        m = new Money(panelW - 120, (int) (panelH * 0.75) - 30, panel);

        // Create blocks for the floor
        createRectOfBlocks(7, 1, 0, (int) (panelH * 0.35));
        createRectOfBlocks(7, 15, panelW - 210, (int) (panelH * 0.75));
    }

    // Resets all character locations back to initial coordinates
    @Override
    void resetLevel() {
        int panelW = Panel.W;
        int panelH = Panel.H;
        c = new Character(90, (int) (panelH * 0.35) - 30, CustomColor.PINK);
        m = new Money(panelW - 120, (int) (panelH * 0.75) - 30, panel);
    }

    // Allows player to air glide if right and up arrow keys are pressed. Updates
    // booleans to true if right or up arrow key is pressed.
    @Override
    public void keyPressed(KeyEvent e) {

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

    // Updates booleans that check if right and up arrow keys are pressed to false.
    @Override
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

    // Draws all blocks and characters onto the panel
    @Override
    public void draw(Graphics g) {

        // Draw font
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20));
        g.drawString("Take any leaps of faith...", 450, 140);

        // Draw the characters
        m.draw(g);
        c.draw(g);

        // Draw the floor blocks
        for (Block b : blocks) {
            b.draw(g);
        }
    }

    // Sets the player's x and y velocities to constant numbers for air gliding
    // effect.
    @Override
    public void move() {
        super.move();

        if (isPressedRight && isPressedUp) {
            c.xVelocity = SPEED - 0.5;
            c.yVelocity = 0.65;
        }
    }

    // Specifies end level conditions
    // If the player touches the money block, start Level 4
    @Override
    protected void checkWin() {
        if (c.intersects(m) && !hasWon) {
            panel.nextLevel(new Level4(panel));
            hasWon = true;
        }
    }
}
