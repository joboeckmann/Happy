package example.happy;

import java.util.ArrayList;

/**
 * Created by Josephine on 4/27/2016.
 */
public class Undo {
    public ArrayList<Number> from;
    public ArrayList<Number> to;
    public MyAdapter fa;
    public MyAdapter ta;
    public int count;
    public Undo next;
    public Boolean pair;
    public Boolean dirtyDeckStuff;

    public Undo(ArrayList<Number> f, ArrayList<Number> t, MyAdapter faa, MyAdapter taa, int c, Boolean p, Boolean d){
        from=f;
        to=t;
        fa=faa;
        ta=taa;
        count=c;
        pair=p;
        dirtyDeckStuff=d;
    }
}
