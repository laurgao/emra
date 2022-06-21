/* Abstract class Level dictates behaviours that all levels must follow. */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public abstract class Level {

    protected Character c; // player-controlled main character
    protected ArrayList<Block> blocks = new ArrayList<Block>(); // floor blocks
    protected Panel panel;
    protected boolean hasWon = false;

    // draw is constantly called from the Panel class after move is called
    public void draw(Graphics g) {
        // Draw character
        c.draw(g);

        // Draw all blocks
        for (Block b : blocks) {
            b.draw(g);
        }
    }

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

    // move is constantly called from the Panel class
    public void move() {
        c.move(blocks);

        checkYCollisions(c);

        checkDeath(c);

        // If main character dies, reset the level
        if (!c.isAlive()) {
            resetLevel();
        }

        checkWin();
    }

    // Called when the player dies
    abstract void resetLevel();

    // Check if the player has won. If so, go to the next level.
    // Called frequently from the move method.
    protected abstract void checkWin();

    // Check if character has died. Called constantly from the move method.
    protected void checkDeath(Character c) {
        // if character falls off the screen, the player has died
        if (c.y > Panel.H * 1.5) {
            c.die();
        }
    }

    // Overload variant so that default behavior of this method is to only check
    // collisions with existing blocks.
    protected void checkYCollisions(Character c) {
        checkYCollisions(c, this.blocks);
    }

    // check collisions in the y direction between the character and blocks.
    // prevents the characters from crashing into the blocks by moving it if a
    // collision will happen.
    protected void checkYCollisions(Character c, ArrayList<Block> blocks) {
        if (c.isFalling && c.yVelocity > 0) {
            // If the character collides with a block while falling downwards:
            for (Block b : blocks) {
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

        // if the character is not above any block, it is falling
        if (!characterIsAboveABlock(c, blocks)) {
            c.isFalling = true;
        }

    }

    // Checks if a character is above a block
    protected boolean characterIsAboveABlock(Character c, ArrayList<Block> blocks) {
        for (Block b : blocks) {
            if (c.x + c.width > b.x && c.x < b.x + b.width && c.height + c.y == b.y) {
                return true;
            }
        }
        return false;
    }

    // Called from panel when a key is pressed
    // Passes the event to character to adjust the character's velocity when an
    // arrow key is pressed.
    public void keyPressed(KeyEvent e) {
        c.keyPressed(e);
    }

    // Called from panel when a key is released
    // Passes the event to character to adjust the character's velocity when an
    // arrow key is released.
    public void keyReleased(KeyEvent e) {
        c.keyReleased(e);
    }

    // Empty method used as a template for individual levels to implement if they
    // have buttons that require mouse event listening. Left blank if a level does
    // not require mouse events.
    public void mouseClicked(MouseEvent e) {

    }
}
