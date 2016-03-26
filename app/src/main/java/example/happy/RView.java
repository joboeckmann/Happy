package example.happy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;

/**
 * Created by Josephine on 3/20/2016.
 */
public class RView{
    public RecyclerView view;
    //public ArrayList<Integer> stack;
    public MyAdapter adapter;
    public RecyclerView.LayoutManager mLayoutManager;

    public RView(RecyclerView v, MyAdapter a, RecyclerView.LayoutManager l ){
        view=v;
        //stack=s;
        adapter=a;
        mLayoutManager=l;
    }
}
