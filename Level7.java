import java.awt.Color;
import java.awt.*;

public class Level7 extends Level {
    private Character m; // block representing money
    private int[] camera; // camera represents the top left coords of the screen being displayed.
    private VoidFunction winCallback;
    private boolean hasWon = false;

    public Level7(VoidFunction winCallback) {
        this.winCallback = winCallback;
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
    void resetLevel() {
        camera = new int[] { 0, 0 };
        int cx = Panel.W / 2 - Block.S / 2; // starting x value of character
        int cy = Panel.H - 3 * Block.S;
        c = new Character(cx, cy, CustomColor.PINK);
        m = new Character(cx + Block.S * 6, cy - 24 * Block.S, CustomColor.MONEY);
    }

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
        g.drawString("To reach the top", 150 - camera[0], 150 - camera[1]);
    }

    public void move() {
       super.move();
        if (c.intersects(m) && !hasWon) {
            winCallback.run();
            hasWon = true;
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
}
