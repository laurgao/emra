import java.awt.*;
import java.util.ArrayList;

public class Level11 extends Level {
    Character family1;
    Character family2;
    Character family3;
    Character family4;
    Character m;
    private int[] camera; // camera represents the top left coords of the screen being displayed.
    private ArrayList<Block> invisibleWall;
    private ArrayList<Block> blackWall;
    private ArrayList<Block> blackPanel;

    public static final double G = 9.8; // gravity
    private static final double fallingYAcceleration = G / 20; // Found experimentally to be a good value for the

    public Level11(Panel panel) {
        this.panel = panel;
        hasWon = false;
        invisibleWall = new ArrayList<Block>();
        blackWall = new ArrayList<Block>();
        blackPanel = new ArrayList<Block>();
        camera = new int[] { 0, 0 };
        int panelHeight = Panel.H;
        int panelWidth = Panel.W;
        // int cx = 265;
        // int cy = (int)(panelHeight*0.65)-30;

        c = new Character(panelWidth - 75, (int) (panelHeight * 0.65) + 90, CustomColor.PINK);
        m = new Money(1510, panelHeight + 739 + 90, panel);

        family1 = new Character(190, (int) (panelHeight * 0.65) - 30, Color.BLUE);
        family2 = new Character(300, (int) (panelHeight * 0.65) - 30, Color.PINK);
        family3 = new Character(225, (int) (panelHeight * 0.65) - 15, 15, Color.GRAY);
        family4 = new Character(245, (int) (panelHeight * 0.65) - 15, 15, Color.GREEN);

        // Create blocks for silo
        createRectOfBlocks(4, 1, -30, (int) (panelHeight * 0.65) - 320);
        createRectOfBlocks(4, 1, -30, (int) (panelHeight * 0.65) - 240);
        createRectOfBlocks(4, 1, -30, (int) (panelHeight * 0.65) - 160);
        createRectOfBlocks(4, 1, -30, (int) (panelHeight * 0.65) - 80);
        createRectOfBlocks(4, 1, -30, (int) (panelHeight * 0.65));
        createRectOfBlocks(1, 12, 60, (int) (panelHeight * 0.65) - 290);
        createRectOfBlocks(1, 12, -60, (int) (panelHeight * 0.65) - 320);
        createRectOfBlocks(3, 1, -30, (int) (panelHeight * 0.65) - 350);

        // Create blocks for the floor
        createRectOfBlocks(30, 33, -450, (int) (panelHeight * 0.65));
        createRectOfBlocks(15, 1, 0, (int) (panelHeight * 0.65) + 30);
        blocks.add(new Block(480, (int) (panelHeight * 0.65) + 30, Color.black));
        createRectOfBlocks(15, 1, 0, (int) (panelHeight * 0.65) + 60);
        createRectOfBlocks(2, 1, 480, (int) (panelHeight * 0.65) + 60);
        createRectOfBlocks(15, 1, 0, (int) (panelHeight * 0.65) + 90);
        createRectOfBlocks(3, 1, 480, (int) (panelHeight * 0.65) + 90);

        createRectOfBlocks(35, 1, -600, (int) (panelHeight * 0.65) + 120);
        createRectOfBlocks(50, 1, 480, (int) (panelHeight * 0.65) + 120);

        createRectOfBlocks(blackPanel, 70, 70, 480, (int) (panelHeight * 0.65) + 120, Color.black);
        createRectOfBlocks(blackPanel, 1, 40, 450, panelHeight + 769, Color.BLACK);
        createRectOfBlocks(blackPanel, 30, 30, -450, panelHeight + 769, Color.black);

        // Create blocks for the walls
        createRectOfBlocks(1, 3, 150, (int) (panelHeight * 0.65) - 90);
        createRectOfBlocks(1, 3, 360, (int) (panelHeight * 0.65) - 90);

        // Create blocks for the roof
        createRectOfBlocks(2, 1, 120, (int) (panelHeight * 0.65) - 120);
        createRectOfBlocks(2, 1, 360, (int) (panelHeight * 0.65) - 120);
        createRectOfBlocks(2, 1, 150, (int) (panelHeight * 0.65) - 150);
        createRectOfBlocks(2, 1, 330, (int) (panelHeight * 0.65) - 150);
        createRectOfBlocks(2, 1, 180, (int) (panelHeight * 0.65) - 180);
        createRectOfBlocks(2, 1, 300, (int) (panelHeight * 0.65) - 180);
        createRectOfBlocks(4, 1, 210, (int) (panelHeight * 0.65) - 210);

        // create underground platform
        createRectOfBlocks(blocks, 3, 1, 420, panelHeight + 799 + 90, Color.WHITE);

        // create invisible wall
        for (int i = 0; i < 20; i++) {
            invisibleWall.add(new Block(420, i * 30, Color.black));
            invisibleWall.add(new Block(Panel.W, i * 30, Color.black));
            // invisibleWall.add(new Block(0 + 30*i, (int)(panelHeight*65)-180,
            // Color.BLACK));
        }

        for (int i = 0; i < 33; i++) {
            blackWall.add(new Block(450, (int) (panelHeight * 0.65) + i * 30, Color.BLACK));
            blackWall.add(new Block(450, (panelHeight + 829 + 90) + i * 30, Color.BLACK));
        }

        // create underground obstacles
        createRectOfBlocks(blocks, 2, 1, 600, panelHeight + 739 + 60, Color.WHITE);
        createRectOfBlocks(blocks, 5, 1, 800, panelHeight + 739 + 120, Color.WHITE);
        createRectOfBlocks(blocks, 3, 1, 1000, panelHeight + 739 + 210, Color.WHITE);
        createRectOfBlocks(blocks, 2, 1, 1150, panelHeight + 739 + 120, Color.WHITE);
        createRectOfBlocks(blocks, 1, 1, 1300, panelHeight + 739 + 60, Color.WHITE);
        createRectOfBlocks(blocks, 5, 1, 1450, panelHeight + 739 + 120, Color.WHITE);
    }

    void resetLevel() {
        camera = new int[] { 0, 0 };
        int panelHeight = Panel.H;
        int panelWidth = Panel.W;
        c = new Character(panelWidth - 75, (int) (panelHeight * 0.65) + 90, CustomColor.PINK);
        m = new Money(1510, panelHeight + 739 + 90, panel);
        family1 = new Character(190, (int) (panelHeight * 0.65) - 30, Color.BLUE);
        family2 = new Character(300, (int) (panelHeight * 0.65) - 30, Color.PINK);
        family3 = new Character(225, (int) (panelHeight * 0.65) - 15, 15, Color.GRAY);
        family4 = new Character(245, (int) (panelHeight * 0.65) - 15, 15, Color.GREEN);
    }

    @Override
    public void draw(Graphics g) {

        for (Block b : blackPanel) {
            b.draw(g, -camera[0], -camera[1]);
        }

        // draw the characters
        c.draw(g, -camera[0], -camera[1]);
        m.draw(g, -camera[0], -camera[1]);

        family1.draw(g, -camera[0], -camera[1]);
        family2.draw(g, -camera[0], -camera[1]);
        family3.draw(g, -camera[0], -camera[1]);
        family4.draw(g, -camera[0], -camera[1]);

        // draw the floor blocks
        for (Block b : Utils.extend(blocks, blackWall)) {
            b.draw(g, -camera[0], -camera[1]);
        }

        // Add text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); // TODO: find better font + standardize across all levels.
        g.drawString("I also realized when I turned around...", 600 - camera[0], 300 - camera[1]);
        g.setColor(Color.WHITE);
        g.drawString("...just how much I had lost.", 320 - camera[0], 1250 - camera[1]);
        g.drawString("Perhaps my chase for wealth", 1300 - camera[0], 1250 - camera[1]);
        g.drawString("was the beginning of my tragedy.", 1400 - camera[0], 1300 - camera[1]);

    }

    public void move() {

        boolean willIntersectX = false;
        for (Block b : Utils.extend(blocks, invisibleWall)) {
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

        checkYCollisions(c, blocks);

        camera[0] = c.x - (Panel.W / 2 - c.height / 2);
        camera[1] = c.y - 400;

        // If main character dies, reset the level
        if (!c.isAlive()) {
            resetLevel();
        }

        checkDeath(c);

        checkWin();
    }

    @Override
    protected void checkWin() {
        if (c.intersects(m) && !hasWon) {
            panel.nextLevel(new Level12(panel));
            hasWon = true;
        }
    }

    @Override
    protected void checkDeath(Character c) {
        // if character falls off the screen, the player has died
        if (c.y > Panel.H * 2.5) {
            c.die();
        }
    }

    protected void createRectOfBlocks(ArrayList<Block> blockArray, int w, int h, int startingX, int startingY,
            Color color) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                blockArray.add(new Block(startingX + i * Block.S, startingY + j * Block.S, color));
            }
        }
    }
}