import java.awt.*;

public class Money extends Character {
    private Panel panel;
    Toolkit t = Toolkit.getDefaultToolkit();
    private Image image;

    public Money(int x, int y, Panel panel) {
        super(x, y, CustomColor.MONEY);
        this.panel = panel;
        image = t.getImage("images/money.png");
    }

    public Money(int x, int y, int z, Panel panel) {
        super(x, y, z, CustomColor.MONEY);
        this.panel = panel;
        if (z == 220) {
            image = t.getImage("images/money-220.png");
        } else {
            image = t.getImage("images/money.png");
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawImage(image, x, y, panel);
    }

    @Override
    public void draw(Graphics g, int offsetX, int offsetY) {
        super.draw(g, offsetX, offsetY);
        g.drawImage(image, x + offsetX, y + offsetY, panel);
    }
}
