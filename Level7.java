/* Level7 class uses the invisible wall feature more. 
 * Main message: The pink block is desperate to the point of taking wild gambles. 
*/

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

public class Level7 extends Level {
    private Character m;
    private Character family;
    private ArrayList<Block> ledges = new ArrayList<Block>(); // ledges that are 1 way jumps.

    // Constructor method, initializes all characters and blocks
    public Level7(Panel panel) {
        this.panel = panel;
        createRectOfBlocks(1, Panel.H / Block.S + 1, 0, 0);
        createRectOfBlocks(1, Panel.H / Block.S + 1, Panel.W - Block.S, 0);
        createRectOfBlocks(9, 1, 8 * Block.S, 390);
        createRectOfBlocks(1, 5, 16 * Block.S, 240);
        createLedge(13 * Block.S, 330);

        resetLevel();
        m = new Money(Panel.W - Block.S * 10, 300, panel);
        createRectOfBlocks(3, 1, Panel.W - 11 * Block.S, 330); // Ledge for money to stand on
    }

    // Creates an x by y sized rectangle that is one way: you can jump through it
    // but cannot fall through it
    private void createLedge(int x, int y) {
        for (int i = 0; i < 6; i++)
            ledges.add(new Block(x + i * Block.S / 2, y, 15, Color.LIGHT_GRAY));
    }

    // Resets character positions
    @Override
    public void resetLevel() {
        c = new Character(12 * Block.S, 360, CustomColor.PINK);
        family = new Character(10 * Block.S, 360, Color.BLUE);
    }

    // Draws characters and text
    @Override
    public void draw(Graphics g) {
        m.draw(g);
        family.draw(g);
        super.draw(g);
        for (Block b : ledges) {
            b.draw(g);
        }

        g.setColor(Color.BLACK);
        Font font = new Font("Monospaced", Font.ITALIC, 20);
        g.setFont(font);
        FontMetrics m = g.getFontMetrics(font);
        String str = "I would step on my family just to get to money.";
        g.drawString(str, Panel.W / 2 - m.stringWidth(str) / 2, 100);
    }

    // Checks if character has touched money. If yes, the character has won and is
    // moved to the next level.
    @Override
    protected void checkWin() {
        if (m.intersects(c) && !hasWon) {
            panel.nextLevel(new Level8(panel));
            hasWon = true;
        }
    }

    // Specifies movement for when the character lands on a block or hits a block
    // going up
    protected void checkYCollisions(Character c, Character other) {
        ArrayList<Block> downwardBlocks = Utils.extend(blocks, ledges);
        ArrayList<Block> upwardBlocks = blocks;

        // if the character is not above any block or ledge or a non-falling other
        // character, it is falling
        if (!characterIsAboveABlock(c, downwardBlocks)
                && !(characterIsAboveOtherCharacter(c, other) && !other.isFalling)) {
            c.isFalling = true;
        }

        if (c.isFalling && c.yVelocity > 0) {
            // If the character collides with a block or ledge while falling downwards:
            for (Block b : downwardBlocks) {
                if (c.willIntersectY(b) && c.y < b.y) {
                    c.isFalling = false;
                    c.y = b.y - c.height;
                    break;
                }
            }
            // If character will collide with the other character and is above the other
            // character
            if (willIntersectY(c, other) && c.y < other.y) {
                if (other.isFalling) {
                    // If the other character is falling, set c to fall at the same rate such that
                    // they both fall together.
                    c.yVelocity = other.yVelocity;
                } else {
                    // If the other character is stationary, make c stationary.
                    c.isFalling = false;
                }
                c.y = other.y - c.height;
            }
        } else if (c.isFalling && c.yVelocity < 0) {
            // if character bumps into a block while going upwards
            for (Block b : upwardBlocks) {
                if (c.willIntersectY(b) && c.y > b.y) {
                    c.yVelocity = 0;
                    c.y = b.y + b.height;
                    break;
                }
            }
        }
    }

    // Checks if a character is above another character
    private boolean characterIsAboveOtherCharacter(Character c, Character other) {
        return c.x + c.width > other.x && c.x < other.x + other.width && c.height + c.y == other.y;
    }

    // Returns whether character will intersect rectangle after 1 more move()
    // Results only according to yVelocity
    private boolean willIntersectY(Character c, Character other) {
        return c.x + c.width > other.x && c.x < other.x + other.width
                && c.y + c.yVelocity + c.height > other.y + other.yVelocity
                && c.y + c.yVelocity < other.y + other.height + other.yVelocity;
    }

    // Checks whether 2 characters will intersect each other after 1 more move()
    // according to xVelocity and updates their positions to prevent collisions.
    private void checkCharacterXCollisions(Character char1, Character char2) {
        boolean willIntersectX;

        // If a character is not free moving, it means that it will update its position
        // in the next call of the c.move() method in a manner differently from its
        // xVelocity, because it is restrained by a block.
        boolean char1IsFreeMoving = char1.xVelocity == 0 || char1.xVelocity != 0 && !char1.willIntersectX(blocks);
        boolean char2IsFreeMoving = char2.xVelocity == 0 || char2.xVelocity != 0 && !char2.willIntersectX(blocks);

        if (char1IsFreeMoving && char2IsFreeMoving) {
            willIntersectX = char1.x + char1.xVelocity + char1.width > char2.x + char2.xVelocity
                    && char1.x + char1.xVelocity < char2.x + char2.width + char2.xVelocity;
        } else if (!char1IsFreeMoving) {
            // disregard char1's xVelocity
            willIntersectX = char1.x + char1.width > char2.x + char2.xVelocity
                    && char1.x < char2.x + char2.width + char2.xVelocity;
        } else {
            // char 2 is not free moving, so disregard its xVelocity.
            willIntersectX = char1.x + char1.xVelocity + char1.width > char2.x
                    && char1.x + char1.xVelocity < char2.x + char2.width;
        }
        willIntersectX = char1.intersectsY(char2) && willIntersectX;
        if (willIntersectX) {
            char1.xVelocity = 0;
            char2.xVelocity = 0;
            Character leftChar;
            Character rightChar;
            if (char1.x < char2.x) {
                leftChar = char1;
                rightChar = char2;
            } else {
                leftChar = char2;
                rightChar = char1;
            }
            leftChar.x = rightChar.x - leftChar.width; // there are only walls on the right.
        }
    }

    // Checks for x/y collisions with blocks and characters, checks win/death of
    // pink block, checks if family characters have died
    @Override
    public void move() {
        checkCharacterXCollisions(c, family);

        c.move(blocks);
        family.move(blocks);

        checkYCollisions(c, family);
        checkYCollisions(family, c);

        checkDeath(c);
        checkDeath(family);

        // If main character dies, reset the level
        if (!c.isAlive()) {
            resetLevel();
        }

        checkWin();
    }

    // Gets the character and the family to both move together when arrow keys are
    // pressed.
    @Override
    public void keyPressed(KeyEvent e) {
        characterKeyPressed(e, c, family);
        characterKeyPressed(e, family, c);
    }

    // Helper method to be called on each character when the key is pressed to
    // allow it to jump when it's on another character.
    private void characterKeyPressed(KeyEvent e, Character c, Character other) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            c.xVelocity = Character.SPEED * -1;
        }

        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            c.xVelocity = Character.SPEED;
        }

        else if (e.getKeyCode() == KeyEvent.VK_UP
                && (!c.isFalling || (characterIsAboveOtherCharacter(c, other) && other.isFalling))) {
            c.yVelocity = -Character.G;
            c.isFalling = true;
        }

    }

    // Stops both the character and the family from moving when arrow keys are
    // released.
    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        family.keyReleased(e);
    }
}
