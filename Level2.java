/* Level2 class introduces up movement key and establishes objective of each level (money)
 * Main message: Pink block is willing to take huge risks to get money.
*/

import java.awt.*;

public class Level2 extends Level {

    Character m; // Block representing money 

    // Image for up arrow
    private Image up;
    Toolkit t = Toolkit.getDefaultToolkit();

    // Constructor method, initializes all characters and blocks
    public Level2(Panel panel) {
        this.panel = panel;
        hasWon = false;

        // Initializes image by accessing the file
        up =  t.getImage("images/up.png");

        int panelW = Panel.W;
        int panelH = Panel.H;

        // Initialize characters
        c = new Character(50, (int) (panelH * 0.4), CustomColor.PINK);
        m = new Money(panelW - 60, panelH - 60, panel);

        // Create blocks for the floor
        createRectOfBlocks(5, 15, 0, (int) (panelH * 0.4) + Block.S);
        createRectOfBlocks(3, 1, (int) (panelW * 0.25), (int) (panelH * 0.35) + Block.S);
        createRectOfBlocks(3, 1, (int) (panelW * 0.4), (int) (panelH * 0.25) + Block.S);
        createRectOfBlocks(15, 1, (int) (panelW * 0.6), panelH);
        createRectOfBlocks(5, 1, (int) (panelW * 0.90), panelH - 30);
    }

    // Resets all character locations back to initial coordinates 
    void resetLevel() {
        int panelW = Panel.W;
        int panelH = Panel.H;
        c = new Character(50, (int) (panelH * 0.4), CustomColor.PINK);
        m = new Money(panelW - 60, panelH - 60, panel);
    }

    // Draws all blocks and characters onto the panel 
    public void draw(Graphics g) {

        // Draw the font
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); // TODO: find better font + standardize across all levels.
        g.drawString("It is prepared to sacrifice.", 550, 100);

        // Draw the characters
        m.draw(g);
        c.draw(g);

        // Draw the floor blocks
        for (Block b : blocks) {
            b.draw(g);
        }

        // Draw up arrow
        g.drawImage(up, 95, (int) (Panel.H * 0.4) + Block.S -75, panel);

    }
    
    // Specifies end level conditions
    // If the player touches the money block, start Level 3
    @Override
    protected void checkWin() {
        if (c.intersects(m) && !hasWon) {
            panel.nextLevel(new Level3(panel));
            hasWon = true;
        }
    }
}
