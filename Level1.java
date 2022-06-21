/* Level1 class introduces the main character, their family, and the left/right/up momvement keys. Also introduces camera feature
 * Main message: Pink block comes from a poor family (farmhouse).
*/

import java.awt.*;

public class Level1 extends Level {

    // Block representing money
    Character m;
    // Blocks representing family members
    Character family1;
    Character family2;
    Character family3;
    Character family4;
    private int[] camera; // camera represents the top left coords of the screen being displayed.

    private Image right;
    private Image left;
    private Image up;
    Toolkit t = Toolkit.getDefaultToolkit();

    // Constructor method, initializes all characters and blocks
    public Level1(Panel panel) {
        this.panel = panel;
        hasWon = false;

        int startingX = Panel.W;
        int startingY = Panel.H;

        // Initializes images by accessing files
        right =  t.getImage("images/right.png");
        left =  t.getImage("images/left.png");
        up =  t.getImage("images/up.png");

        // Initialize character positions
        c = new Character(265, (int) (startingY * 0.65) - 30, CustomColor.PINK);
        family1 = new Character(190, (int) (startingY * 0.65) - 30, Color.BLUE);
        family2 = new Character(300, (int) (startingY * 0.65) - 30, Color.PINK);
        family3 = new Character(225, (int) (startingY * 0.65) - 15, 15, Color.GRAY);
        family4 = new Character(245, (int) (startingY * 0.65) - 15, 15, Color.GREEN);

        // Initialize camera position
        camera = new int[] { 0, 0 };

        // Create blocks for silo
        createRectOfBlocks(3, 1, 0, (int) (Panel.H * 0.65) - 320);
        createRectOfBlocks(3, 1, 0, (int) (startingY * 0.65) - 240);
        createRectOfBlocks(3, 1, 0, (int) (startingY * 0.65) - 160);
        createRectOfBlocks(3, 1, 0, (int) (startingY * 0.65) - 80);
        createRectOfBlocks(3, 1, 0, (int) (startingY * 0.65));
        createRectOfBlocks(1, 10, 60, (int) (startingY * 0.65) - 290);
        createRectOfBlocks(2, 1, 0, (int) (startingY * 0.65) - 350);

        // Create blocks for the floor
        createRectOfBlocks(16, 1, 0, (int) (startingY * 0.65));
        createRectOfBlocks(17, 1, 0, (int) (startingY * 0.65) + 30);
        createRectOfBlocks(18, 1, 0, (int) (startingY * 0.65) + 60);
        createRectOfBlocks(19, 1, 0, (int) (startingY * 0.65) + 90);
        createRectOfBlocks(40, 15, 0, (int) (startingY * 0.65) + 120);

        // Create blocks for the walls
        createRectOfBlocks(1, 3, 150, (int) (startingY * 0.65) - 90);
        createRectOfBlocks(1, 2, 360, (int) (startingY * 0.65) - 90);

        // Create blocks for the roof
        createRectOfBlocks(2, 1, 120, (int) (startingY * 0.65) - 120);
        createRectOfBlocks(2, 1, 360, (int) (startingY * 0.65) - 120);
        createRectOfBlocks(2, 1, 150, (int) (startingY * 0.65) - 150);
        createRectOfBlocks(2, 1, 330, (int) (startingY * 0.65) - 150);
        createRectOfBlocks(2, 1, 180, (int) (startingY * 0.65) - 180);
        createRectOfBlocks(2, 1, 300, (int) (startingY * 0.65) - 180);
        createRectOfBlocks(4, 1, 210, (int) (startingY * 0.65) - 210);

        m = new Money(startingX - 60 + Panel.W, Panel.H + 199 - 2 * Block.S, panel);

        // Create blocks for the floor
        createRectOfBlocks(5, 15, Panel.W, (int) (startingY * 0.65) + 4 * Block.S);
        createRectOfBlocks(3, 1, (int) (startingX * 1.25), (int) (startingY * 0.35) + 9 * Block.S);
        createRectOfBlocks(3, 1, (int) (startingX * 1.4), (int) (startingY * 0.25) + 9 * Block.S);
        createRectOfBlocks(15, 1, (int) (startingX * 1.63), Panel.H + 199);
        createRectOfBlocks(5, 1, (int) (startingX * 1.9), Panel.H + 199 - Block.S);
    }

    // Resets character location back to checkpoint coordinates.
    // This is so the player doesn't have to restart the level from the beginning
    // when they die, which can be annoying for the player.
    void resetLevel() {
        c = new Character(1165, 499, CustomColor.PINK);
        camera = new int[] { 620, 199 };
    }

    // Draws all blocks and characters onto the panel
    public void draw(Graphics g) {
        // Draw the font
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20));
        g.drawString("True love is selfless.", 650 - camera[0], 250 - camera[1]);
        g.drawString("It is prepared to sacrifice.", 550 + Panel.W - camera[0], 300 - camera[1]);

        // Draw the characters
        c.draw(g, -camera[0], -camera[1]);
        family1.draw(g, -camera[0], -camera[1]);
        family2.draw(g, -camera[0], -camera[1]);
        family3.draw(g, -camera[0], -camera[1]);
        family4.draw(g, -camera[0], -camera[1]);
        m.draw(g, -camera[0], -camera[1]);

        // Draw the arrows
        g.drawImage(right, 280-camera[0], (int) (Panel.H * 0.65) - 110-camera[1], panel);
        g.drawImage(left, 215-camera[0], (int) (Panel.H * 0.65) - 110-camera[1], panel);
        g.drawImage(up, Panel.W + 5*30-20-camera[0], (int) (Panel.H * 0.65) + 4 * Block.S - 70-camera[1], panel);


        // Draw the floor blocks
        for (Block b : blocks) {
            b.draw(g, -camera[0], -camera[1]);
        }

        HomeScreen.completed = true;
    }

    // Updates camera position based on character location. Also checks character's x and y collisions
    @Override
    public void move() {
        super.move();

        camera[0] = Math.min(Math.max(0, c.x - (Panel.W / 2 - c.height / 2)), Panel.W); // do not let camera x go left
                                                                                        // of 0 or right of the panel
                                                                                        // width.
        int idealPos;
        if (c.x > Panel.W) {
            // c.y here at the beginning is 499.
            idealPos = 199;

            if (camera[1] < idealPos) {
                // if the character is jumping and camera is higher than if character was on the
                // ground, slowly move the camera in position.
                camera[1] += 3;
            } else {
                camera[1] = idealPos;
            }
        } else {
            idealPos = c.y - 300;
            if (camera[1] > idealPos) {
                // if the character is jumping and camera is lower than if character was on the
                // ground, slowly move the camera in position.
                camera[1] -= 3;
            } else {
                camera[1] = idealPos;
            }
        }

    }

    // Specifies end level conditions
    // If the player walks off screen, start Level 2
    @Override
    protected void checkWin() {
        if (c.intersects(m) && !hasWon) {
            panel.nextLevel(new Level2(panel));
            hasWon = true;
        }
    }
}