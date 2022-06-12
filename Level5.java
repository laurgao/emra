import java.awt.*;
import java.util.ArrayList;

public class Level5 extends Level {
    private final int S = Block.S;
    private ArrayList<Fire> fires;
    private Character m;

    public Level5() {
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

        m = new Character(34 * S, 8 * S, CustomColor.MONEY);
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
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); // TODO: find better font + standardize across all levels.
        g.drawString("Dodge any hardship.", 250, 250);
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
}
