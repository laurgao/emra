import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

public class Level9 extends Level {
    private Character m; // block representing money
    private ArrayList<Fire> fires;
    private Panel panel;
    private boolean hasWon;
    private ArrayList<Block> ledges; // ledges that are 1 way jumps.

    public Level9(Panel panel) {
        this.panel = panel;
        hasWon = false;

        int startingY = (Panel.H * 3 / 4) / Block.S * Block.S;
        c = new Character(4 * Block.S, startingY - Block.S, CustomColor.PINK);
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
        createRectOfBlocks(1, Panel.H / Block.S - 5, Block.S * 28, 0 * Block.S); // middle wall

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
        for (int i = 0; i < 6; i++)
            ledges.add(new Block(15 * Block.S + i * Block.S / 2, startingY - 6 * Block.S, 15, Color.LIGHT_GRAY));

    }

    @Override
    public void resetLevel() {
        int startingY = Panel.H / 3 * 2;
        c = new Character(4 * Block.S, startingY - Block.S, CustomColor.PINK);
    }

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
    }

    @Override
    public void draw(Graphics g) {
        c.draw(g);
        for (Block b : Utils.extend(blocks, ledges)) {
            b.draw(g);
        }
        for (Fire f : fires) {
            f.draw(g);
        }
    }

    @Override
    protected void checkDeath(Character c) {
        super.checkDeath(c);
        for (Fire f : fires) {
            if (f.intersects(c)) {
                c.die();
            }
        }
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

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            c.xVelocity = 0;
        }
    }
}
