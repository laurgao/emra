
/* Panel class acts as the main "game loop" - continuously runs the game and calls whatever needs to be called

Child of JPanel because JPanel contains methods for drawing to the screen

Implements KeyListener interface to listen for keyboard input

Implements Runnable interface to use "threading" - let the game do two things at once

*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Panel extends JPanel implements Runnable, KeyListener {

    // dimensions of window
    public static final int W = 1120; // width of window
    public static final int H = 630; // height of window

    private Level1 currentScreen; // current screen being displayed

    private Thread gameThread;

    public Panel() {
        currentScreen = new Level1();

        // add the MousePressed method from the MouseAdapter - by doing this we can
        // listen for mouse input.
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

            }
        });

        this.setPreferredSize(new Dimension(W, H));
        this.setFocusable(true); // make everything in this class appear on the screen
        this.addKeyListener(this); // start listening for keyboard input

        // make this class run at the same time as other classes (without this each
        // class would "pause" while another class runs). By using threading we can
        // remove lag, and also allows us to do features like display timers in real
        // time!
        gameThread = new Thread(this);
        gameThread.start();
    }

    // paint is a method in java.awt library that we are overriding.
    // It is called automatically in the background in order to update what
    // appears in the window.
    public void paint(Graphics g) {
        // use double buffering - draw images OFF the screen, then move the image on
        // screen
        Image image = createImage(W, H); // draw off screen
        Graphics graphics = image.getGraphics();
        draw(graphics);// update the positions of everything on the screen
        g.drawImage(image, 0, 0, this); // move the image on the screen

    }

    // call the draw methods in each class to update positions as things move
    public void draw(Graphics g) {
        // draw the background
        Color bgColor = new Color(250, 250, 249);
        g.setColor(bgColor);
        g.fillRect(0, 0, W, H);

        currentScreen.draw(g);

    }

    // call the move methods in other classes to update positions
    // this method is constantly called from run(). By doing this, movements appear
    // fluid and natural.
    public void move() {

    }

    // run() method is what makes the game continue running without end. It calls
    // other methods to move objects and update the screen
    public void run() {
        // the CPU runs our game code too quickly - we need to slow it down! The
        // following lines of code "force" the computer to get stuck in a loop for short
        // intervals between calling other methods to update the screen.
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60;
        final double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long now;

        while (true) { // this is the infinite game loop
            now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            // only move objects around and update screen if enough time has passed
            if (delta >= 1) {
                move();
                repaint();
                delta--;
            }

        }
    }

    // if a key is pressed, we'll send it over to the player paddle object for
    // processing
    public void keyPressed(KeyEvent e) {
        currentScreen.keyPressed(e);
    }

    // if a key is released, we'll send it over to the player paddle object for
    // processing
    public void keyReleased(KeyEvent e) {
        currentScreen.keyReleased(e);
    }

    // left empty because we don't need it; must be here because it is required to
    // be overridded by the KeyListener interface
    public void keyTyped(KeyEvent e) {

    }
}