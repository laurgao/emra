/* This class contains utility functions shared across multiple classes.
 */

import java.util.ArrayList;
import java.awt.*;

public class Utils {

    // Returns a new arraylist that includes all elements from two arraylist
    // arguments.
    public static ArrayList<Block> extend(ArrayList<Block> list, ArrayList<Block> list2) {
        ArrayList<Block> newList = new ArrayList<Block>();
        for (Block b : list) {
            newList.add(b);
        }
        for (Block b : list2) {
            newList.add(b);
        }
        return newList;
    }

    // Helper method to draw a string with word wrapping.
    // Returns the y position of the bottom of the last line.
    public static int drawStringWrap(Graphics g, String str, FontMetrics metrics, int initialX, int initialY,
            int width) {
        final double lineHeightFactor = 0.9;
        String[] words = str.split(" ");
        int lineHeight = (int) (lineHeightFactor * metrics.getHeight());

        String currLine = "";
        int y = initialY;
        for (String word : words) {
            int wordWidth = metrics.stringWidth(word);
            // If adding this word would make the line overflow, draw the current line.
            if (wordWidth + metrics.stringWidth(currLine) > width) {
                g.drawString(currLine, initialX + (width - metrics.stringWidth(currLine)) / 2, y);
                currLine = "";
                y += lineHeight;
            }
            currLine += word + " ";
        }
        g.drawString(currLine, initialX + (width - metrics.stringWidth(currLine)) / 2, y);
        return y + lineHeight;
    }

    // Draw text on the center of the screen
    public static void drawStringCenter(Graphics g, String str, int yPos) {
        FontMetrics m = g.getFontMetrics();
        g.drawString(str, Panel.W / 2 - m.stringWidth(str) / 2, yPos);
    }
}
