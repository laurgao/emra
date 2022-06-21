
/* Panel class acts as the main "game loop" - continuously runs the game and calls whatever needs to be called

Child of JPanel because JPanel contains methods for drawing to the screen

Implements KeyListener interface to listen for keyboard input

Implements Runnable interface to use "threading" - let the game do two things at once

*/
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class Panel extends JPanel implements Runnable, KeyListener, MouseListener {

    // dimensions of window
    public static final int W = 1120; // width of window
    public static final int H = 630; // height of window

    public AudioPlayer currentSound;
    public static boolean firstTimeSound;

    private Level currentScreen; // current screen being displayed
    private Level nextLevel;

    private Thread gameThread;
    private float opacity; // sign dictates whether alpha is going down or up.

    public static boolean isMuted;

    private Image muted;
    private Image unmuted;
    private Image replay;
    Toolkit t = Toolkit.getDefaultToolkit();

    // Initializes screen, and creates a new instance of the loading screen to be displayed. Basic game functions also initialized 
    public Panel() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        playSound("background", true);
        opacity = 1.0f; // start with full opacity.

        isMuted = false;
        firstTimeSound = true;

        // Initializes images by accessing files
        muted = t.getImage("images/muted.png");
        unmuted = t.getImage("images/unmuted.png");
        replay = t.getImage("images/replay.png");

        currentScreen = new LoadingScreen(this);
        nextLevel = currentScreen;

        this.setPreferredSize(new Dimension(W, H));
        this.setFocusable(true); // make everything in this class appear on the screen
        this.addKeyListener(this); // start listening for keyboard input
        this.addMouseListener(this);

        // make this class run at the same time as other classes (without this each
        // class would "pause" while another class runs). By using threading we can
        // remove lag, and also allows us to do features like display timers in real
        // time!
        gameThread = new Thread(this);
        gameThread.start();
    }

    // call this method to switch to the next level.
    // it fades out the current level and fades in the next one.
    public void nextLevel(Level level) {
        opacity = -1.0f;
        nextLevel = level;
    }

    // paint is a method in java.awt library that we are overriding.
    // It is called automatically in the background in order to update what
    // appears in the window.
    public void paint(Graphics g) {
        g.clearRect(0, 0, W, H); // clear the screen
        // Create a graphics 2d object to set the opacity of the entire screen.
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.abs(opacity)));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // use double buffering - draw images OFF the screen, then move the image on
        // screen
        Image image = createImage(W, H); // draw off screen
        Graphics graphics = image.getGraphics();
        draw(graphics);// update the positions of everything on the screen
        g2d.drawImage(image, 0, 0, this); // move the image on the screen

        // When done drawing, increment the opacity for the next call of this method.
        float increment = 0.01f; // adjust this to change the speed of the fade in/out
        if (opacity < 1.0f) {
            opacity = Math.min(opacity + increment, 1.0f);
            repaint();
        }
        if (opacity >= 0.0f && currentScreen != nextLevel) {
            currentScreen = nextLevel;
        }
    }

    // call the draw methods in each class to update positions as things move
    public void draw(Graphics g) {

        // draw the background
        Color bgColor = new Color(250, 250, 249);
        g.setColor(bgColor);
        g.fillRect(0, 0, W, H);

        currentScreen.draw(g);

        // Draw menu on top of the level's graphics

        // Draw the mute button
        g.setColor(CustomColor.PINK);
        g.fillRect(1000, 0, 80, 80);

        // Draw the mute image
        if(LoadingScreen.loadingComplete) {
            if (isMuted) {
                g.drawImage(muted, 1015, 15, this);
            } else if (!isMuted) {
                g.drawImage(unmuted, 1015, 15, this);
            }
        }

        if(HomeScreen.completed) {
            // Draw the replay button
            g.fillRect(900, 0, 80, 80);
            g.drawImage(replay, 913, 12, this);
        }
    }

    // call the move methods in other classes to update positions
    // this method is constantly called from run(). By doing this, movements appear
    // fluid and natural.
    public void move() {
        currentScreen.move();
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

    // Creates a new instance of an Audioplayer and plays sound file
    public void playSound(String audioFile, boolean continuous)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioPlayer ac = new AudioPlayer();
        currentSound = ac;
        ac.playSound(audioFile, continuous);
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

    // If mouse clicks the sound button, it toggles the mute function. If on
    // HomeScreen and clicks start, will pass MouseEvent to HomeScreen.
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        if (mouseY < 80 && mouseY > 0) {
            if (mouseX < 1080 && mouseX > 1000) {
                // Mouse clicked the mute button
                if (isMuted) {
                    try {
                        isMuted = false;
                        playSound("background", true);
                    } catch (UnsupportedAudioFileException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (LineUnavailableException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else if (!isMuted) {
                    isMuted = true;
                    currentSound.mute();
                }
            } else if (mouseX < 980 && mouseX > 900) {
                // Mouse is in the replay button
                currentScreen.resetLevel();
            }
        }
        currentScreen.mouseClicked(e);
    }

    // Empty method
    @Override
    public void mousePressed(MouseEvent e) {
    }

    // Empty method
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    // Empty method
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    // Empty method
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}