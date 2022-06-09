/* Abstract class Level dictates behaviours that all levels must follow. */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public abstract class Level {

    protected Character c; 
    
    ArrayList<Block> blocks = new ArrayList<Block>();

    // // move is constantly called from the Panel class
    // public abstract void move();

    // // move is constantly called from the Panel class after move is called
    public abstract void draw(Graphics g);

    // public abstract void keyPressed(KeyEvent e);

    // public abstract void keyReleased(KeyEvent e);

    
    // Create a rectangle of blocks with width and height given by number of blocks
    // and x and y coordinates given by pixel values of the top left corner of
    // rectangle
    protected void createRectOfBlocks(int w, int h, int startingX, int startingY) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                blocks.add(new Block(startingX + i * Block.S, startingY + j * Block.S, Color.BLACK));
            }
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
    }

    abstract void resetLevel();

    protected void checkDeath(Character c) {
        // if character falls off the screen, the player has died
        if (c.y > Panel.H * 1.5) {
            c.die();
        }
    }

    protected void checkCollisions(Character c) {
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

    protected boolean characterIsAboveABlock(Character c) {
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
