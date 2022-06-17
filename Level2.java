import java.awt.*;

public class Level2 extends Level {
    Character m; // block representing money

    public Level2(Panel panel) {
        this.panel = panel;
        hasWon = false;

        // starting x and y coordinates of main character
        int startingX = Panel.W;
        int startingY = Panel.H;
        c = new Character(50, (int) (startingY * 0.4), CustomColor.PINK);
        m = new Money(startingX - 60, startingY - 60, panel);

        // Create blocks for the floor
        createRectOfBlocks(5, 15, 0, (int) (startingY * 0.4) + Block.S);
        createRectOfBlocks(3, 1, (int) (startingX * 0.25), (int) (startingY * 0.35) + Block.S);
        createRectOfBlocks(3, 1, (int) (startingX * 0.4), (int) (startingY * 0.25) + Block.S);
        createRectOfBlocks(15, 1, (int) (startingX * 0.6), startingY);
        createRectOfBlocks(5, 1, (int) (startingX * 0.90), startingY - 30);
    }

    void resetLevel() {
        int startingX = Panel.W;
        int startingY = Panel.H;
        c = new Character(50, (int) (startingY * 0.4), CustomColor.PINK);
        m = new Money(startingX - 60, startingY - 60, panel);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); // TODO: find better font + standardize across all levels.
        g.drawString("It is prepared to sacrifice.", 550, 100);

        // draw the characters
        m.draw(g);
        c.draw(g);

        // draw the floor blocks
        for (Block b : blocks) {
            b.draw(g);
        }
    }

    @Override
    protected void checkWin() {
        if (c.intersects(m) && !hasWon) {
            panel.nextLevel(new Level3(panel));
            hasWon = true;
        }
    }
}
