/* Level9 features rotated controls. This shows the character being
 * confused and delusional.
 */

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

public class Level9 extends LevelWithFire {
    private Character m; // block representing money
    private ArrayList<Block> ledges; // ledges that are 1 way jumps.

    // Constructor method, initializes all characters, blocks, and fire
    public Level9(Panel panel) {
        this.panel = panel;
        hasWon = false;

        int startingY = (Panel.H * 3 / 4) / Block.S * Block.S;
        c = new Character(4 * Block.S, startingY - Block.S, CustomColor.PINK);
        m = new Money(26 * Block.S, 5 * Block.S, panel);
        createRectOfBlocks(10, 10, 2 * Block.S, startingY); // starting floor
        createRectOfBlocks(2, Panel.H / Block.S, Panel.W - 2 * Block.S, 0); // left wall
        createRectOfBlocks(2, Panel.H / Block.S, 0, 0); // right wall
        createRectOfBlocks(Panel.W, 2, 0, Panel.H - 2 * Block.S);
        createRectOfBlocks(10, 1, Block.S * 4, startingY - Block.S * 8); // left middle ledge
        createRectOfBlocks(13, 1, Block.S * 9, startingY - Block.S * 11); // top ledge
        createRectOfBlocks(7, 15, Block.S * 15, startingY - Block.S * 3); // middle fat bottom pillar
        createRectOfBlocks(3, 1, Block.S * 25, startingY - 9 * Block.S); // upper lil ledge
        createRectOfBlocks(3, 1, Block.S * 22, startingY - 5 * Block.S); // middle lil ledge
        createRectOfBlocks(3, 1, Block.S * 25, startingY); // lower lil ledge
        createRectOfBlocks(4, 8, Block.S * 18, startingY - 10 * Block.S); // vertical
        createRectOfBlocks(1, 4, Block.S * 28, 6 * Block.S); // middle wall bottom part
        createRectOfBlocks(1, 5, Block.S * 28, 11 * Block.S); // middle wall bottom part
        createRectOfBlocks(1, 4, Block.S * 25, 2 * Block.S); // middle wall top part
        createRectOfBlocks(1, 1, Block.S * 25, 0); // middle wall top part

        createRectOfBlocks(1, 10, Panel.W - Block.S * 4, 13 * Block.S);
        createRectOfBlocks(3, 1, Block.S * 29, startingY - 2 * Block.S); // lower lil ledge
        createRectOfBlocks(3, 1, Block.S * 31, startingY - 8 * Block.S); // helper right block
        createRectOfBlocks(6, 4, Block.S * 31, 0); // top right space filler

        fires = new ArrayList<Fire>();
        // Two starting fires
        fires.add(new Fire(8 * Block.S, startingY - Block.S));
        fires.add(new Fire(9 * Block.S, startingY - Block.S));
        // Three fires in the pit
        for (int i = 0; i < 3; i++) {
            fires.add(new Fire((12 + i) * Block.S, Panel.H - 3 * Block.S));
        }
        fires.add(new Fire(Block.S * 12, startingY - Block.S * 12)); // Fire on top ledge
        fires.add(new Fire(Block.S * 6, startingY - Block.S * 9)); // Fire on left middle ledge
        fires.add(new Fire(Block.S * 22, startingY - 6 * Block.S));
        fires.add(new Fire(Block.S * 25, startingY - Block.S));

        // Create ledges which are light grey blocks that are 1 way jumps.
        ledges = new ArrayList<Block>();
        createLedge(15 * Block.S, startingY - 6 * Block.S);
        createLedge(Panel.W - 7 * Block.S, startingY + Block.S);
        createLedge(Block.S * 29, (int) (9.5 * Block.S));
    }

    // Creates a rectangle with top left coordinates x and y that is one way: you
    // can jump through it but cannot fall through it
    private void createLedge(int x, int y) {
        for (int i = 0; i < 6; i++)
            ledges.add(new Block(x + i * Block.S / 2, y, 15, Color.LIGHT_GRAY));
    }

    // Resets characters to starting positions
    @Override
    public void resetLevel() {
        int startingY = (Panel.H * 3 / 4) / Block.S * Block.S;
        c = new Character(4 * Block.S, startingY - Block.S, CustomColor.PINK);
    }

    // Checks x and y collisions of pink block
    @Override
    public void move() {
        c.move(blocks);

        // Check y collisions:
        if (c.isFalling && c.yVelocity > 0) {
            // If the character collides with a block or ledge while falling downwards:
            for (Block b : Utils.extend(blocks, ledges)) {
                if (c.willIntersectY(b) && c.y < b.y) {
                    c.isFalling = false;
                    c.y = b.y - c.height;
                    break;
                }
            }
        } else if (c.isFalling && c.yVelocity < 0) {
            // if character bumps into a block while going upwards
            for (Block b : blocks) {
                if (c.willIntersectY(b) && c.y > b.y) {
                    c.yVelocity = 0;
                    c.y = b.y + b.height;
                    break;
                }
            }
        }

        // if the character is not above any block or ledge, it is falling
        if (!characterIsAboveABlock(c, Utils.extend(blocks, ledges))) {
            c.isFalling = true;
        }

        checkDeath(c);

        // If main character dies, reset the level
        if (!c.isAlive()) {
            resetLevel();
        }

        checkWin();
    }

    // Checks if the character touches the money, if yes, move player to next level
    protected void checkWin() {
        if (c.intersects(m) && !hasWon) {
            panel.nextLevel(new Level10(panel));
            hasWon = true;
        }
    }

    // Draws fires, blocks, text, and characters
    @Override
    public void draw(Graphics g) {
        m.draw(g);
        c.draw(g);
        for (Block b : Utils.extend(blocks, ledges)) {
            b.draw(g);
        }
        for (Fire f : fires) {
            f.draw(g);
        }

        // Draw text
        g.setColor(Color.BLACK);
        Font font = new Font("Monospaced", Font.ITALIC, 20);
        g.setFont(font);
        FontMetrics m = g.getFontMetrics(font);
        Utils.drawStringWrap(g, "I realized I became delusional in my pursuit of grandeur.", m, 100, 300, 250);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Rotate the mapping of arrow keys to directions.
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            // Move left
            c.xVelocity = Character.SPEED * -1;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            // Move right
            c.xVelocity = Character.SPEED;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && !c.isFalling) {
            // Jump
            c.yVelocity = -Character.G;
            c.isFalling = true;
        }
    }

    // Because left and down arrow keys control left and right movements,
    // stop the player from moving in the x direction when up and down keys are
    // released.
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            c.xVelocity = 0;
        }
    }
}
