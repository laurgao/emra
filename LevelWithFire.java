/* Abstract class to be used as a template for levels that include fire.
 * 
 */

import java.util.ArrayList;
import java.awt.*;

abstract public class LevelWithFire extends Level {
    protected ArrayList<Fire> fires;

    // Draws character, blocks, and fire to the screen.
    @Override
    public void draw(Graphics g) {
        super.draw(g);

        // Draws fires
        for (Fire f : fires) {
            f.draw(g);
        }
    }

    // Kill the character if it touches any fire.
    @Override
    protected void checkDeath(Character c) {
        super.checkDeath(c);
        for (Fire f : fires) {
            if (f.intersects(c)) {
                c.die();
            }
        }
    }
}