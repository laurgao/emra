/* Object representing the money character. Child of character.
 * Draws the money character with a dollar sign image on top.
 */

import java.awt.*;

public class Money extends Character {
    private Panel panel;
    Toolkit t = Toolkit.getDefaultToolkit();
    private Image image;

    // Creates a new money character.
    public Money(int x, int y, Panel panel) {
        super(x, y, CustomColor.MONEY);
        this.panel = panel;
        image = t.getImage("images/money.png");
    }

    // Creates a new money character with custom size.
    public Money(int x, int y, int size, Panel panel) {
        super(x, y, size, CustomColor.MONEY);
        this.panel = panel;
        if (size == 220) {
            image = t.getImage("images/money-220.png");
        } else {
            image = t.getImage("images/money.png");
        }
    }

    // Draws the money to the screen.
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawImage(image, x, y, panel);
    }

    // Draws the money to the screen with camera offset positions.
    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        super.draw(g, offsetX, offsetY);
        g.drawImage(image, x + offsetX, y + offsetY, panel);
    }
}
