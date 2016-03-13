package example.happy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;
    private RecyclerView.LayoutManager mLayoutManager3;
    private RecyclerView.LayoutManager mLayoutManager4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

            String[] myDataset={"fmsbdfmsbfmsfdfs", " dsfbsdfbjsgfjsgfjsgfjsgfjs ","3sbdfmsbfmsmm"};

            mAdapter = new MyAdapter(myDataset);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView2.setAdapter(mAdapter);
            mRecyclerView3.setAdapter(mAdapter);
            mRecyclerView4.setAdapter(mAdapter);

        }


    }


