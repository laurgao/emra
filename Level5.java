/* Level5 class allows a player to move a block. New collision feature between characters.
 * Main message: The pink block is resourceful and ready to use anything at hand to get money.
*/

import java.util.ArrayList;
import java.awt.*;
import java.util.Arrays;

public class Level5 extends Level {
    private Character m; // block representing money
    private Character stool;
    private boolean hasWon;
    private Panel panel;

    // Constructor method, initializes all characters and blocks
    public Level5(Panel panel) {
        this.panel = panel;
        hasWon = false;

        // center x and y coordinates of screen
        int cx = Panel.W / 2;
        int cy = Panel.H / 2;
        c = new Character(cx - (int) (Block.S * 6.5), cy - Block.S * 7 / 2, CustomColor.PINK);

        // Create blocks for the floor
        createRectOfBlocks(15, 5, cx - (int) (Block.S * 15 / 2.0), cy - (int) (Block.S * 5 / 2.0));
        for (int i = 0; i < 5; i++)
            createRectOfBlocks(15 - 2 * (i + 1), 1, cx - (int) (Block.S * (6.5 - i)), cy + (int) (Block.S * (2.5 + i)));
        for (int i = 0; i < 4; i++)
            createRectOfBlocks(6 - i, 1, cx + (int) (Block.S * 0.5), cy - (int) (Block.S * (3.5 + i)));
        blocks.add(new Block(cx + (int) (Block.S * 1.5), cy - (int) (Block.S * 7.5), Color.BLACK));
        m = new Money(cx + (int) (Block.S * 6.5), cy - (int) (Block.S * 3.5), panel);
        stool = new Character(cx - (int) (Block.S * 4.5), cy - (int) (Block.S * 3.5), CustomColor.BROWN);
    }

    // reset characters to starting positions.
    @Override
    void resetLevel() {
        int cx = Panel.W / 2;
        int cy = Panel.H / 2;
        c = new Character(cx - (int) (Block.S * 6.5), cy - Block.S * 7 / 2, CustomColor.PINK);
        m = new Money(cx + (int) (Block.S * 6.5), cy - (int) (Block.S * 3.5), panel);
        stool = new Character(cx - (int) (Block.S * 4.5), cy - (int) (Block.S * 3.5), CustomColor.BROWN);
    }

    // Draws characters, blocks, and text
    @Override
    public void draw(Graphics g) {
        // draw the characters
        m.draw(g);
        c.draw(g);
        stool.draw(g);

        // draw the floor blocks
        for (Block b : blocks) {
            b.draw(g);
        }

        // draw the text
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20));
        String str = "Use whatever is necessary...";
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(str, Panel.W / 2 - metrics.stringWidth(str) / 2, Panel.H / 2 - metrics.getHeight() / 2);
    }

    // Checks character's x and y collisions
    @Override
    public void move() {
        ArrayList<Block> blocksAndStool = Utils.extend(blocks, new ArrayList<Block>(Arrays.asList(stool)));
        checkYCollisions(c, blocksAndStool);
        checkYCollisions(stool);

        checkDeath(c);

        // If main character dies, reset the level
        if (!c.isAlive() && !hasWon) {
            resetLevel();
        }

        // If main character touches stool in the x direction, move the stool.
        boolean stoolWillIntersectBlock = false;
        for (Block b : blocks) {
            boolean isCollidingFromLeft = stool.x + stool.width >= b.x && stool.intersectsY(b);
            if (isCollidingFromLeft) {
                stool.x = b.x - stool.width;
                stoolWillIntersectBlock = true;
                break;
            }
        }
        if (!stoolWillIntersectBlock) {
            if (c.willIntersectX(stool) && c.xVelocity > 0) {
                stool.x = c.x + c.width;
            } else if (c.willIntersectX(stool) && c.xVelocity < 0) {
                stool.x = c.x - stool.width;
            }
        }
        ArrayList<Block> temp = stoolWillIntersectBlock ? blocksAndStool : blocks;
        c.move(temp);
        stool.move(blocks);

        // Check win condition
        checkWin();
    }

    // Specifies end level conditions
    // If the player touches the money block, start Level 6
    @Override
    protected void checkWin() {
        if (c.intersects(m) && !hasWon) {
            panel.nextLevel(new Level6(panel));
            hasWon = true;
        }
    }
}
