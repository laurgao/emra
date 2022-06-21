/* Level10 class introduces special feature of zoomed in character. Has a button function.
 * Main message: Pink block has become narcissistic
*/

import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Level10 extends Level {

    private static final double G = 50;
    private static final double fallingYAcceleration = G / 20;
    private static final int SPEED = 14;
    private static final int UNIT = 220;

    private Character m; // block representing money

    private Block button;
    private boolean pressedButton;
    private int[] camera; // camera represents the top left coords of the screen being displayed.
    private ArrayList<Block> ledges;
    private ArrayList<Block> retractableWall;

    // Constructor method, initializes all characters and blocks
    public Level10(Panel panel) {
        this.panel = panel;
        hasWon = false;

        ledges = new ArrayList<Block>();
        retractableWall = new ArrayList<Block>();
        pressedButton = false;
        camera = new int[] { 0, 0 };

        // Initializes starting x and y values of character 
        int cx = Panel.W / 2 - 125; 
        int cy = Panel.H / 3 - 2 * Block.S;
        
        // Initializes charcters 
        c = new Character(cx, cy, UNIT, CustomColor.PINK);
        m = new Money(UNIT * 21, cy, UNIT, panel);

        // Creates red button
        button = new Block(cx - UNIT, cy - 4 * UNIT + 70, UNIT, 150, Color.RED);

        // Creates red retractable wall
        for (int i = 0; i < 6; i++) {
            retractableWall.add(new Block(19 * UNIT, cy - (UNIT * 4 - i * UNIT), UNIT, Color.red));
        }

        // Create borders
        createRectOfBlocks(30, 2, UNIT, 0, cy + UNIT);
        createRectOfBlocks(1, 4, UNIT, 0, -2 * UNIT);
        createRectOfBlocks(5, 8, UNIT, UNIT * 18, -12 * UNIT);
        createRectOfBlocks(1, 6, UNIT, UNIT * 23, -3 * UNIT);
        createRectOfBlocks(5, 9, UNIT, UNIT * 22, -12 * UNIT);
        createRectOfBlocks(4, 1, UNIT, UNIT * 18, -4 * UNIT);
        createRectOfBlocks(5, 10, UNIT, UNIT * 24, -5 * UNIT);

        // Create obstacles
        createRectOfBlocks(2, 1, UNIT, 5 * UNIT, cy);
        createRectOfBlocks(3, 5, UNIT, 11 * UNIT, cy - 4 * UNIT);
        createRectOfBlocks(2, 1, UNIT, 9 * UNIT, cy - 2 * UNIT);
        createRectOfBlocks(2, 1, UNIT, UNIT * 16, cy - 6 * UNIT);
        createRectOfBlocks(6, 1, UNIT, 8 * UNIT, cy - 8 * UNIT);
        createRectOfBlocks(3, 1, UNIT, 2 * UNIT, cy - 6 * UNIT);
        createRectOfBlocks(3, 1, UNIT, cx - 2 * UNIT + 5, cy - 3 * UNIT);
        createRectOfBlocks(2, 2, UNIT, 14 * UNIT, cy - UNIT);

        // Create ledges which are light grey blocks that are 1 way jumps
        for (int i = 0; i < 3; i++)
            ledges.add(new Block(14 * UNIT + i * 150, cy - 3 * UNIT, 150, Color.LIGHT_GRAY));
    }

    // Reset characters to starting positions + reset camera position
    void resetLevel() {
        camera = new int[] { 0, 0 };
        int cx = Panel.W / 2 - 125; 
        int cy = Panel.H / 3 - 2 * Block.S;
        c = new Character(cx, cy, UNIT, CustomColor.PINK);
        m = new Money(UNIT * 21, cy, UNIT, panel);

        //Resets retractable wall if button has been pressed priorly 
        if(pressedButton){
            for (int i = 0; i < 6; i++) {
                retractableWall.add(new Block(19 * UNIT, cy - (UNIT * 4 - i * UNIT), UNIT, Color.red));
            }
        }

        // Resets button to full height
        button = new Block(cx - UNIT, cy - 4 * UNIT + 70, UNIT, 150, Color.RED);
        pressedButton = false;
        
    }

    // Draws all blocks and characters onto the panel 
    public void draw(Graphics g) {

        // Draw the characters
        m.draw(g, -camera[0], -camera[1]);
        c.draw(g, -camera[0], -camera[1]);

        // If button has been pressed, make it smaller and remove the red wall to allow player to get to money 
        if (!pressedButton) {
            button.draw(g, -camera[0], -camera[1]);
            for (Block b : retractableWall) {
                b.draw(g, -camera[0], -camera[1]);
            }
        } else {
            retractableWall.removeAll(retractableWall);
            button.draw(g, 50, -camera[0], -camera[1] + 100);
        }

        // Draw the floor blocks
        for (Block b : Utils.extend(blocks, ledges)) {
            b.draw(g, -camera[0], -camera[1]);
        }

        // Draw text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20));
        g.drawString("This is How I saw the World", 300 - camera[0], 50 - camera[1]);
    }

    // Updates the character's x and y velocities after arrow keys are pressed
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            c.xVelocity = SPEED * -1;
        }

        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            c.xVelocity = SPEED;
        }

        else if (e.getKeyCode() == KeyEvent.VK_UP && !c.isFalling) {
            c.yVelocity = -G;
            c.isFalling = true;
        }

    }

    // Checks for x/y collisions, checks for button press, updates camera, and checks for win
    @Override
    public void move() {
        boolean willIntersectX = false;
        for (Block b : Utils.extend(blocks, retractableWall)) {
            boolean isCollidingFromLeft = c.willIntersectX(b) && c.x < b.x && c.xVelocity > 0;
            boolean isCollidingFromRight = c.willIntersectX(b) && c.x > b.x && c.xVelocity < 0;
            if (isCollidingFromLeft || isCollidingFromRight) {
                c.x = isCollidingFromLeft ? b.x - UNIT : b.x + b.width;
                willIntersectX = true;
                break;
            }
        }
        if (!willIntersectX) {
            c.x += c.xVelocity;
        }

        // Move in the y direction.
        c.yVelocity = c.isFalling ? c.yVelocity + fallingYAcceleration : 0;
        c.y += c.yVelocity;

        checkButton();

        checkYCollisions(c, blocks);

        checkDeath(c);

        //Update camera based on character's current position so that it is always centered on the player
        camera[0] = c.x - 435;
        camera[1] = c.y - 250;

        // If main character dies, reset the level
        if (!c.isAlive()) {
            resetLevel();
        }

        checkWin();
    }

    // Checks if the button has been pressed 
    public void checkButton() {
        if (c.intersects(button)) {
            pressedButton = true;
        }
    }

    // Checks for y collisions 
    @Override
    protected void checkYCollisions(Character c, ArrayList<Block> blocks) {
        
        if (c.willIntersectY(button) && pressedButton && c.yVelocity > 0) {
            c.isFalling = false;
            c.y = (button.y + 100) - c.height;
        } else {
            if (c.isFalling && c.yVelocity > 0) {
                // If the character collides with a block or ledge while falling downwards:
                for (Block b : Utils.extend(blocks, ledges)) {
                    if (c.willIntersectY(b) && c.y < b.y) {
                        c.isFalling = false;
                        c.y = b.y - c.height;
                        break;
                    }
                }

            } else if (c.isFalling && c.yVelocity < 0) {
                // If character bumps into a block while going upwards
                for (Block b : blocks) {
                    if (c.willIntersectY(b) && c.y > b.y) {
                        c.yVelocity = 0;
                        c.y = b.y + b.height;
                        break;
                    }
                }
            }

            // If the character is not above any block, it is falling
            if (!characterIsAboveABlock(c, Utils.extend(blocks, ledges))) {
                c.isFalling = true;
            }
        }

    }

    // Returns whether character will intersect rectangle after 1 more move()
    // Results only according to xVelocity
    public boolean willIntersectX(Rectangle r) {
        return c.x + c.xVelocity + c.height > r.x && c.x + c.xVelocity < r.x + r.width && c.y + c.height > r.y
                && c.y < r.y + r.height;
    }

    // Returns whether character will intersect rectangle after 1 more move()
    // Results only according to yVelocity
    public boolean willIntersectY(Rectangle r) {
        return c.x + c.height > r.x && c.x < r.x + r.width && c.y + c.yVelocity + c.height > r.y
                && c.y + c.yVelocity < r.y + r.height;
    }

    // Alternative method to create a rectangle 
    // Can customize the array the blocks are added to and the size of the blocks
    protected void createRectOfBlocks(int w, int h, int z, int startingX, int startingY) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                blocks.add(new Block(startingX + i * UNIT, startingY + j * UNIT, z, Color.BLACK));
            }
        }
    }

    // Specifies end level conditions
    // If the player touches the money block, start Level 11
    protected void checkWin() {
        if (c.intersects(m) && !hasWon) {
            panel.nextLevel(new Level11(panel));
            hasWon = true;
        }
    }
}
