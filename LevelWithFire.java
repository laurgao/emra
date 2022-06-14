import java.util.ArrayList;
import java.awt.*;

abstract public class LevelWithFire extends Level {
    protected ArrayList<Fire> fires;

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        for (Fire f : fires) {
            f.draw(g);
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
}