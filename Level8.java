import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Level8 extends Level {
    private Character c; // player-controlled main character
    private Character m; // block representing money
    private Character family1;
    private Character family2;
    private ArrayList<Block> blocks = new ArrayList<Block>();
    private ArrayList<Fire> fires = new ArrayList<Fire>();

    public Level8() {
        // starting x and y coordinates of main character
        int startingX = Panel.W / 10 + Block.S;
        int startingY = Panel.H / 2;
        c = new Character(startingX, startingY, CustomColor.PINK);
        m = new Character(startingX + 22 * Block.S, startingY, CustomColor.MONEY);
        family1 = new Character(startingX + 15 * Block.S, startingY - 4 * Block.S, CustomColor.CORAL);
        family2 = new Character(startingX + 15 * Block.S, startingY + 5 * Block.S, CustomColor.CORAL); // TODO: get a
                                                                                                       // 4th colour
                                                                                                       // (blue).

        // Create fires
        fires.add(new Fire(startingX + 8 * Block.S, startingY - 4 * Block.S));
        fires.add(new Fire(startingX + 8 * Block.S, startingY + 5 * Block.S));

        // Create blocks for the floor
        createRectOfBlocks(5, 2, startingX - Block.S, startingY + Block.S);
        blocks.add(new Block(startingX + 8 * Block.S, startingY + Block.S, Color.BLACK));
        blocks.add(new Block(startingX + 12 * Block.S, startingY + Block.S, Color.BLACK));
        createRectOfBlocks(20, 1, startingX + 4 * Block.S, startingY + 6 * Block.S);
        createRectOfBlocks(5, 6, startingX + 16 * Block.S, startingY + Block.S); // modified to be easier. originally: w
                                                                                 // = 4, startingx = 17
        createRectOfBlocks(10, 1, startingX + 8 * Block.S, startingY - 3 * Block.S);
        createRectOfBlocks(5, 7, startingX + 16 * Block.S, startingY - 9 * Block.S); // other modified one.
        createRectOfBlocks(2, 1, startingX + 21 * Block.S, startingY + Block.S);
        createRectOfBlocks(1, 11, startingX + 23 * Block.S, startingY - 9 * Block.S);
    }

    // reset characters to starting positions.
    private void resetLevel() {
        int startingX = Panel.W / 10 + Block.S;
        int startingY = Panel.H / 2;
        c = new Character(startingX, startingY, CustomColor.PINK);
        m = new Character(startingX + 22 * Block.S, startingY, CustomColor.MONEY);
        family1 = new Character(startingX + 15 * Block.S, startingY - 4 * Block.S, CustomColor.CORAL);
        family2 = new Character(startingX + 15 * Block.S, startingY + 5 * Block.S, CustomColor.CORAL);
    }

    // Create a rectangle of blocks with width and height given by number of blocks
    // and x and y coordinates given by pixel values of the top left corner of
    // rectangle
    private void createRectOfBlocks(int w, int h, int startingX, int startingY) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                blocks.add(new Block(startingX + i * Block.S, startingY + j * Block.S, Color.BLACK));
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); // TODO: find better font + standardize across all levels.
        g.drawString("Reaching my goal was meant to hurt others", 150, 150);

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

    public void move() {
        c.move();
        family1.move();
        family2.move();

        checkCollisions(c);
        checkCollisions(family1);
        checkCollisions(family2);

        checkDeath(c);
        checkDeath(family1);
        checkDeath(family2);

        // If main character dies, reset the level
        if (!c.isAlive()) {
            resetLevel();
        }

        // If main character reaches money after having killed all the family, go to
        // next level
        if (!family1.isAlive() && !family2.isAlive() && c.intersects(m)) {
            System.out.println("You win!");
        }
    }

    private void checkDeath(Character c) {
        for (Fire f : fires) {
            if (f.intersects(c)) {
                c.die();
            }
        }
        // if character falls off the screen, the player has died
        if (c.y > Panel.H * 1.5) {
            c.die();
        }
    }

    private void checkCollisions(Character c) {
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
        if (!characterIsAboveABlock(c)) {
            c.isFalling = true;
        }

    }

    private boolean characterIsAboveABlock(Character c) {
        for (Block b : blocks) {
            if (c.x + c.width > b.x && c.x < b.x + b.width && c.height + c.y == b.y) {
                return true;
            }
        }
        return false;
    }

    public void keyPressed(KeyEvent e) {
        c.keyPressed(e);
        family1.keyPressed(e);
        family2.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        c.keyReleased(e);
        family1.keyReleased(e);
        family2.keyReleased(e);
    }
}
