import java.awt.*;

public class Level3 extends Level {
    Character m; // block representing money
    Character family1;
    Character family2;

    public Level3() {
        // starting x and y coordinates of main character
        int startingX = Panel.W;
        int startingY = Panel.H;
        c = new Character(50, startingY-60, CustomColor.PINK);
        m = new Character(startingX-50, startingY-60, CustomColor.MONEY);

        // Create blocks for the floor
        createRectOfBlocks(6, 15, startingX/2-70, (int)(startingY*0.4));
        createRectOfBlocks(70, 2, 0, startingY-30 );
    }

    void resetLevel() {
        int startingX = Panel.W;
        int startingY = Panel.H;
        c = new Character(50, (int) (startingY*0.9), CustomColor.PINK);
        m = new Character(startingX-60, (int) (startingY*0.9), CustomColor.MONEY);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); // TODO: find better font + standardize across all levels.
        g.drawString("Climb any challenge...", 150, 150);

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
        // If main character reaches money after having killed all the family, go to
        // next level
        if (c.intersects(m)) {
            System.out.println("You win!");
        }
    }
}
