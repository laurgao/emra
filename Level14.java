/* Level14 class introduces the antigravity feature
 * Main message: The pink block is desperate to the point of taking wild gambles. 
*/

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Level14 extends Level {

    // Initialize characters
    Character m;
    Character family1;
    Character family2;
    Character family3;
    Character family4;

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
    private boolean textHasStarted; // when strings start fading in

    private int buttonX = Panel.W * 4 / 5;
    private int buttonY = cy - 40;
    private int buttonW = 200;
    private int buttonH = 80;

    private ArrayList<Block> goThroughBlocks = new ArrayList<Block>();
    private ArrayList<Block> invisibleBlocks = new ArrayList<Block>();
    private boolean isGravityReversed = false;

    // Constructor method, initializes all characters and blocks
    public Level14(Panel panel) {
        this.panel = panel;
        hasWon = false;
        int offsetX = 300;
        int y = 360;
        resetLevel();

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

        createRectOfInvisibleBlocks(5, 1, 60 + offsetX, y + 210);

        family1 = new Character(190 + offsetX, y + 30 + Block.S, Color.BLUE);
        family2 = new Character(300 + offsetX, y + 30 + Block.S, Color.PINK);
        family3 = new Character(225 + offsetX, y + 30 + Block.S, 15, Color.GRAY);
        family4 = new Character(245 + offsetX, y + 30 + Block.S, 15, Color.GREEN);

    }

    protected void createRectOfGoThroughBlocks(int w, int h, int startingX, int startingY) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                goThroughBlocks.add(new Block(startingX + i * Block.S, startingY + j * Block.S, Color.DARK_GRAY));
            }
        }
    }

    protected void createRectOfInvisibleBlocks(int w, int h, int startingX, int startingY) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                invisibleBlocks.add(new Block(startingX + i * Block.S, startingY + j * Block.S, Color.DARK_GRAY));
            }
        }
    }

    @Override
    void resetLevel() {
        c = new Character(cx - (int) (Block.S * 7.5), cy - Block.S * 7 / 2, CustomColor.PINK);
        m = new Money(cx + (int) (Block.S * 6.5), cy - (int) (Block.S * 3.5), panel);
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
    }

    @Override
    protected void checkWin() {

    }

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
        Font font = new Font("Monospaced", Font.ITALIC, 16); // TODO: find better font + standardize across all levels.
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);

        int y = 160;
        int marginY = 20; // space between lines

        float increment = 0.01f; // adjust this to change the speed of the fade in/out
        y = drawStringWrap(g, "But it was too late.", metrics, cx - 85, y, 170);
        Graphics2D g2d = (Graphics2D) g;
        if (str1 != "") {
            str1Alpha = Math.min(str1Alpha + increment, 1.0f);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, str1Alpha));
            y = drawStringWrap(g2d, str1, metrics, cx - 125, y + marginY, 250);

        }

        if (str2 != "") {
            str2Alpha = Math.min(str2Alpha + increment, 1.0f);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, str2Alpha));

            y = drawStringWrap(g2d, str2, metrics, cx - 170, y + marginY, 340);
        }

        Font font2 = new Font("Monospaced", Font.ITALIC, 12);
        FontMetrics metrics2 = g2d.getFontMetrics(font2);
        if (str3 != "") {
            g2d.setFont(font2);

            str3Alpha = Math.min(str3Alpha + increment, 1.0f);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, str3Alpha));

            g2d.drawString(str3, cx - metrics2.stringWidth(str3) / 2, y + (10));

        }

        if (str4 != "") {
            g2d.setFont(font);
            str4Alpha = Math.min(str4Alpha + increment, 1.0f);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, str4Alpha));

            g2d.drawString(str4, cx - metrics.stringWidth(str4) / 2, y + (marginY) + metrics2.getHeight() + 20);

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

    // Helper method to draw a string with word wrapping.
    // Returns the y position of the bottom of the last line.
    private int drawStringWrap(Graphics g, String str, FontMetrics metrics, int initialX, int initialY, int width) {
        final double lineHeightFactor = 0.9;
        String[] words = str.split(" ");
        int lineHeight = (int) (lineHeightFactor * metrics.getHeight());

        String currLine = "";
        int y = initialY;
        for (String word : words) {
            int wordWidth = metrics.stringWidth(word);
            // If adding this word would make the line overflow, draw the current line.
            if (wordWidth + metrics.stringWidth(currLine) > width) {
                g.drawString(currLine, initialX + (width - metrics.stringWidth(currLine)) / 2, y);
                currLine = "";
                y += lineHeight;
            }
            currLine += word + " ";
        }
        g.drawString(currLine, initialX + (width - metrics.stringWidth(currLine)) / 2, y);
        return y + lineHeight;
    }

    @Override
    public void move() {
        if (!textHasStarted && c.x > cx) {
            str1 = "All of this was just a hedonic paradox.";
            m.x = 265 + 300;
            m.y = 360 + 30 + Block.S;
            startTime = System.currentTimeMillis();
            textHasStarted = true;

            // Make the invisible blocks disappear so that we can no longer go back to
            // family
            invisibleBlocks = new ArrayList<Block>();
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

        // Make gravity go reverse on touching the go through blocks
        for (Block b : goThroughBlocks) {
            if (b.intersects(c)) {
                isGravityReversed = true;
            }
        }

        if (isGravityReversed) {
            checkYCollisionsReverse(c, Utils.extend(blocks, invisibleBlocks));

            c.move(Utils.extend(blocks, invisibleBlocks));
            c.yVelocity = c.isFalling ? c.yVelocity + -2 * c.fallingYAcceleration : 0;

            checkYCollisions(c);

            checkDeath(c);

            // If main character dies, reset the level
            if (!c.isAlive()) {
                resetLevel();
            }

            checkWin();
        } else {
            c.move(Utils.extend(blocks, invisibleBlocks));

            checkYCollisions(c, Utils.extend(blocks, invisibleBlocks));

            checkDeath(c);

            // If main character dies, reset the level
            if (!c.isAlive()) {
                resetLevel();
            }

            checkWin();

        }
    }

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

        // if the character is not above any block, it is falling
        if (!characterIsBelowABlock(c, blocks)) {
            c.isFalling = true;
        }

    }

    protected boolean characterIsBelowABlock(Character c, ArrayList<Block> blocks) {
        for (Block b : blocks) {
            if (c.x + c.width > b.x && c.x < b.x + b.width && c.height == b.y + b.height) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() > buttonX && e.getX() < buttonX + buttonW && e.getY() > buttonY && e.getY() < buttonY + buttonH) {
            panel.nextLevel(new LoadingScreen(panel));
            HomeScreen.completed = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            c.xVelocity = c.SPEED * -1;
        }

        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            c.xVelocity = c.SPEED;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP && !c.isFalling) {
            c.yVelocity = isGravityReversed ? c.G : -1 * c.G;
            c.isFalling = true;
        }

    }

}
