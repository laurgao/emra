/* Level3 class introduces special feature of "rock climbing". Player gains the ability to scale tall walls for this level. 
 * Main message: Pink block is undeterred by challenges to get money. 
*/

import java.awt.*;
import java.awt.event.*;

public class Level3 extends Level {

    private static final int SPEED = 3; // velocity of player when moving horizontally

    Character m; // block representing money
    Character family1;
    Character family2;

    public Level3(Panel panel) {
        this.panel = panel;
        hasWon = false;
        // starting x and y coordinates of main character
        int startingX = Panel.W;
        int startingY = Panel.H;
        c = new Character(50, startingY - 150, CustomColor.PINK);
        m = new Money(startingX - 70, startingY - 150, panel);

        // Create blocks for the floor
        createRectOfBlocks(6, 15, startingX / 2 + 175, 100);
        createRectOfBlocks(6, 12, startingX / 2 - 325, 200);
        createRectOfBlocks(70, 5, 0, startingY - 120);
    }

    void resetLevel() {
        int startingX = Panel.W;
        int startingY = Panel.H;
        c = new Character(50, startingY - 150, CustomColor.PINK);
        m = new Money(startingX - 70, startingY - 150, panel);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); // TODO: find better font + standardize across all levels.
        g.drawString("Climb any challenge...", 350, 100);

        // draw the characters
        m.draw(g);
        c.draw(g);

        // draw the floor blocks
        for (Block b : blocks) {
            b.draw(g);
        }
    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            c.xVelocity = SPEED * -1;
        }

        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            c.xVelocity = SPEED;
        }

        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (!c.isFalling) {
                c.yVelocity = -9.8;
                c.isFalling = true;
            }

            else if (c.isFalling && c.willIntersectX) {
                c.yVelocity = -9.8;
                c.isFalling = true;
            }
        }
    }

    @Override
    protected void checkWin() {
        if (c.intersects(m) && !hasWon) {
            panel.nextLevel(new Level4(panel));
            hasWon = true;
        }
    }

}
