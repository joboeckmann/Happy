package example.happy;

import java.util.ArrayList;

/**
 * Created by Josephine on 3/13/2016.
 */
public class Move {

    public int pos;
    public int id;
    public boolean inUse;
    public MyAdapter adapter;
    public int num;
    public ArrayList<Number> stack;
    public Move(){
        id=-1;
        pos=-1;
        inUse=false;
        num=0;
    }
}
