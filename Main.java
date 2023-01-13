/* Laura Gao and Emma Liu
 * June 21, 2022
 * This program is a platformer game called "The Hedonic Parodox",
 * modeled off of The Pretentious Game.
 * Created in Java Abstract Window Toolkit
 */

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main {
    public static void main(String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Frame();
    }
}
