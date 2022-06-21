import java.awt.*;
import java.util.ArrayList;

public class Level4 extends Level {
    private final int S = Block.S;
    private ArrayList<Fire> fires;
    private Character m;

    public Level4(Panel panel) {
        this.panel = panel;
        hasWon = false;
        int sy = 6; // starting y coordinate
        c = new Character(S, (sy + 2) * S, CustomColor.PINK);
        fires = new ArrayList<Fire>();
        createRectOfBlocks(3, 1, 0, (sy + 3) * S); // where character is sitting on at beginning
        createRectOfBlocks(1, 3, 6 * S, (sy + 3) * S);

        // Main left block
        createRectOfBlocks(1, 7, 8 * S, (sy + 1) * S);
        createRectOfBlocks(1, 9, 9 * S, (sy) * S);
        createRectOfBlocks(5, 11, 10 * S, (sy - 1) * S);
        fires.add(new Fire(11 * S, (sy - 2) * S));

        // Main right block
        createRectOfBlocks(1, 9, 18 * S, 6 * S);
        createRectOfBlocks(3, 7, 19 * S, 8 * S);
        fires.add(new Fire(19 * S, 7 * S));
        fires.add(new Fire(20 * S, 7 * S));

        createRectOfBlocks(1, 7, 25 * S, 5 * S);
        createRectOfBlocks(1, 1, 28 * S, 6 * S);
        createRectOfBlocks(1, 1, 30 * S, 7 * S);
        fires.add(new Fire(30 * S, 6 * S));
        createRectOfBlocks(1, 1, 32 * S, 5 * S);
        createRectOfBlocks(1, 4, 34 * S, 9 * S); // where money stands

        m = new Money(34 * S, 8 * S, panel);
    }

    @Override
    void resetLevel() {
        c = new Character(S, 8 * S, CustomColor.PINK);
    }

    @Override
    public void draw(Graphics g) {
        m.draw(g);
        super.draw(g);
        for (Fire f : fires) {
            f.draw(g);
        }

        g.setColor(Color.WHITE);
        Font font = new Font("Monospaced", Font.ITALIC, 20); // TODO: find better font + standardize across all levels.
        FontMetrics metrics = g.getFontMetrics(font);
        g.setFont(font);

        // Center the text on the block.
        int w = 7 * Block.S;
        int x = 240;
        g.drawString("Dodge any", x + (w - metrics.stringWidth("Dodge any")) / 2, 250);
        g.drawString("hardship.", x + (w - metrics.stringWidth("hardship")) / 2, 280);
    }

    @Override
    protected void checkDeath(Character c) {
        super.checkDeath(c);
        for (Fire f : fires) {
            if (f.intersects(c)) {
                c.die();
            }
        }
    }

     // Specifies end level conditions
    // If the player touches the money block, start Level 5
    @Override
    protected void checkWin() {
        if (c.intersects(m) && !hasWon) {
            panel.nextLevel(new Level5(panel));
            hasWon = true;
        }
    }
}
