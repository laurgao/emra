import java.util.ArrayList;
import java.awt.*;

public class Level6 extends Level {
    private Character m; // block representing money
    private Character stool;
    private Panel panel;

    public Level6(Panel panel) {
        this.panel = panel;

        // center x and y coordinates of screen
        int cy = Panel.W / 2;
        int cx = Panel.H / 2;
        c = new Character(cx - Block.S * 11 / 2, cy - Block.S * 7 / 2, CustomColor.PINK);
        m = new Character(cx, cy, CustomColor.MONEY);
        stool = new Character(cx + 15 * Block.S, cy + 5 * Block.S, Color.BLUE);

        // Create blocks for the floor
        createRectOfBlocks(13, 5, cx - Block.S * 13 / 2, cy - Block.S * 5 / 2);
    }

    // reset characters to starting positions.
    void resetLevel() {
        int startingX = Panel.W / 10 + 5 * Block.S;
        int startingY = Panel.H / 2 + Block.S;
        c = new Character(startingX, startingY, CustomColor.PINK);
        m = new Character(startingX + 21 * Block.S, startingY, CustomColor.MONEY);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); // TODO: find better font + standardize across all levels.
        g.drawString("Reaching my goal was meant to hurt others.", 150, 150);

        // draw the characters
        m.draw(g);
        c.draw(g);

        // draw the floor blocks
        for (Block b : blocks) {
            b.draw(g);
        }
    }

    public void move() {
        super.move();
    }
}
