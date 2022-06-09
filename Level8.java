import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Level8 extends Level {
    private Character m; // block representing money
    private Character family1;
    private Character family2;
    private ArrayList<Fire> fires = new ArrayList<Fire>();

    public Level8() {
        // starting x and y coordinates of main character
        int startingX = Panel.W / 10 + Block.S;
        int startingY = Panel.H / 2;
        c = new Character(startingX, startingY, CustomColor.PINK);
        m = new Character(startingX + 22 * Block.S, startingY, CustomColor.MONEY);
        family1 = new Character(startingX + 15 * Block.S, startingY - 4 * Block.S, CustomColor.CORAL);
        family2 = new Character(startingX + 15 * Block.S, startingY + 5 * Block.S, CustomColor.CORAL); // TODO: get a
                                                                                                       // 4th colour
                                                                                                       // (blue).

        // Create fires
        fires.add(new Fire(startingX + 8 * Block.S, startingY - 4 * Block.S));
        fires.add(new Fire(startingX + 8 * Block.S, startingY + 5 * Block.S));

        // Create blocks for the floor
        createRectOfBlocks(5, 2, startingX - Block.S, startingY + Block.S);
        blocks.add(new Block(startingX + 8 * Block.S, startingY + Block.S, Color.BLACK));
        blocks.add(new Block(startingX + 12 * Block.S, startingY + Block.S, Color.BLACK));
        createRectOfBlocks(20, 1, startingX + 4 * Block.S, startingY + 6 * Block.S);
        createRectOfBlocks(5, 6, startingX + 16 * Block.S, startingY + Block.S); // modified to be easier. originally: w
                                                                                 // = 4, startingx = 17
        createRectOfBlocks(10, 1, startingX + 8 * Block.S, startingY - 3 * Block.S);
        createRectOfBlocks(5, 7, startingX + 16 * Block.S, startingY - 9 * Block.S); // other modified one.
        createRectOfBlocks(2, 1, startingX + 21 * Block.S, startingY + Block.S);
        createRectOfBlocks(1, 11, startingX + 23 * Block.S, startingY - 9 * Block.S);
    }

    // reset characters to starting positions.
    void resetLevel() {
        int startingX = Panel.W / 10 + Block.S;
        int startingY = Panel.H / 2;
        c = new Character(startingX, startingY, CustomColor.PINK);
        m = new Character(startingX + 22 * Block.S, startingY, CustomColor.MONEY);
        family1 = new Character(startingX + 15 * Block.S, startingY - 4 * Block.S, CustomColor.CORAL);
        family2 = new Character(startingX + 15 * Block.S, startingY + 5 * Block.S, CustomColor.CORAL);
    }


    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); // TODO: find better font + standardize across all levels.
        g.drawString("Reaching my goal was meant to hurt others", 150, 150);

        // draw the characters
        m.draw(g);
        family1.draw(g);
        family2.draw(g);
        c.draw(g);

        // draw the fires
        for (Fire f : fires) {
            f.draw(g);
        }

        // draw the floor blocks
        for (Block b : blocks) {
            b.draw(g);
        }
    }

    public void move() {
        super.move();
        family1.move();
        family2.move();

        checkCollisions(family1);
        checkCollisions(family2);

        checkDeath(family1);
        checkDeath(family2);

        // If main character reaches money after having killed all the family, go to
        // next level
        if (!family1.isAlive() && !family2.isAlive() && c.intersects(m)) {
            System.out.println("You win!");
        }
    }

    @Override
    protected void checkDeath(Character c) {
        for (Fire f : fires) {
            if (f.intersects(c)) {
                c.die();
            }
        }
        super.checkDeath(c);
    }

    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        family1.keyPressed(e);
        family2.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        super.keyPressed(e);
        family1.keyReleased(e);
        family2.keyReleased(e);
    }
}
