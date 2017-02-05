package example.happy;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This was tricky. This object stores the views for the numbers in the stacks. They all start out
 * small. However when a player selects one it gets bigger and then after a move is completed it
 * gets small again.  Sounds simple but it was unexpectedly difficult.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public ArrayList<Number> mDataset;
    private  ClickListener clickListener;
    private ViewHolder vh;
   public int stackID;


    public class ViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener{
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            v.setOnClickListener(this);//Create the onclick listener
            mTextView= (TextView) v.findViewById(R.id.numView);//set the small view

        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
            //get the position that was clicked

        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


 //The dataset is the the stack of Numbers
    public MyAdapter(ArrayList<Number> myDataset,int id) {
        mDataset = myDataset;
        stackID=id;
    }

    //determines which view (big or small)
    @Override
    public int getItemViewType(int position) {

       if(mDataset.get(position).num==0) return 0;//If the num is 0 that means it is invisible
       if (mDataset.get(position).big) return 2;//big

        return 1;//everything else should be small
    }

    // Determines which view to use
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        TextView v;

        //invisible;
        if (viewType==0){
            v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.invisible, parent, false);
        }
        //regular view
        else if (viewType==1){
             v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_text_view, parent, false);
       }
        //Big view
        else{
            v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.large_text, parent, false);
        }
         vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // set the text in the view
        holder.mTextView.setText(""+mDataset.get(position).num);



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}
