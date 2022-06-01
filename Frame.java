
/* Frame class establishes the window (frame) for the game
It is a child of JFrame because JFrame manages frames
Runs the constructor in GamePanel class

*/
import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {

    public Frame() {
        Panel content = new Panel(); // run GamePanel constructor
        this.add(content);
        this.setTitle("Pong"); // set title for frame
        this.setResizable(false); // frame can't change size
        this.setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // X button will stop program execution
        this.pack(); // makes components fit in window - don't need to set JFrame size, as it will
                     // adjust accordingly
                     // this makes the frame the size of the gamepanel.
        this.setVisible(true); // makes window visible to user
        this.setLocationRelativeTo(null);// set window in middle of screen
    }

}