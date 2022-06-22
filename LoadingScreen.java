import java.awt.event.*;
import java.awt.*;

public class LoadingScreen extends Level {
    private long startTime;

    public LoadingScreen(Panel panel) {
        this.panel = panel;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void draw(Graphics g) {
        // Reset these two variables so that the mute button and replay button don't get
        // displayed once the loading screen starts getting drawn
        panel.loadingCompleted = false;
        panel.homeCompleted = false;

        long elapsedMs = System.currentTimeMillis() - startTime;

        int length = 5000; // number of ms we want the loading screen to last
        int width = 400; // width of loading screen rectangle

        int currWidth = Math.min((int) elapsedMs * width / length, width);

        // Draw pink bg
        g.setColor(CustomColor.PINK);
        g.fillRect(0, 0, Panel.W, Panel.H);

        // Draw text on the center of the screen
        g.setColor(Color.WHITE);
        Font loadingFont = new Font("Monospaced", Font.ITALIC, 40);
        g.setFont(loadingFont);
        Utils.drawStringCenter(g, "Loading...", Panel.H / 2 - 50);

        // Draw loading bar
        int wOuter = width + 10;
        g.drawRoundRect((Panel.W - width) / 2 - 5, Panel.H / 2 - 5, wOuter, 20, 10, 10);
        g.fillRoundRect((Panel.W - width) / 2, Panel.H / 2, currWidth, 10, 5, 5);

        if (elapsedMs > length + 250 && !hasWon) {
            panel.nextLevel(new HomeScreen(panel));
            hasWon = true;
        }
    }

    @Override
    protected void checkWin() {
    }

    @Override
    public void move() {
    }

    @Override
    void resetLevel() {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
