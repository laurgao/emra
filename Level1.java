import java.awt.*;
import java.awt.event.*;

public class Level1 {
    Character c;

    public Level1() {
        c = new Character(Panel.W / 2, Panel.H / 2, new Color(255, 0, 255));
    }

    public void draw(Graphics g) {
        // draw the character
        c.draw(g);
    }

    public void keyPressed(KeyEvent e) {
        c.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        c.keyReleased(e);
    }
}
