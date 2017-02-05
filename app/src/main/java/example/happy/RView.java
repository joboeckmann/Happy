package example.happy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;

/**
 * This is just a clean way to keep track of the recyclerViews
 * Created by Josephine on 3/20/2016.
 */
public class RView{
    public RecyclerView view;
    public MyAdapter adapter;
    public RecyclerView.LayoutManager mLayoutManager;

    public RView(RecyclerView v, MyAdapter a, RecyclerView.LayoutManager l ){
        view=v;
        adapter=a;
        mLayoutManager=l;
    }
}
