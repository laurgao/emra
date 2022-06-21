/* This class is an animated fire sprite with custom drawn graphics.
 * It is used in Level14 to show money burning.
 */

import java.awt.*;

public class FireSprite {
    int x;
    int y;

    private Panel panel;
    private Toolkit t = Toolkit.getDefaultToolkit();
    private Image[] images;

    private final int nFrames = 9; // number of frames in the animation
    private final long startTime;

    // Instantiates a new fire sprite with x and y coordinates.
    public FireSprite(int x, int y, Panel panel) {
        this.x = x;
        this.y = y;
        this.panel = panel;
        startTime = System.currentTimeMillis();

        // Initialize all frames of the animation by getting their frames from the
        // image files
        images = new Image[nFrames];
        for (int i = 0; i < nFrames; i++) {
            images[i] = t.getImage("images/fire/fire000" + (i + 1) + ".png");
        }
    }

    // Draws the fire sprite to the screen
    public void draw(Graphics g) {
        final int fps = 10; // frames per second of the animation

        long elapsedMs = System.currentTimeMillis() - startTime;
        int frame = (int) (elapsedMs / (1000 / fps)) % nFrames;
        g.drawImage(images[frame], x, y, panel);
    }

}
