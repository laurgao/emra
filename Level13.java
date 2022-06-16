import java.awt.*;
import java.util.ArrayList;

public class Level13 extends Level {
    private ArrayList<Fire> fires;
    private ArrayList<Block> invisibleObstacles;

    public Level13(Panel panel) {
        this.panel = panel;
        hasWon = false;
        invisibleObstacles = new ArrayList<Block>();
        c = new Character(50,270, CustomColor.PINK);

        fires = new ArrayList<Fire>();

        //create start and end points
        createRectOfBlocks(7, 1, 0, 300);
        createRectOfBlocks(7, 1, Panel.W-210, 300);

        createRectOfBlocks(invisibleObstacles, 2, 1, 300, 300, Color.BLACK);
        createRectOfBlocks(invisibleObstacles, 1, 1, 350, 300, Color.BLACK);
        createRectOfBlocks(invisibleObstacles, 2, 1, 450, 300, Color.BLACK);
        createRectOfBlocks(invisibleObstacles, 1, 1, 600, 300, Color.BLACK);
        createRectOfBlocks(invisibleObstacles, 2, 1, 680, 300, Color.BLACK);
        createRectOfBlocks(invisibleObstacles, 1, 1, 770, 300, Color.BLACK);
        // createRectOfBlocks(invisibleObstacles, 2, 1, 760, 300, Color.BLACK);
        // createRectOfBlocks(invisibleObstacles, 1, 1, 840, 300, Color.BLACK);

        //create fire platform
        createRectOfBlocks(11, 2, Panel.W/2-180, 500);

        //add fires
        for(int i=0;i<11; i++) {
            fires.add(new Fire(Panel.W/2-180 + i*30,470));
        }

    }

    @Override
    void resetLevel() {
        c = new Character(50,270, CustomColor.PINK);
    }

    @Override
    public void draw(Graphics g) {
        c.draw(g);

        for (Block b : blocks) {
            b.draw(g);
        }           
        
        for (Fire f : fires) {
            f.draw(g);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 20)); // TODO: find better font + standardize across all levels.
        g.drawString("I will jump blindly to regain what was lost.", 300, 100);
    }

    @Override
    protected void checkYCollisions(Character c, ArrayList<Block> blocks) {
        // check collisions
         // Check y collisions:

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
            // if character bumps into a block while going upwards
            for (Block b : blocks) {
                if (c.willIntersectY(b) && c.y > b.y) {
                    c.yVelocity = 0;
                    c.y = b.y + b.height;
                    break;
                }
            }
        }

        // if the character is not above any block, it is falling
        if (!characterIsAboveABlock(c, Utils.extend(blocks, invisibleObstacles))) {
            c.isFalling = true;
        }


    }

    @Override
    protected void checkDeath(Character c) {
        super.checkDeath(c);
        for (Fire f : fires) {
            if (f.intersects(c)) {
                c.die();
            }
        }
    }

    @Override
    protected void checkWin() {
        if (c.x > Panel.W && !hasWon) {
            panel.nextLevel(new Level13(panel));
            hasWon = true;
        }
    }

    protected void createRectOfBlocks(ArrayList<Block> blockArray, int w, int h, int startingX, int startingY, Color color) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                blockArray.add(new Block(startingX + i * Block.S, startingY + j * Block.S, color));
            }
        }
    }
}
