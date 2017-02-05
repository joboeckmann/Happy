package example.happy;

import java.util.ArrayList;

/**
 * This in an object that is used for tracking moves so that they can be undone. They are stored in
 * a linked list in the game
 * Created by Josephine on 4/27/2016.
 */
public class Undo {
    public ArrayList<Number> from;//which stack it is moving from
    public ArrayList<Number> to;//which stack it is moving to
    public MyAdapter fromAdaper;//the adapter of the from stack
    public MyAdapter toAdapter;
    public int count;//how many numbers moved between the stacks
    public Undo next;//the next Undo object in the list
    public Boolean pair;//if it is part of a pair
    public Boolean deckDealed;//if it is part of a deck deal

    public Undo(ArrayList<Number> f, ArrayList<Number> t, MyAdapter faa, MyAdapter taa, int c, Boolean p, Boolean d){
        from=f;
        to=t;
        fromAdaper =faa;
        toAdapter =taa;
        count=c;
        pair=p;
        deckDealed =d;
    }
}
