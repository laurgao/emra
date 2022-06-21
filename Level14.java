/* Level14 class introduces the antigravity feature
 * Main message: If the pink block chooses money, they get a sad ending.
 * But if they choose family, they get a slightly less sad ending.
*/

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Level14 extends Level {

    // Initialize characters
    private Character m;
    private Character family1;
    private Character family2;
    private Character family3;
    private Character family4;

    private FireSprite fire;

    // center x and y coordinates of screen
    private int cx = Panel.W / 2;
    private int cy = Panel.H / 2;

    private String str1;
    private String str2;
    private String str3;
    private String str4;
    private float str1Alpha;
    private float str2Alpha;
    private float str3Alpha;
    private float str4Alpha;

    private long startTime; // when strings start fading in
    private boolean textHasStarted; // true if strings have started fading in

    // Declare button coordinates and dimensions (which are shared across multiple
    // methods)
    private final int buttonX = Panel.W * 4 / 5;
    private final int buttonY = cy - 40;
    private final int buttonW = 200;
    private final int buttonH = 80;

    private ArrayList<Block> goThroughBlocks;
    private ArrayList<Block> invisibleBlocks;
    private boolean isGravityReversed = false;

    // Constructor method, initializes all characters and blocks
    public Level14(Panel panel) {
        this.panel = panel;
        hasWon = false;
        resetLevel();

        m = new Money(cx + (int) (Block.S * 6.5), cy - (int) (Block.S * 3.5), panel);
        fire = new FireSprite(cx + (int) (Block.S * 6.5) - 10, cy - (int) (Block.S * 3.5) - 65, panel);

    }

    // Alternate method to create rectangles that can be passed through
    protected void createRectOfGoThroughBlocks(int w, int h, int startingX, int startingY) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                goThroughBlocks.add(new Block(startingX + i * Block.S, startingY + j * Block.S, Color.DARK_GRAY));
            }
        }
    }

    // Alternate method to create rectangles that cannot be seen or travelled
    // through
    protected void createRectOfInvisibleBlocks(int w, int h, int startingX, int startingY) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                invisibleBlocks.add(new Block(startingX + i * Block.S, startingY + j * Block.S, Color.DARK_GRAY));
            }
        }
    }

    // Resets character positions, strings, and alpha variables (for fade effect)
    @Override
    void resetLevel() {

        textHasStarted = false;

        str1 = "";
        str2 = "";
        str3 = "";
        str4 = "";

        str1Alpha = 0.0f;
        str2Alpha = 0.0f;
        str3Alpha = 0.0f;
        str4Alpha = 0.0f;

        isGravityReversed = false;

        int offsetX = 300;
        int y = 360;

        // Reset all characters
        c = new Character(cx - (int) (Block.S * 7.5), cy - Block.S * 7 / 2, CustomColor.PINK);
        family1 = new Character(190 + offsetX, y + 30 + Block.S, Color.BLUE);
        family2 = new Character(300 + offsetX, y + 30 + Block.S, Color.PINK);
        family3 = new Character(225 + offsetX, y + 30 + Block.S, 15, Color.GRAY);
        family4 = new Character(245 + offsetX, y + 30 + Block.S, 15, Color.GREEN);

        // Reset all blocks
        blocks = new ArrayList<Block>();
        invisibleBlocks = new ArrayList<Block>();
        goThroughBlocks = new ArrayList<Block>();

        // Create blocks for the floor
        createRectOfBlocks(15, 5, cx - (int) (Block.S * 15 / 2.0), cy - (int) (Block.S * 5 / 2.0));
        for (int i = 0; i < 1; i++)
            createRectOfBlocks(15 - 2 * (i + 1), 1, cx - (int) (Block.S * (6.5 - i)), cy + (int) (Block.S * (2.5 + i)));
        for (int i = 0; i < 5; i++)
            createRectOfBlocks(15 - 2 * (i + 1), 1, cx - (int) (Block.S * (6.5 - i)), cy - (int) (Block.S * (3.5 + i)));

        // Create blocks for the walls
        createRectOfGoThroughBlocks(1, 2, 150 + offsetX, y + 60);
        createRectOfBlocks(1, 2, 360 + offsetX, y + 60);

        // Create blocks for the roof
        createRectOfBlocks(2, 1, 120 + offsetX, y + 120);
        createRectOfBlocks(2, 1, 360 + offsetX, y + 120);
        createRectOfBlocks(2, 1, 150 + offsetX, y + 150);
        createRectOfBlocks(2, 1, 330 + offsetX, y + 150);
        createRectOfBlocks(2, 1, 180 + offsetX, y + 180);
        createRectOfBlocks(2, 1, 300 + offsetX, y + 180);
        createRectOfBlocks(4, 1, 210 + offsetX, y + 210);

        // Create invisible platform that allows character to return to family
        createRectOfInvisibleBlocks(5, 1, 360, 570);
    }

    // Draws characters, background, and text
    @Override
    public void draw(Graphics g) {
        m.draw(g);
        family1.draw(g);
        family2.draw(g);
        family3.draw(g);
        family4.draw(g);
        super.draw(g);
        for (Block b : goThroughBlocks) {
            b.draw(g);
        }

        g.setColor(Color.WHITE);
        Font font = new Font("Monospaced", Font.ITALIC, 15);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);

        int y = 160;
        int marginY = 20; // space between lines

        float increment = 0.01f; // adjust this to change the speed of the fade in/out
        y = Utils.drawStringWrap(g, "But it was too late.", metrics, cx - 85, y, 170);
        Graphics2D g2d = (Graphics2D) g;
        if (str1 != "") {
            str1Alpha = Math.min(str1Alpha + increment, 1.0f);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, str1Alpha));
            y = Utils.drawStringWrap(g2d, str1, metrics, cx - 125, y + marginY, 250);

            // Draw the fire sprite if the gravity is reversed.
            if (isGravityReversed) {
                fire.draw(g2d);
            }
        }

        if (str2 != "") {
            str2Alpha = Math.min(str2Alpha + increment, 1.0f);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, str2Alpha));

            y = Utils.drawStringWrap(g2d, str2, metrics, cx - 170, y + marginY, 340);
        }

        Font smallerFont = new Font("Monospaced", Font.ITALIC, 12);
        FontMetrics smallerMetrics = g2d.getFontMetrics(smallerFont);
        if (str3 != "") {
            g2d.setFont(isGravityReversed ? font : smallerFont);

            str3Alpha = Math.min(str3Alpha + increment, 1.0f);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, str3Alpha));

            y = Utils.drawStringWrap(g2d, str3, isGravityReversed ? metrics : smallerMetrics, cx - 170,
                    y + (isGravityReversed ? marginY : 10), 340);

        }

        if (str4 != "") {
            g2d.setFont(isGravityReversed ? smallerFont : font);
            str4Alpha = Math.min(str4Alpha + increment, 1.0f);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, str4Alpha));

            g2d.drawString(str4, cx - metrics.stringWidth(str4) / 2, y + (marginY));

            // Draw button for main menu
            Font buttonFont = new Font("Monospaced", Font.BOLD, 20);
            FontMetrics buttonMetrics = g.getFontMetrics(buttonFont);
            String buttonStr = "MAIN MENU";

            g2d.setColor(CustomColor.PINK);
            g2d.fillRect(buttonX, buttonY, buttonW, buttonH);
            g2d.setColor(Color.WHITE);
            g2d.setFont(buttonFont);
            g2d.drawString(buttonStr, buttonX + (buttonW - buttonMetrics.stringWidth(buttonStr)) / 2, cy + 5);

        }
        g2d.dispose();

    }

    // Helper method that returns whether the main character intersects at least 1
    // family member.
    private boolean cIntersectsFamily() {
        return c.intersects(family1) || c.intersects(family2) || c.intersects(family3) || c.intersects(family4);
    }

    // Allows character to move, has time function that makes text show up with time
    // delays
    @Override
    public void move() {
        if (!isGravityReversed) {
            // If character crosses the imaginary vertical halfway line while chasing money,
            // the money teleports away and the sad ending is initiated.
            if (!textHasStarted && c.x > cx) {
                str1 = "Thsi game was just a hedonic paradox.";
                m.x = 265 + 300;
                m.y = 360 + 30 + Block.S;
                startTime = System.currentTimeMillis();
                textHasStarted = true;

                // Make the invisible blocks and go through blocks disappear so that we can no
                // longer go back to family after they've chosen money.
                invisibleBlocks = new ArrayList<Block>();
                for (Block b : goThroughBlocks) {
                    blocks.add(new Block(b.x, b.y, Color.BLACK));
                }
                goThroughBlocks = new ArrayList<Block>();
            }
            if (textHasStarted && System.currentTimeMillis() - startTime > 3000) {
                str2 = "He who loves money is never satisfied by money, and he who loves wealth is never satisfied by income.";
            }

            if (textHasStarted && System.currentTimeMillis() - startTime > 6000) {
                str3 = "- Ecclesiastes 5:10";
            }

            if (textHasStarted && System.currentTimeMillis() - startTime > 9000) {
                str4 = "THE END";
            }
        } else {
            // If the character chooses the other fate and goes for the family, initiate the
            // alternative ending's text.

            if (!textHasStarted && cIntersectsFamily()) {
                str1 = "I realized my mistakes and returned to my family";
                startTime = System.currentTimeMillis();
                textHasStarted = true;
            }

            if (textHasStarted && System.currentTimeMillis() - startTime > 3000) {
                str2 = "...but I've already hurt them, and nothing will fully repair our relationship.";
            }

            if (textHasStarted && System.currentTimeMillis() - startTime > 6000) {
                str3 = "Now, I find myself both without the wealth I've worked for and without family. This game was just a hedonic paradox.";
            }

            if (textHasStarted && System.currentTimeMillis() - startTime > 9000) {
                str4 = "THE END";
            }
        }

        // Make gravity go reverse on touching the go through blocks
        for (Block b : goThroughBlocks) {
            if (b.intersects(c)) {
                isGravityReversed = true;

                // Get rid of invisible blocks when gravity gets reversed to avoid behavior that
                // appears glitchy.
                invisibleBlocks = new ArrayList<Block>();
            }
        }

        c.move(Utils.extend(blocks, invisibleBlocks));
        checkDeath(c);

        // If main character dies, reset the level
        if (!c.isAlive()) {
            resetLevel();
        }
        checkWin();

        // Do custom collision checking depending on the direction of gravity.
        if (isGravityReversed) {
            checkYCollisionsReverse(c, Utils.extend(blocks, invisibleBlocks));
            // Add an acceleration of -2*G to cancel out the default falling acceleration of
            // G, leading to a net acceleration of -G to simulate "upside-down gravity."
            c.yVelocity = c.isFalling ? c.yVelocity - 2 * Character.fallingYAcceleration : 0;
        } else {
            checkYCollisions(c, Utils.extend(blocks, invisibleBlocks));
        }
    }

    // Checks if the character has collided with a block while moving up or down
    protected void checkYCollisionsReverse(Character c, ArrayList<Block> blocks) {
        // check collisions
        if (c.isFalling && c.yVelocity > 0) {
            // If the character collides with a block while falling downwards:
            for (Block b : blocks) {
                if (c.willIntersectY(b) && c.y < b.y) {
                    c.yVelocity = 0;
                    c.y = b.y - c.height;
                    break;
                }
            }
        } else if (c.isFalling && c.yVelocity < 0) {
            // if character bumps into a block while going upwards
            for (Block b : blocks) {
                if (c.willIntersectY(b) && c.y > b.y) {
                    c.isFalling = false;
                    c.y = b.y + b.height;
                    break;
                }
            }
        }

        // if the character is not below any block, it is falling
        if (!characterIsBelowABlock(c, blocks)) {
            c.isFalling = true;
        }

    }

    // Checks if a character is a below a block
    protected boolean characterIsBelowABlock(Character c, ArrayList<Block> blocks) {
        for (Block b : blocks) {
            if (c.x + c.width > b.x && c.x < b.x + b.width && c.y == b.y + b.height) {
                return true;
            }
        }
        return false;
    }

    // Custom checkDeath method to account for when gravity is reversed.
    @Override
    protected void checkDeath(Character c) {
        if (!isGravityReversed) {
            super.checkDeath(c);
        } else {
            // The character dies if it "falls up" off the screen.
            if (c.y < -Panel.H * 0.5) {
                c.die();
            }
        }
    }

    // If user clicks button to return to home screen, redirect them to the loading
    // screen
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() > buttonX && e.getX() < buttonX + buttonW && e.getY() > buttonY && e.getY() < buttonY + buttonH) {
            panel.nextLevel(new LoadingScreen(panel));
            panel.loadingCompleted = false;
            panel.homeCompleted = false;
        }
    }

    // Left empty because this level cannot be won.
    @Override
    protected void checkWin() {
    }

    // Overrides the default behavior to allow jumps that go in the downward
    // direction when gravity is reversed.
    @Override
    public void keyPressed(KeyEvent e) {
        if (isGravityReversed) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                c.xVelocity = Character.SPEED * -1;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                c.xVelocity = Character.SPEED;
            } else if (e.getKeyCode() == KeyEvent.VK_UP && !c.isFalling) {
                c.yVelocity = Character.G;
                c.isFalling = true;
            }
        } else {
            super.keyPressed(e);
        }
    }

}
