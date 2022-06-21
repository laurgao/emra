/* Level13 class uses the invisible wall feature more. 
 * Main message: The pink block is desperate to the point of taking wild gambles. 
*/

import java.awt.*;
import java.util.ArrayList;

public class Level13 extends Level {

    private ArrayList<Fire> fires;
    private ArrayList<Block> invisibleObstacles;

    public Level13(Panel panel) {
        this.panel = panel;
        hasWon = false;

        // Initialize character
        c = new Character(50, 270, CustomColor.PINK);

        invisibleObstacles = new ArrayList<Block>();
        fires = new ArrayList<Fire>();

        // create start and end platforms
        createRectOfBlocks(7, 1, 0, 300);
        createRectOfBlocks(7, 1, Panel.W - 210, 300);

        // Create invisible obstacles 
        createRectOfBlocks(invisibleObstacles, 2, 1, 300, 300, Color.BLACK);
        createRectOfBlocks(invisibleObstacles, 1, 1, 350, 300, Color.BLACK);
        createRectOfBlocks(invisibleObstacles, 2, 1, 450, 300, Color.BLACK);
        createRectOfBlocks(invisibleObstacles, 1, 1, 600, 300, Color.BLACK);
        createRectOfBlocks(invisibleObstacles, 2, 1, 680, 300, Color.BLACK);
        createRectOfBlocks(invisibleObstacles, 1, 1, 770, 300, Color.BLACK);

        // create fire platform
        createRectOfBlocks(11, 2, Panel.W / 2 - 180, 500);

        // add fires
        for (int i = 0; i < 11; i++) {
            fires.add(new Fire(Panel.W / 2 - 180 + i * 30, 470));
        }
    }

    // Reset character to starting position 
    @Override
    void resetLevel() {
        c = new Character(50, 270, CustomColor.PINK);
    }

    // Draws all blocks and characters onto the panel 
    @Override
    public void draw(Graphics g) {
        
        // Draw character
        c.draw(g);

        // Draw all blocks
        for (Block b : blocks) {
            b.draw(g);
        }

        // Draws fire
        for (Fire f : fires) {
            f.draw(g);
        }

        // Draw text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); 
        g.drawString("I will jump blindly to regain what was lost.", 300, 100);
    }

    // Checks for y collisions 
    @Override
    protected void checkYCollisions(Character c, ArrayList<Block> blocks) {

        if (c.isFalling && c.yVelocity > 0) {
            // If the character collides with a block or ledge while falling downwards:
            for (Block b : Utils.extend(blocks, invisibleObstacles)) {
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
        if (!characterIsAboveABlock(c, Utils.extend(blocks, invisibleObstacles))) {
            c.isFalling = true;
        }
    }

    // If player touches fire, the player dies
    @Override
    protected void checkDeath(Character c) {
        super.checkDeath(c);
        for (Fire f : fires) {
            if (f.intersects(c)) {
                c.die();
            }
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

    // Specifies end level conditions
    // If the player touches the money block, start Level 14
    protected void checkWin() {
        if (c.x > Panel.W && !hasWon) {
            panel.nextLevel(new Level14(panel));
            hasWon = true;
        }
    }
}
