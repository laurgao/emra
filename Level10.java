import java.awt.Color;
import java.awt.*;
import java.awt.event.*;

public class Level10 extends Level {

    private static final double G = 50;
    private static final double fallingYAcceleration = G/20;
    private static final int SPEED = 14; 
    private static final int UNIT = 220; 

    // private Character m; // block representing money
    private Character retractableWall;
    private Character button;
    private int[] camera; // camera represents the top left coords of the screen being displayed.
    // private VoidFunction winCallback;
    // private boolean hasWon = false;
    private boolean pressedButton;

    public Level10( ) {
        // this.winCallback = winCallback;
        pressedButton = false;
        camera = new int[] { 0, 0 };
        int cx = Panel.W / 2 - 125; // starting x value of character
        int cy = Panel.H/3  - 2*Block.S;
        c = new Character(cx, cy, UNIT, CustomColor.PINK);
        retractableWall = new Character(UNIT*21, cy-UNIT*4, UNIT, UNIT*4, Color.RED);
        button = new Character(cx-UNIT, cy-4*UNIT+70, UNIT, 150, Color.RED);
        // button = new Character(5*UNIT, cy -UNIT, UNIT, 150, Color.RED);

        // m = new Character(cx + Block.S * 6, cy - 24 * Block.S, CustomColor.MONEY);

        //create borders
        createRectOfBlocks(50, 2, UNIT, 0, cy+UNIT);
        createRectOfBlocks(1, 4, UNIT, 0, -2*UNIT);
        createRectOfBlocks(1, 10, UNIT, UNIT*18, -8*UNIT);

        //create obstacles 
        createRectOfBlocks(2, 1, UNIT, 5*UNIT, cy);
        createRectOfBlocks(3, 5, UNIT, 11*UNIT, cy -4*UNIT);
        createRectOfBlocks(2, 1, UNIT, 9*UNIT, cy-2*UNIT);
        createRectOfBlocks(2, 1, UNIT, UNIT*16, cy - 6*UNIT);
        createRectOfBlocks(6, 1, UNIT, 8*UNIT, cy -8*UNIT);
        createRectOfBlocks(3, 1, UNIT, 2*UNIT, cy - 6*UNIT);
        createRectOfBlocks(3, 1, UNIT, cx-2*UNIT+5, cy-3*UNIT);

    }

    // reset characters to starting positions + reset camera position
    void resetLevel() {
        camera = new int[] { 0, 0 };
        int cx = Panel.W / 2 - 125; // starting x value of character
        int cy = Panel.H/3  - 2*Block.S;
        c = new Character(cx, cy, UNIT, CustomColor.PINK);
        // m = new Character(cx + Block.S * 6, cy - 24 * Block.S, CustomColor.MONEY);
    }

    public void draw(Graphics g) {

        // draw the characters
        // m.draw(g, -camera[0], -camera[1]);
        c.draw(g, -camera[0], -camera[1]);

        if(!pressedButton) {
            button.draw(g,-camera[0], -camera[1]);
        } else {
            button.draw(g, 50, -camera[0], -camera[1]+100);
        }
        retractableWall.draw(g, -camera[0], -camera[1]);

        // draw the floor blocks
        for (Block b : blocks) {
            b.draw(g, -camera[0], -camera[1]);
        }

        // Add text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20));
        g.drawString("This is How I saw the World", 250 - camera[0], 150 - camera[1]);
    }

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

    public void move() {

        boolean willIntersectX = false;
        for (Block b : blocks) {
            boolean isCollidingFromLeft = c.willIntersectX(b) && c.x < b.x && c.xVelocity > 0;
            boolean isCollidingFromRight = c.willIntersectX(b) && c.x > b.x && c.xVelocity < 0;
            if (isCollidingFromLeft || isCollidingFromRight) {
                c.x = isCollidingFromLeft ? b.x - c.width : b.x + b.width;
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

        checkCollisions(c);

        checkDeath(c);

        camera[0] = c.x -435;
        camera[1] = c.y-250;

        // If main character dies, reset the level
        if (!c.isAlive()) {
            resetLevel();
        }

        // if (c.intersects(m)) {
        //     System.out.println("you won!");
        // }
    }

    public void checkButton() {
        if(c.intersects(button)) {
            pressedButton = true;
        }
    }

    // returns whether character will intersect rectangle after 1 more move() (but
    // only according to xVelocity)
    public boolean willIntersectX(Rectangle r) {
        return c.x + c.xVelocity + c.width > r.x && c.x + c.xVelocity < r.x + r.width && c.y + c.height > r.y && c.y < r.y + r.height;
    }

    public boolean willIntersectY(Rectangle r) {
        return c.x + c.width > r.x && c.x < r.x + r.width && c.y + c.yVelocity + c.height > r.y && c.y + c.yVelocity < r.y + r.height;
    }

    protected void createRectOfBlocks(int w, int h, int z, int startingX, int startingY) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                blocks.add(new Block(startingX + i * UNIT, startingY + j * UNIT, z, Color.BLACK));
            }
        }
    }

    // protected void checkCollisions(Character c) {
    //     super.checkCollisions(c);
    //     for
    // }
}
