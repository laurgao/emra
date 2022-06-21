/* HomeScreen class identifies name of game and has a start button */

import java.awt.*;
import java.awt.event.*;

public class HomeScreen extends Level {

    // Constructor method that intializes button (rectangle)
    public HomeScreen(Panel panel) {
        this.panel = panel;

        createRectOfBlocks(6, 5, (int) (Panel.W / 2) - 90, (int) (Panel.H * 0.5), CustomColor.PINK);
    }

    // Checks if player has clicked on the button. If yes, player proceeds to next
    // level.
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        if ((mouseX < (int) (Panel.W / 2) - 90 + 6 * 30) && (mouseX > (int) (Panel.W / 2) - 90)
                && (mouseY < (int) (Panel.H * 0.5) + 5 * 30) && (mouseY > (int) (Panel.H * 0.5))) {
            panel.nextLevel(new Level111(panel));
        }
    }

    // Draws text and blocks
    public void draw(Graphics g) {

        // Draw button
        for (Block b : blocks) {
            b.draw(g);
        }

        // Draws triangle
        int[] xPoints = { (int) (Panel.W / 2) - 90 + 65, (int) (Panel.W / 2) - 90 + 65,
                (int) (Panel.W / 2) - 90 + 120 };
        int[] yPoints = { (int) (Panel.H * 0.5) + 50, (int) (Panel.H * 0.5) + 100, (int) (Panel.H * 0.5) + 75 };
        int nPoints = 3;
        g.setColor(Color.WHITE);
        g.fillPolygon(xPoints, yPoints, nPoints);

        // Adds text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.ITALIC, 60)); // TODO: find better font + standardize across all levels.
        g.drawString("The Hedonic Paradox", (int) (Panel.W / 2) - 350, 200);
    }

    // Alternative method to create a rectangle
    // Can customize the colour of the blocks
    protected void createRectOfBlocks(int w, int h, int startingX, int startingY, Color color) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                blocks.add(new Block(startingX + i * 30, startingY + j * 30, color));
            }
        }
    }

    // Empty method
    @Override
    protected void checkWin() {
    }

    // Empty method
    @Override
    public void move() {
    }

    // Empty method
    @Override
    void resetLevel() {
    }

    // Empty method
    @Override
    public void keyPressed(KeyEvent e) {
    }

    // Empty method
    @Override
    public void keyReleased(KeyEvent e) {
    }

}
