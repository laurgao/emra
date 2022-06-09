import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Level3 extends Level {
    Character c; // player-controlled main character
    Character m; // block representing money
    Character family1;
    Character family2;
    ArrayList<Block> blocks = new ArrayList<Block>();

    public Level3() {
        // starting x and y coordinates of main character
        int startingX = Panel.W;
        int startingY = Panel.H;
        c = new Character(50, startingY-60, CustomColor.PINK);
        m = new Character(startingX-50, startingY-60, CustomColor.MONEY);

        // Create blocks for the floor
        createRectOfBlocks(6, 15, startingX/2-70, (int)(startingY*0.4));
        createRectOfBlocks(70, 2, 0, startingY-30 );
    }

    private void resetLevel() {
        int startingX = Panel.W;
        int startingY = Panel.H;
        c = new Character(50, (int) (startingY*0.9), CustomColor.PINK);
        m = new Character(startingX-60, (int) (startingY*0.9), CustomColor.MONEY);
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
        g.drawString("Climb any challenge...", 150, 150);

        // draw the characters
        m.draw(g);
        c.draw(g);

        // draw the floor blocks
        for (Block b : blocks) {
            b.draw(g);
        }
    }

    public void move() {
        c.move();

        checkCollisions(c);

        checkDeath(c);

        // If main character dies, reset the level
        if (!c.isAlive()) {
            resetLevel();
        }

        // If main character reaches money after having killed all the family, go to
        // next level
        if (c.intersects(m)) {
            System.out.println("You win!");
        }
    }

    private void checkDeath(Character c) {
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
    }

    public void keyReleased(KeyEvent e) {
        c.keyReleased(e);
    }
}
