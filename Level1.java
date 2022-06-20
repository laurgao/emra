/* Level1 class introduces the main character, their family, and the left/right momvement keys.
 * Main message: Pink block comes from a poor family (farmhouse).
*/

import java.awt.*;

public class Level1 extends Level {

    // Blocks representing family members
    Character family1; 
    Character family2; 
    Character family3;
    Character family4;

    // Constructor method, initializes all characters and blocks 
    public Level1(Panel panel) {
        this.panel = panel;
        hasWon = false;
        
        // Initializes characters
        int startingY = Panel.H; 
        c = new Character(265, (int)(startingY*0.65)-30, CustomColor.PINK);
        family1 = new Character(190, (int)(startingY*0.65)-30, Color.BLUE);
        family2 = new Character(300, (int)(startingY*0.65)-30, Color.PINK);
        family3 = new Character(225, (int)(startingY*0.65)-15, 15, Color.GRAY); 
        family4 = new Character(245, (int)(startingY*0.65)-15, 15, Color.GREEN); 

        // Create blocks for silo 
        createRectOfBlocks(3, 1, 0, (int)(Panel.H*0.65)-320);
        createRectOfBlocks(3, 1, 0, (int)(startingY*0.65)-240);
        createRectOfBlocks(3, 1, 0, (int)(startingY*0.65)-160);
        createRectOfBlocks(3, 1, 0, (int)(startingY*0.65)-80);
        createRectOfBlocks(3, 1, 0, (int)(startingY*0.65));
        createRectOfBlocks(1, 10, 60, (int)(startingY*0.65)-290);
        createRectOfBlocks(2, 1, 0,(int)(startingY*0.65)-350);

        // Create blocks for the floor
        createRectOfBlocks(16, 1, 0, (int)(startingY*0.65));
        createRectOfBlocks(17, 1, 0, (int)(startingY*0.65)+30);
        createRectOfBlocks(18, 1, 0, (int)(startingY*0.65)+60);
        createRectOfBlocks(19, 1, 0, (int)(startingY*0.65)+90);
        createRectOfBlocks(75, 15, 0, (int)(startingY*0.65)+120);

        // Create blocks for the walls 
        createRectOfBlocks(1, 3, 150, (int)(startingY*0.65)-90);
        createRectOfBlocks(1, 2, 360 , (int)(startingY*0.65)-90);

        // Create blocks for the roof 
        createRectOfBlocks(2, 1, 120 , (int)(startingY*0.65)-120);
        createRectOfBlocks(2, 1, 360 , (int)(startingY*0.65)-120);
        createRectOfBlocks(2, 1, 150 , (int)(startingY*0.65)-150);
        createRectOfBlocks(2, 1, 330 , (int)(startingY*0.65)-150);
        createRectOfBlocks(2, 1, 180, (int)(startingY*0.65)-180);
        createRectOfBlocks(2, 1, 300, (int)(startingY*0.65)-180);
        createRectOfBlocks(4, 1, 210, (int)(startingY*0.65)-210);
    }

    // Resets all character locations back to initial coordinates 
    void resetLevel() {
        int startingY = Panel.H;
        c = new Character(265, (int)(startingY*0.65)-30, CustomColor.PINK);
        family1 = new Character(190, (int)(startingY*0.65)-30, Color.BLUE);
        family2 = new Character(300, (int)(startingY*0.65)-30, Color.PINK);
        family3 = new Character(225, (int)(startingY*0.65)-15, 15, Color.GRAY); 
        family4 = new Character(245, (int)(startingY*0.65)-15, 15, Color.GREEN); 
    }

    // Draws all blocks and characters onto the panel 
    public void draw(Graphics g) {

        // Draw the font 
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); 
        g.drawString("True love is selfless.", 650, 250);

        // Draw the characters
        c.draw(g);
        family1.draw(g);
        family2.draw(g);
        family3.draw(g);
        family4.draw(g);

        // Draw the floor blocks
        for (Block b : blocks) {
            b.draw(g);
        }
    }

    // Specifies end level conditions
    // If the player walks off screen, start Level 2 
    @Override
    protected void checkWin() {
        if (c.x > Panel.W && !hasWon) {
            panel.nextLevel(new Level2(panel));
            hasWon = true;
        }
    }
}