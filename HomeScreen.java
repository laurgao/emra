import java.awt.*;
import java.awt.event.*;


public class HomeScreen extends Level {

    String buttonText = "START";

    public HomeScreen(Panel panel) {
        this.panel = panel;
        createRectOfBlocks(6,5, (int)(Panel.W/2)-90, (int)(Panel.H*0.5), CustomColor.PINK);
    }

    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        if ((mouseX < (int)(Panel.W/2)-90 + 6*30) && (mouseX > (int)(Panel.W/2)-90) && (mouseY < (int)(Panel.H*0.5) + 5*30) && (mouseY >(int)(Panel.H*0.5))) {
            panel.nextLevel(new Level1(panel));
            Panel.titlePageDone = true;
        }
    }

    public void draw(Graphics g) {


        for(Block b : blocks) {
            b.draw(g); 
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 60)); // TODO: find better font + standardize across all levels.
        g.drawString("The Hedonic Paradox", 220, 200);

        // int[] xPoints={550, 550, 650};
        // int[] yPoints={380, 460, 420};
        int[] xPoints={(int)(Panel.W/2)-90+65, (int)(Panel.W/2)-90+65, (int)(Panel.W/2)-90+120};
        int[] yPoints={(int)(Panel.H*0.5)+50, (int)(Panel.H*0.5) +100, (int)(Panel.H*0.5)+75};
        int nPoints=3;
        g.setColor(Color.WHITE);
        g.fillPolygon(xPoints, yPoints, nPoints);
    }

    protected void createRectOfBlocks(int w, int h, int startingX, int startingY, Color color) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                blocks.add(new Block(startingX + i * 30, startingY + j * 30, color));
            }
        }
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


