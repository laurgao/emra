import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Level8 extends Level {
    private Character m; // block representing money
    private Character family1;
    private Character family2;
    private ArrayList<Fire> fires = new ArrayList<Fire>();

    public Level8() {
        // starting x and y coordinates of main character
        int startingX = Panel.W / 10 + 5 * Block.S;
        int startingY = Panel.H / 2 + Block.S;
        c = new Character(startingX, startingY, CustomColor.PINK);
        m = new Character(startingX + 21 * Block.S, startingY, CustomColor.MONEY);
        family1 = new Character(startingX + 15 * Block.S, startingY - 4 * Block.S, CustomColor.CORAL);
        family2 = new Character(startingX + 15 * Block.S, startingY + 5 * Block.S, Color.BLUE);

        // Create fires
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
    void resetLevel() {
        int startingX = Panel.W / 10 + 5 * Block.S;
        int startingY = Panel.H / 2 + Block.S;
        c = new Character(startingX, startingY, CustomColor.PINK);
        m = new Character(startingX + 21 * Block.S, startingY, CustomColor.MONEY);
        family1 = new Character(startingX + 15 * Block.S, startingY - 4 * Block.S, CustomColor.CORAL);
        family2 = new Character(startingX + 15 * Block.S, startingY + 5 * Block.S, Color.BLUE);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); // TODO: find better font + standardize across all levels.
        g.drawString("Reaching my goal was meant to hurt others.", 150, 150);

        // draw the characters
        m.draw(g);
        family1.draw(g);
        family2.draw(g);
        c.draw(g);

        // draw the fires
        for (Fire f : fires) {
            f.draw(g);
        }

        // draw the floor blocks
        for (Block b : blocks) {
            b.draw(g);
        }
    }

    private ArrayList<Block> extend(ArrayList<Block> list, ArrayList<Block> list2) {
        ArrayList<Block> newList = new ArrayList<Block>();
        for (Block b : list) {
            newList.add(b);
        }
        for (Block b : list2) {
            newList.add(b);
        }
        return newList;
    }

    public void move() {
        super.move();
        family1.move();
        family2.move();

        // Check collisions for ea. character with floor blocks and each other alive
        // character
        checkCollisions(c,
                extend(blocks,
                        new ArrayList<Block>(family1.isAlive() && family2.isAlive() ? Arrays.asList(family1, family2)
                                : family1.isAlive() ? Arrays.asList(family1)
                                        : family2.isAlive() ? Arrays.asList(family2) : new ArrayList<Block>())));
        checkCollisions(family1,
                extend(blocks, new ArrayList<Block>(family2.isAlive() ? Arrays.asList(c, family2) : Arrays.asList(c))));
        checkCollisions(family2,
                extend(blocks, new ArrayList<Block>(family1.isAlive() ? Arrays.asList(c, family1) : Arrays.asList(c))));

        checkDeath(family1);
        checkDeath(family2);

        // If main character reaches money after having killed all the family, go to
        // next level
        if (!family1.isAlive() && !family2.isAlive() && c.intersects(m)) {
            System.out.println("You win!");
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

    // Overriding the method in Level class to allow checking collisions with other
    // characters.
    private void checkCollisions(Character c, ArrayList<Block> blocks) {
        // check collisions
        if (c.isFalling && c.yVelocity > 0) {
            // If the character collides with a block while falling downwards:
            for (Block b : blocks) {
                if (c.willIntersect(b) && c.y < b.y) {
                    c.isFalling = false;
                    c.y = b.y - c.height;
                    break;
                }
            }
        } else if (c.isFalling && c.yVelocity < 0) {
            // if character bumps into a block while going upwards
            for (Block b : blocks) {
                if (c.willIntersect(b) && c.y > b.y) {
                    c.yVelocity = 0;
                    c.y = b.y + b.height;
                    break;
                }
            }
        }
        // If character collides with a block while moving sideways and not falling
        else if (c.xVelocity > 0) {
            // character is moving right
            for (Block b : blocks) {
                if (c.willIntersect(b) && c.x < b.x) {
                    c.xVelocity = 0;
                    c.x = b.x - c.width;
                    break;
                }
            }
        } else if (c.xVelocity < 0) {
            for (Block b : blocks) {
                if (c.willIntersect(b) && c.x > b.x) {
                    c.xVelocity = 0;
                    c.x = b.x + b.width;
                    break;
                }
            }
        }

        // if the character is not above any block, it is falling
        if (!characterIsAboveABlock(c, blocks)) {
            c.isFalling = true;
        }

    }

    private boolean characterIsAboveABlock(Character c, ArrayList<Block> blocks) {
        for (Block b : blocks) {
            if (c.x + c.width > b.x && c.x < b.x + b.width && c.height + c.y == b.y) {
                return true;
            }
        }
        return false;
    }

    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        family1.keyPressed(e);
        family2.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        super.keyPressed(e);
        family1.keyReleased(e);
        family2.keyReleased(e);
    }
}
