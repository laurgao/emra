//this class plays sound 
//uses LineListener to check when the audio file has started/stopped 

import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException; 

public class AudioPlayer implements LineListener{

    boolean playCompleted;
    
    //allows a sound file to be played
    //pre: string; name of sound file 
    //post: creates a file based on the inputed path, and transforms the file into a clip so that it can be played
    public void playSound(String af, boolean continuous) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        File audioFile = new File("sounds/" + af + ".au");

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip ac = (Clip) AudioSystem.getLine(info);
            ac.addLineListener(this);

            ac.open(audioStream); 
            if(continuous) {
                ac.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                ac.setMicrosecondPosition(0);
            }
            ac.start();

            if(!continuous){
                //Makes sure the sound doesn't stop immediately after it is played
                while (!playCompleted) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex){
                        ex.printStackTrace();
                    }
                }
                ac.close();
            }

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }

    @Override
    public void update(LineEvent event) {
        // TODO Auto-generated method stub
        
    }

    //is called twice when the audiofile is played and stopped to update the boolean (overrided from the original)
    //pre: an event that has occured (ie. file started, file stopped)
    //post: updates the playCompleted boolean 
    
    // @Override
    // public void update(LineEvent event) {
    //     LineEvent.Type type = event.getType();       
    //     if (type == LineEvent.Type.START) {
    //         playCompleted = false;
    //     } else if (type == LineEvent.Type.STOP) {
    //         playCompleted = true;
    //     }
    // }
}