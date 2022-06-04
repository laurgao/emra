/* Abstract class Level dictates behaviours that all levels must follow. */

import java.awt.*;
import java.awt.event.*;

public abstract class Level {
    // move is constantly called from the Panel class
    public abstract void move();

    // move is constantly called from the Panel class after move is called
    public abstract void draw(Graphics g);

    public abstract void keyPressed(KeyEvent e);

    public abstract void keyReleased(KeyEvent e);
}
