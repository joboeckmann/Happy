package example.happy;

import java.util.ArrayList;

/**
 * This object is incredible useful. When a player picks a stack, it stores info on
 * almost everything which makes it much more accessible
 * Created by Josephine on 3/13/2016.
 */
public class Move {

    public int pos;//the selected position in the stack, first or second, etc
    public int id;// each stack has an id (1-4)
    public boolean inUse;//whether it has already been selected
    public MyAdapter adapter;//which adapter
    public int num;//the actual number selected
    public ArrayList<Number> stack;//the stack selected
    //Dummy values are declared when it is created
    public Move(){
        id=-1;
        pos=-1;
        inUse=false;
        num=0;
    }
}
