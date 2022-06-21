/* Level12 uses timing features to make the blocks fall every few seconds.
 * Main point: the character's life is falling apart but she still continues to chase money desperately.
 */

import java.awt.*;
import java.util.ArrayList;

public class Level12 extends LevelWithFire {
    Character m;
    long startTime;
    ArrayList<Character> fallingBlocks;
    ArrayList<Block> invisibleBlocks;
    long elapsedMs;
    private final int offsetX = 40;
    private final int offsetY = 40;

    // Constructor method, initialize all characters, fire, and blocks 
    public Level12(Panel panel) {
        this.panel = panel;

        resetLevel();
        fires = new ArrayList<Fire>();
        createRectOfBlocks(4, 1, 600 + offsetX, 90 + offsetY); // headbump where fires stand
        for (int i = 0; i < 4; i++)
            fires.add(new Fire(600 + i * 30 + offsetX, 60 + offsetY));
        createRectOfBlocks(1, 7, 870 + offsetX, 240 + offsetY); // where money stands
        createRectOfBlocks(6, 1, 210 + offsetX, 420 + offsetY); // bottom left stand where fires stand
        for (int i = 0; i < 6; i++)
            fires.add(new Fire(210 + i * 30 + offsetX, 390 + offsetY));

        m = new Money(870 + offsetX, 210 + offsetY, panel);

    }

    // Creates a falling block and an invisible block beneath it.
    protected void createFallingBlock(int startingX, int startingY) {
        fallingBlocks.add(new Character(startingX, startingY, Color.BLACK));
        invisibleBlocks.add(new Block(startingX, startingY + Block.S, Color.WHITE)); // add invisible block beneath
                                                                                     // falling block.
    }

    // This method resets the character's and each of the falling blocks' positions
    // to their initial values.
    @Override
    protected void resetLevel() {
        fallingBlocks = new ArrayList<Character>();
        invisibleBlocks = new ArrayList<Block>();
        startTime = System.currentTimeMillis();
        elapsedMs = 0;
        c = new Character(150 + offsetX, 180 + offsetY, CustomColor.PINK);

        createFallingBlock(150 + offsetX, 210 + offsetY);
        createFallingBlock(240 + offsetX, 240 + offsetY);
        createFallingBlock(300 + offsetX, 180 + offsetY);
        createFallingBlock(390 + offsetX, 270 + offsetY);
        createFallingBlock(480 + offsetX, 330 + offsetY);
        createFallingBlock(570 + offsetX, 240 + offsetY);
        createFallingBlock(630 + offsetX, 180 + offsetY);
        createFallingBlock(690 + offsetX, 150 + offsetY);
        createFallingBlock(780 + offsetX, 270 + offsetY);
    }

    // Move on to the next level if the character touches money.
    @Override
    protected void checkWin() {
        if (c.intersects(m) && !hasWon) {
            hasWon = true;
            panel.nextLevel(new Level13(panel));
        }
    }

    // Draws characters, blocks, and font
    @Override
    public void draw(Graphics g) {
        m.draw(g);
        super.draw(g);
        for (Character b : fallingBlocks) {
            b.draw(g);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20));
        g.drawString("Even as the world is crumbling", 135, 100);

    }

    // Utility method to combine an arraylist of blocks and an arraylist of
    // characters into one arraylist of blocks.
    private static ArrayList<Block> extend(ArrayList<Block> list, ArrayList<Character> list2) {
        ArrayList<Block> newList = new ArrayList<Block>();
        for (Block b : list) {
            newList.add(b);
        }
        for (Block b : list2) {
            newList.add(b);
        }
        return newList;
    }

    // Moves the character and the falling blocks according to time elapsed since
    // the level started.
    @Override
    public void move() {
        c.move(extend(blocks, fallingBlocks));

        checkYCollisions(c, extend(blocks, fallingBlocks));
        for (Character b : fallingBlocks) {
            checkYCollisions(b, Utils.extend(invisibleBlocks, blocks));
            b.move(invisibleBlocks);
        }

        checkDeath(c);

        // If main character dies, reset the level
        if (!c.isAlive()) {
            resetLevel();
        }

        checkWin();
        long newElapsedMs = System.currentTimeMillis() - startTime; // time elapsed in milliseconds
        final int interval = 1000; // make a block fall every this many ms.
        if (newElapsedMs % interval < elapsedMs % interval && invisibleBlocks.size() > 0) {
            // Removing the first invisible block in the arraylist causes the leftmost
            // falling block to fall.
            invisibleBlocks.remove(0);
        }
        elapsedMs = newElapsedMs;
    }
}
