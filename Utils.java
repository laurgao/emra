/* This class contains utility functions shared across multiple classes.
 */

import java.util.ArrayList;

public class Utils {

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
}
