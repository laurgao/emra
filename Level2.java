/* Level3 class introduces special feature of "rock climbing". Player gains the ability to scale tall walls for this level. 
 * Main message: Pink block is undeterred by challenges to get money. 
*/

import java.awt.*;
import java.awt.event.*;

public class Level2 extends Level {

    private static final int SPEED = 3; // velocity of player when moving horizontally

    Character m; 
    Character family1;
    Character family2;

    // Constructor method, initializes all characters and blocks
    public Level2(Panel panel) {
        this.panel = panel;
        hasWon = false;

        int panelW = Panel.W;
        int panelH = Panel.H;

        // Initializes charcters 
        c = new Character(50, panelH - 150, CustomColor.PINK);
        m = new Money(panelW - 70, panelH - 150, panel);

        // Create blocks for the floor
        createRectOfBlocks(6, 15, panelW / 2 + 175, 100);
        createRectOfBlocks(6, 12, panelW / 2 - 325, 200);
        createRectOfBlocks(70, 5, 0, panelH - 120);
    }

    // Resets all character locations back to initial coordinates 
    void resetLevel() {
        int panelW = Panel.W;
        int panelH = Panel.H;
        c = new Character(50, panelH - 150, CustomColor.PINK);
        m = new Money(panelW - 70, panelH - 150, panel);
    }

    // Draws all blocks and characters onto the panel 
    public void draw(Graphics g) {

        // Draw font
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); // TODO: find better font + standardize across all levels.
        g.drawString("Climb any challenge...", 350, 100);

        // Draw the characters
        m.draw(g);
        c.draw(g);

        // Draw the floor blocks
        for (Block b : blocks) {
            b.draw(g);
        }
    }

    // Allows player to double jump (rock climb) if they press the right/left and up arrow keys
    @Override
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

    // Specifies end level conditions
    // If the player touches the money block, start Level 3
    @Override
    protected void checkWin() {
        if (c.intersects(m) && !hasWon) {
            panel.nextLevel(new Level3(panel));
            hasWon = true;
        }
    }

}
