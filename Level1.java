import java.awt.*;
import java.util.ArrayList;

public class Level1 extends Level {
    // Character c; // player-controlled main character
    Character family1; 
    Character family2; 
    Character family3;
    Character family4;

    // ArrayList<Block> blocks = new ArrayList<Block>();

    public Level1() {
        // starting x and y coordinates of main character
        int startingY = Panel.H; 
        c = new Character(190, startingY/2-30, CustomColor.PINK);
        family1 = new Character(100, startingY/2-30, Color.BLUE);
        family2 = new Character(225, startingY/2-30, Color.PINK);
        family3 = new Character(135, startingY/2 -15, 15, Color.GRAY); 
        family4 = new Character(155, startingY/2 -15, 15, Color.GREEN); 

        // Create blocks for the floor
        createRectOfBlocks(12, 1, 0, startingY/2);
        createRectOfBlocks(13, 1, 0, startingY/2+30);
        createRectOfBlocks(14, 1, 0, startingY/2+60);
        createRectOfBlocks(15, 1, 0, startingY/2+90);
        createRectOfBlocks(75, 15, 0, startingY/2+120);

        // Create blocks for the walls 
        createRectOfBlocks(1, 3, 60, startingY/2-90);
        createRectOfBlocks(1, 2, 270 , startingY/2-90);

        // Create blocks for the roof 
        createRectOfBlocks(2, 1, 30 , startingY/2-120);
        createRectOfBlocks(2, 1, 270 , startingY/2-120);
        createRectOfBlocks(2, 1, 60 , startingY/2-150);
        createRectOfBlocks(2, 1, 240 , startingY/2-150);
        createRectOfBlocks(2, 1, 90, startingY/2-180);
        createRectOfBlocks(2, 1, 210, startingY/2-180);
        createRectOfBlocks(4, 1, 120, startingY/2-210);
    }

    void resetLevel() {
        int startingY = Panel.H;
        c = new Character(160, startingY/2-30, CustomColor.PINK);
        family1 = new Character(120, startingY/2-30, Color.BLUE);
        family2 = new Character(195, startingY/2-30, Color.YELLOW);
        family3 = new Character(120, startingY/2 -15, 15, Color.GRAY); 
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); // TODO: find better font + standardize across all levels.
        g.drawString("It is prepared to sacrifice.", 550, 100);

        // draw the characters
        c.draw(g);
        family1.draw(g);
        family2.draw(g);
        family3.draw(g);

        // draw the floor blocks
        for (Block b : blocks) {
            b.draw(g);
        }
    }

    @Override
    public void move() {
        super.move();
        // If main character reaches money after having killed all the family, go to
        // next level
        if (c.x > Panel.W) {
            System.out.println("You win!");
        }
    }
}
