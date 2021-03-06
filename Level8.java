/* Level8 uses multiple characters and fires. It is meant to be a difficult and fun puzzle 
 * for the player to figure out how to kill both family members and still reach the money.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Level8 extends LevelWithFire {
    private Character m; // block representing money
    private Character family1;
    private Character family2;
    private Panel panel;
    private boolean hasWon;

    // Initializes the level by creating characters, blocks, and fires.
    public Level8(Panel panel) {
        this.panel = panel;
        hasWon = false;

        // starting x and y coordinates of main character
        int startingX = Panel.W / 10 + 5 * Block.S;
        int startingY = Panel.H / 2 + Block.S;
        c = new Character(startingX, startingY, CustomColor.PINK);
        m = new Money(startingX + 21 * Block.S, startingY, panel);
        family1 = new Character(startingX + 15 * Block.S, startingY - 4 * Block.S, CustomColor.CORAL);
        family2 = new Character(startingX + 15 * Block.S, startingY + 5 * Block.S, Color.BLUE);

        // Create fires
        fires = new ArrayList<Fire>();
        fires.add(new Fire(startingX + 8 * Block.S, startingY - 4 * Block.S));
        fires.add(new Fire(startingX + 8 * Block.S, startingY + 5 * Block.S));

        // Create blocks for the floor
        createRectOfBlocks(5, 2, startingX - Block.S, startingY + Block.S);
        blocks.add(new Block(startingX + 8 * Block.S, startingY + Block.S, Color.BLACK));
        blocks.add(new Block(startingX + 12 * Block.S, startingY + Block.S, Color.BLACK));
        createRectOfBlocks(30, 10, startingX + 4 * Block.S, startingY + 6 * Block.S);// bottom
        createRectOfBlocks(5, 6, startingX + 16 * Block.S, startingY + Block.S);
        createRectOfBlocks(10, 1, startingX + 8 * Block.S, startingY - 3 * Block.S); // floor of coral
        createRectOfBlocks(5, 17, startingX + 16 * Block.S, startingY - 19 * Block.S); // top
        createRectOfBlocks(10, 2, startingX + 20 * Block.S, startingY + Block.S); // floor of money
        createRectOfBlocks(10, 21, startingX + 22 * Block.S, startingY - 19 * Block.S); // top right
    }

    // reset characters to starting positions.
    @Override
    void resetLevel() {
        int startingX = Panel.W / 10 + 5 * Block.S;
        int startingY = Panel.H / 2 + Block.S;
        c = new Character(startingX, startingY, CustomColor.PINK);
        m = new Money(startingX + 21 * Block.S, startingY, panel);
        family1 = new Character(startingX + 15 * Block.S, startingY - 4 * Block.S, CustomColor.CORAL);
        family2 = new Character(startingX + 15 * Block.S, startingY + 5 * Block.S, Color.BLUE);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20));
        g.drawString("Reaching my goal was meant to hurt others.", 150, 150);

        // draw the characters
        m.draw(g);
        family1.draw(g);
        family2.draw(g);

        super.draw(g);
    }

    @Override
    public void move() {
        // The following variables define blocks that each character cannot move
        // through.
        ArrayList<Block> cBlocks = Utils.extend(blocks,
                new ArrayList<Block>(family1.isAlive() && family2.isAlive() ? Arrays.asList(family1, family2)
                        : family1.isAlive() ? Arrays.asList(family1)
                                : family2.isAlive() ? Arrays.asList(family2) : new ArrayList<Block>()));
        ArrayList<Block> f1Blocks = Utils.extend(blocks,
                new ArrayList<Block>(family2.isAlive() ? Arrays.asList(c, family2) : Arrays.asList(c)));
        ArrayList<Block> f2Blocks = Utils.extend(blocks,
                new ArrayList<Block>(family1.isAlive() ? Arrays.asList(c, family1) : Arrays.asList(c)));

        c.move(cBlocks);
        family1.move(f1Blocks);
        family2.move(f2Blocks);

        // Check collisions for ea. character with floor blocks and each other alive
        // character
        checkYCollisions(c, cBlocks);
        checkYCollisions(family1, f1Blocks);
        checkYCollisions(family2, f2Blocks);

        checkDeath(c);
        checkDeath(family1);
        checkDeath(family2);

        // If main character dies, reset the level
        if (!c.isAlive() && !hasWon) {
            resetLevel();
        }

        checkWin();
    }

    @Override
    protected void checkWin() {
        // If main character reaches money after having killed all the family, go to
        // next level
        if (!hasWon && !family1.isAlive() && !family2.isAlive() && c.intersects(m)) {
            panel.nextLevel(new Level9(panel));
            hasWon = true;
        }
    }

    // Gets the main and family characters to all move together when arrow keys are
    // pressed.
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        family1.keyPressed(e);
        family2.keyPressed(e);
    }

    // Stops both the main and family characters from moving when arrow keys are
    // released.
    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        family1.keyReleased(e);
        family2.keyReleased(e);
    }
}
