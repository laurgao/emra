import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;

public class Level88 extends Level {
    Character m;
    Character family;
    private ArrayList<Block> ledges = new ArrayList<Block>(); // ledges that are 1 way jumps.

    public Level88(Panel panel) {
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

    private void createLedge(int x, int y) {
        for (int i = 0; i < 6; i++)
            ledges.add(new Block(x + i * Block.S / 2, y, 15, Color.LIGHT_GRAY));
    }

    @Override
    public void resetLevel() {
        c = new Character(12 * Block.S, 360, CustomColor.PINK);
        family = new Character(10 * Block.S, 360, Color.BLUE);
    }

    @Override
    public void draw(Graphics g) {
        m.draw(g);
        family.draw(g);
        super.draw(g);
        for (Block b : ledges) {
            b.draw(g);
        }

        g.setColor(Color.BLACK);
        Font font = new Font("Monospaced", Font.ITALIC, 16);
        g.setFont(font);
        FontMetrics m = g.getFontMetrics(font);
        String str = "I would step on my family just to get to money.";
        g.drawString(str, Panel.W / 2 - m.stringWidth(str) / 2, 100);
    }

    @Override
    protected void checkWin() {
        if (m.intersects(c) && !hasWon) {
            panel.nextLevel(new Level8(panel));
            hasWon = true;
        }
    }

    protected void checkYCollisions(Character c, Character other) {
        // Check y collisions:
        ArrayList<Block> downwardBlocks = Utils.extend(blocks, ledges);
        ArrayList<Block> upwardBlocks = blocks;
        if (c.isFalling && c.yVelocity > 0) {
            // If the character collides with a block or ledge while falling downwards:
            for (Block b : downwardBlocks) {
                if (c.willIntersectY(b) && c.y < b.y) {
                    c.isFalling = false;
                    c.y = b.y - c.height;
                    break;
                }
            }

            if (willIntersectY(c, other)) {
                c.isFalling = false;
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

        // if the character is not above any block or ledge, it is falling
        if (!characterIsAboveABlock(c, downwardBlocks)
                && !(characterIsAboveOtherCharacter(c, other) && !other.isFalling)) {
            c.isFalling = true;
        }
    }

    private boolean characterIsAboveOtherCharacter(Character c, Character other) {
        return c.x + c.width > other.x && c.x < other.x + other.width && c.height + c.y == other.y;
    }

    private boolean willIntersectY(Character c, Character other) {
        return c.x + c.width > other.x && c.x < other.x + other.width
                && c.y + c.yVelocity + c.height > other.y + other.yVelocity
                && c.y + c.yVelocity < other.y + other.height + other.yVelocity;
    }

    private void checkCharacterXCollisions(Character char1, Character char2) {
        boolean willIntersectX = char1.x + char1.xVelocity + char1.width > char2.x + char2.xVelocity
                && char1.x + char1.xVelocity < char2.x + char2.width + char2.xVelocity
                && char1.y + char1.height > char2.y
                && char1.y < char2.y + char2.height;
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

    @Override
    public void move() {
        checkCharacterXCollisions(c, family);

        // c.move(Utils.extend(blocks, new ArrayList<Block>(Arrays.asList(family))));
        // family.move(Utils.extend(blocks, new ArrayList<Block>(Arrays.asList(c))));
        c.move(blocks);
        family.move(blocks);

        // checkYCollisions(c, blocks,
        // Utils.extend(Utils.extend(blocks, ledges), new
        // ArrayList<Block>(Arrays.asList(family))));
        // checkYCollisions(family, blocks,
        // Utils.extend(Utils.extend(blocks, ledges), new
        // ArrayList<Block>(Arrays.asList(c))));
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

    @Override
    public void keyPressed(KeyEvent e) {
        characterKeyPressed(e, c, family);
        characterKeyPressed(e, family, c);

    }

    private void characterKeyPressed(KeyEvent e, Character c, Character other) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            c.xVelocity = c.SPEED * -1;
        }

        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            c.xVelocity = c.SPEED;
        }

        else if (e.getKeyCode() == KeyEvent.VK_UP
                && (!c.isFalling || (characterIsAboveOtherCharacter(c, other) && other.isFalling))) {
            c.yVelocity = -c.G;
            c.isFalling = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        family.keyReleased(e);
    }
}