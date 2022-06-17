/* Abstract class Level dictates behaviours that all levels must follow. */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import org.w3c.dom.events.MouseEvent;

public abstract class Level {

    protected Character c; // player-controlled main character
    protected ArrayList<Block> blocks = new ArrayList<Block>(); // floor blocks
    protected Panel panel;
    protected boolean hasWon = false;

    // draw is constantly called from the Panel class after move is called
    public void draw(Graphics g) {
        c.draw(g);
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
    protected abstract void checkWin();

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

    protected void checkYCollisions(Character c, ArrayList<Block> blocks) {
        // check collisions
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

    protected boolean characterIsAboveABlock(Character c, ArrayList<Block> blocks) {
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

    public void mousePressed(MouseEvent e){

    }
}
