import java.awt.Color;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

public class Level7 extends Level {
    private Character c; // player-controlled main character
    private Character m; // block representing money
    private ArrayList<Block> blocks = new ArrayList<Block>();
    private int[] camera; // camera represents the top left coords of the screen being displayed.

    public Level7() {
        camera = new int[] { 0, 0 };
        int cx = Panel.W / 2 - Block.S / 2; // starting x value of character
        int cy = Panel.H - 3 * Block.S;
        c = new Character(cx, cy, CustomColor.PINK);

        // Make floor
        for (int x = -7; x <= 6; x++) {
            blocks.add(new Block(cx + (x + 1) * Block.S, cy + 1 * Block.S, Color.BLACK));
            blocks.add(new Block(cx + (x + 1) * Block.S, cy + 2 * Block.S, Color.BLACK));
        }

        // Make 2 walls
        for (int x = 5; x <= 6; x++) {
            for (int y = 0; y <= 20; y++) {
                blocks.add(new Block(cx + (x + 1) * Block.S, cy - y * Block.S, Color.BLACK));
                blocks.add(new Block(cx - (x) * Block.S, cy - y * Block.S, Color.BLACK));
            }
        }

        // ledges
        for (int x = 2; x <= 5; x++)
            blocks.add(new Block(cx + Block.S * x, cy - 2 * Block.S, Color.BLACK));
        for (int x = 1; x <= 4; x++)
            blocks.add(new Block(cx - Block.S * x, cy - 5 * Block.S, Color.BLACK));
        for (int x = 2; x <= 5; x++)
            blocks.add(new Block(cx + Block.S * x, cy - 8 * Block.S, Color.BLACK));
        for (int x = 1; x <= 4; x++)
            blocks.add(new Block(cx - Block.S * x, cy - 11 * Block.S, Color.BLACK));
        for (int x = 2; x <= 5; x++)
            blocks.add(new Block(cx + Block.S * x, cy - 14 * Block.S, Color.BLACK));
        for (int x = 1; x <= 4; x++)
            blocks.add(new Block(cx - Block.S * x, cy - 17 * Block.S, Color.BLACK));
        for (int x = 2; x <= 5; x++)
            blocks.add(new Block(cx + Block.S * x, cy - 20 * Block.S, Color.BLACK));
        // staircase
        for (int x = 3; x <= 7; x++)
            blocks.add(new Block(cx + Block.S * x, cy - 21 * Block.S, Color.BLACK));
        for (int x = 4; x <= 7; x++)
            blocks.add(new Block(cx + Block.S * x, cy - 22 * Block.S, Color.BLACK));
        for (int x = 5; x <= 7; x++)
            blocks.add(new Block(cx + Block.S * x, cy - 23 * Block.S, Color.BLACK));

        // put money on the top of the staircase
        m = new Character(cx + Block.S * 6, cy - 24 * Block.S, CustomColor.MONEY);

    }

    // reset characters to starting positions + reset camera position
    private void resetLevel() {
        camera = new int[] { 0, 0 };
        int cx = Panel.W / 2 - Block.S / 2; // starting x value of character
        int cy = Panel.H - 3 * Block.S;
        c = new Character(cx, cy, CustomColor.PINK);
        m = new Character(cx + Block.S * 6, cy - 24 * Block.S, CustomColor.MONEY);
    }

    public void draw(Graphics g) {

        // draw the characters
        c.draw(g, -camera[0], -camera[1]);
        m.draw(g, -camera[0], -camera[1]);

        // draw the floor blocks
        for (Block b : blocks) {
            b.draw(g, -camera[0], -camera[1]);
        }

        // Add text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20));
        g.drawString("To reach the top", 150 - camera[0], 150 - camera[1]);
    }

    public void move() {
        c.move();
        checkCollisions(c);
        checkDeath(c);

        if (!c.isAlive()) {
            resetLevel();
        }

        if (c.intersects(m)) {
            System.out.println("You win!");
        }

        // Adjust camera based on character position
        int threshold = Panel.H / 3;
        if ((-camera[1] + c.y) < threshold) {
            camera[1]--;
        } else if ((-camera[1] + c.y) > Panel.H - 3 * Block.S) { // 3 * block side because that's the height of the
                                                                 // floor + character.
            // the lowest camera position 0.
            int newCameraY = camera[1] + 20;
            if (newCameraY > 0) {
                newCameraY = 0;
            }
            camera[1] = newCameraY;
        }
    }

    private void checkDeath(Character c) {
        // if character falls off the screen, the player has died
        if (c.y > Panel.H * 1.5) {
            c.die();
        }
    }

    public void keyPressed(KeyEvent e) {
        c.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        c.keyReleased(e);
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
}
