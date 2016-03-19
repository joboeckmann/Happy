package example.happy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {


    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private RecyclerView mRecyclerView3;
    private RecyclerView mRecyclerView4;
    //private RecyclerView.Adapter mAdapter;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;
    private RecyclerView.LayoutManager mLayoutManager3;
    private RecyclerView.LayoutManager mLayoutManager4;
    private  Number[] myDataset;
    private Number[] myDataset2;
    private Number[] myDataset3;
    private Number[] myDataset4;
    private MyAdapter mAdapter3;
    private MyAdapter mAdapter2;
    private MyAdapter mAdapter4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Game g=new Game();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView2 = (RecyclerView) findViewById(R.id.my_recycler_view2);
        mRecyclerView3 = (RecyclerView) findViewById(R.id.my_recycler_view3);
        mRecyclerView4 = (RecyclerView) findViewById(R.id.my_recycler_view4);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        mRecyclerView3.setLayoutManager(mLayoutManager3);
        mRecyclerView4.setLayoutManager(mLayoutManager4);

//        myDataset = new Number[]{new Number(1, 0), new Number(2, 1), new Number(3, 2), new Number(4, 3), new Number(5, 4)};
//        myDataset2 = new Number[]{new Number(5, 0), new Number(4, 1), new Number(3, 2), new Number(2, 3), new Number(1, 4)};
//        myDataset3 = new Number[]{new Number(1, 0), new Number(2, 1), new Number(3, 2), new Number(4, 3), new Number(5, 4)};
//        myDataset4 = new Number[]{new Number(5, 0), new Number(4, 1), new Number(3, 2), new Number(2, 3), new Number(1, 4)};
        mAdapter = new MyAdapter(g.stack1,1);
        mAdapter2 = new MyAdapter(g.stack2,2);
        mAdapter3 = new MyAdapter(g.stack3,3);
        mAdapter4 = new MyAdapter(g.stack4,4);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView2.setAdapter(mAdapter2);
        mRecyclerView3.setAdapter(mAdapter3);
        mRecyclerView4.setAdapter(mAdapter4);




    }
    @Override
    protected void onResume() {
        super.onResume();
        ((MyAdapter) mAdapter).setOnItemClickListener(new MyAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                for (int i = 0; i <= position; i++) {
                    v=mAdapter.vList.get(i);
                    ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                }


            }
        });
        ((MyAdapter) mAdapter2).setOnItemClickListener(new MyAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                for (int i = 0; i <= position; i++) {
                 v=mAdapter2.vList.get(i);
                 ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                  }


            }
        });
        ((MyAdapter) mAdapter3).setOnItemClickListener(new MyAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                for (int i = 0; i <= position; i++) {
                    v=mAdapter3.vList.get(i);
                    ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                }


            }
        });
        ((MyAdapter) mAdapter4).setOnItemClickListener(new MyAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                for (int i = 0; i <= position; i++) {
                    v=mAdapter4.vList.get(i);
                    ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                }


            }

        });
    }
}

