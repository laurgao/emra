/* This class is an animated fire sprite with custom drawn graphics.
 * It is used in Level14 to show money burning.
 */

import java.awt.*;
import java.awt.geom.*;

public class FireSprite {
    int x;
    int y;

    private Panel panel;
    private Toolkit t = Toolkit.getDefaultToolkit();
    private Image[] images;

    private final int N_FRAMES = 9; // number of frames in the animation
    private final long START_TIME;

    // Instantiates a new fire sprite with x and y coordinates.
    public FireSprite(int x, int y, Panel panel) {
        this.x = x;
        this.y = y;
        this.panel = panel;
        START_TIME = System.currentTimeMillis();

        // Initialize all frames of the animation by getting their frames from the
        // image files
        images = new Image[N_FRAMES];
        for (int i = 0; i < N_FRAMES; i++) {
            images[i] = t.getImage("images/fire/fire000" + (i + 1) + ".png");
        }
    }

    // Draws the fire sprite to the screen
    public void draw(Graphics2D g) {
        draw(g, 0.0);

    }

    // Draws the fire sprite to the screen
    // Overload variant that allows rotation of the sprite
    public void draw(Graphics2D g, double theta) {
        final int FPS = 10; // frames per second of the animation

        long elapsedMs = System.currentTimeMillis() - START_TIME;
        int frame = (int) (elapsedMs / (1000 / FPS)) % N_FRAMES;
        // g.drawImage(images[frame], x, y, panel);
        Image currentFrame = images[frame];

        // width and height of the images.
        final int W = 50;
        final int H = 90;

        // Create a transform that will rotate the image.
        AffineTransform tx = AffineTransform.getRotateInstance(theta, x, y);
        if (theta != 0.0)
            tx.translate(-W, -1.75 * H);
        else
            tx.translate(0, -0.75 * H);

        // Set our Graphics2D object to the transform
        g.setTransform(tx);
        // Draw our image like normal
        g.drawImage(currentFrame, x, y, panel);

        // Reset the transform so we can draw the next image
        g.setTransform(new AffineTransform());
    }

}
