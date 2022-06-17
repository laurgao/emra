import java.awt.*;
import java.awt.event.*;

import org.w3c.dom.events.MouseEvent;

public class HomeScreen extends Level {

    String buttonText = "START";

    public HomeScreen(Panel panel) {
        this.panel = panel;
        hasWon = false;

        createRectOfBlocks(5,3, (int)(Panel.W/3)-75, (int)(Panel.H*0.75));
    }

    public void mousePressed(MouseEvent e) {
        double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
        double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        if ((mouseX > (int)(Panel.W/3)-75 + 5*30) && (mouseX < (int)(Panel.W/3)-75) &&
        (mouseY > (int)(Panel.H*0.75) + 3*30) && (mouseY <(int)(Panel.H*0.75))) {
            panel.nextLevel(new Level1(panel));
            hasWon = true; 
        }
    }

    public void draw(Graphics g) {

        for(Block b : blocks) {
            b.draw(g); 
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 50)); // TODO: find better font + standardize across all levels.
        g.drawString("The Hedonic Paradox", 250, 200);

        
    }

    @Override
    protected void checkWin() {}
    @Override
    public void move() {}
    @Override
    void resetLevel() {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

}


