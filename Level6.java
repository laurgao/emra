/* Level6 class uses camera pan feature. 
 * Main message: The pink block has reached all their goals and is now a rich man/woman.
*/

import java.awt.Color;
import java.awt.*;

public class Level6 extends Level {
    private Character m; // block representing money
    private int[] camera; // camera represents the top left coords of the screen being displayed.

    // Constructor method, initializes all characters, blocks, and camera
    public Level6(Panel panel) {
        this.panel = panel;
        camera = new int[] { 0, 0 };
        int cx = Panel.W / 2 - Block.S / 2; // starting x value of character
        int cy = Panel.H - 3 * Block.S;
        c = new Character(cx, cy, CustomColor.PINK);

        // Make floor
        for (int x = -31; x <= 30; x++) {
            blocks.add(new Block(cx + (x + 1) * Block.S, cy + 1 * Block.S, Color.BLACK));
            blocks.add(new Block(cx + (x + 1) * Block.S, cy + 2 * Block.S, Color.BLACK));
        }

        // Make 2 walls
        for (int x = 5; x <= 30; x++) {
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
        // top ledge + staircase
        for (int x = 1; x <= 5; x++)
            blocks.add(new Block(cx + Block.S * x, cy - 19 * Block.S, Color.BLACK));
        for (int x = 2; x <= 7; x++)
            blocks.add(new Block(cx + Block.S * x, cy - 20 * Block.S, Color.BLACK));
        for (int x = 3; x <= 7; x++)
            blocks.add(new Block(cx + Block.S * x, cy - 21 * Block.S, Color.BLACK));
        for (int x = 4; x <= 7; x++)
            blocks.add(new Block(cx + Block.S * x, cy - 22 * Block.S, Color.BLACK));
        for (int x = 5; x <= 7; x++)
            blocks.add(new Block(cx + Block.S * x, cy - 23 * Block.S, Color.BLACK));

        // put money on the top of the staircase
        m = new Money(cx + Block.S * 6, cy - 24 * Block.S, panel);

    }

    // reset characters to starting positions + reset camera position
    void resetLevel() {
        camera = new int[] { 0, 0 };
        int cx = Panel.W / 2 - Block.S / 2; // starting x value of character
        int cy = Panel.H - 3 * Block.S;
        c = new Character(cx, cy, CustomColor.PINK);
        m = new Money(cx + Block.S * 6, cy - 24 * Block.S, panel);
    }

    // Draws characters, and text onto the screen
    @Override
    public void draw(Graphics g) {

        // draw the characters
        m.draw(g, -camera[0], -camera[1]);
        c.draw(g, -camera[0], -camera[1]);

        // draw the floor blocks
        for (Block b : blocks) {
            b.draw(g, -camera[0], -camera[1]);
        }

        // Add text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20));
        g.drawString("To reach the top.", 150 - camera[0], -100 - camera[1]);
    }

    // Specifies end level conditions
    // If the player touches the money block, start Level 7
    @Override
    protected void checkWin() {
        if (c.intersects(m) && !hasWon) {
            panel.nextLevel(new Level7(panel));
            hasWon = true;
        }
    }

    // Checks for character's x and y collisions and updates camera position
    @Override
    public void move() {
        super.move();

        // Adjust camera based on character position
        if (!hasWon) {
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
    }
}
