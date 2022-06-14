import java.awt.*;
import java.util.ArrayList;

public class Level12 extends LevelWithFire {
    Character m;
    long startTime;
    ArrayList<Character> fallingBlocks;
    ArrayList<Block> invisibleBlocks;
    long elapsedMs;

    public Level12(Panel panel) {
        this.panel = panel;

        resetLevel();
        fires = new ArrayList<Fire>();
        createRectOfBlocks(4, 1, 600, 90); // headbump where fires stand
        for (int i = 0; i < 4; i++)
            fires.add(new Fire(600 + i * 30, 60));
        createRectOfBlocks(1, 7, 870, 240); // where money stands

        m = new Character(870, 210, CustomColor.MONEY);

    }

    protected void createFallingBlock(int startingX, int startingY) {
        fallingBlocks.add(new Character(startingX, startingY, Color.BLACK));
        invisibleBlocks.add(new Block(startingX, startingY + Block.S, Color.WHITE)); // add invisible block beneath
                                                                                     // falling block.
    }

    @Override
    protected void resetLevel() {
        fallingBlocks = new ArrayList<Character>();
        invisibleBlocks = new ArrayList<Block>();
        startTime = System.currentTimeMillis();
        elapsedMs = 0;
        c = new Character(150, 180, CustomColor.PINK);

        createFallingBlock(150, 210);
        createFallingBlock(240, 240);
        createFallingBlock(300, 180);
        createFallingBlock(390, 270);
        createFallingBlock(480, 330);
        createFallingBlock(570, 240);
        createFallingBlock(630, 180);
        createFallingBlock(690, 150);
        createFallingBlock(780, 270);
    }

    @Override
    protected void checkWin() {

    }

    @Override
    public void draw(Graphics g) {
        m.draw(g);
        super.draw(g);
        for (Character b : fallingBlocks) {
            b.draw(g);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20));
        g.drawString("Even as the world is crumbling", 300, 50);
    }

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

    @Override
    public void move() {
        c.move(extend(blocks, fallingBlocks));

        checkYCollisions(c, extend(blocks, fallingBlocks));
        for (Character b : fallingBlocks) {
            checkYCollisions(b, invisibleBlocks);
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
            invisibleBlocks.remove(0);
        }
        elapsedMs = newElapsedMs;
    }
}
