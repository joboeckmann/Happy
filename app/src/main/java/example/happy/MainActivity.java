package example.happy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;


public class MainActivity extends Activity {



    private example.happy.RView[] rViews;
    private Game g;
    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //this is where it all begins. Everything. All the poop
        g=new Game();
        //the r stands for recyclerview. (or maybe Rhonda its a secret)
        rViews= new RView[]{new RView((RecyclerView) findViewById(R.id.my_recycler_view), new MyAdapter(g.stack1, 1), new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)),
                new RView((RecyclerView) findViewById(R.id.my_recycler_view2), new MyAdapter(g.stack2, 2), new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)),
                new RView((RecyclerView) findViewById(R.id.my_recycler_view3), new MyAdapter(g.stack3, 3), new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)),
                new RView((RecyclerView) findViewById(R.id.my_recycler_view4), new MyAdapter(g.stack4, 4), new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))};

        for (int i=0;i<4;i++){//this does the stuff
            rViews[i].view.setLayoutManager(rViews[i].mLayoutManager);
            rViews[i].view.setAdapter(rViews[i].adapter);
        }




        button = (Button) findViewById(R.id.butt2);
        button.setText("Deck (48)");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (g.deck.size() > 0){
                    g.doDeckStuff(rViews[0].adapter, rViews[1].adapter, rViews[2].adapter, rViews[3].adapter);
                    //dirty dirty deck stuff (aka shuffle)
                button.setText("Deck (" + g.deck.size() + ")");
            }
            }

        });
       Button button2 = (Button) findViewById(R.id.butt3);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
                Intent intent = new Intent(MainActivity.this, JokeActivity.class);
                startActivity(intent);
            }

        });
        Button button3 = (Button) findViewById(R.id.butt4);//so many butts
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int r=g.undoThatSheet();
                if (r==2){
                    g.undoThatSheet();
                }
                else if (r==4){
                    g.undoThatSheet();
                    g.undoThatSheet();
                    g.undoThatSheet();
                }
            }

        });


    }
    @Override
    protected void onResume() {//should more things be under onResume? *shrug
        super.onResume();


       for ( int index=0;index<4;index++){
          final int i=index;//is this a sketchy line of code or what
           rViews[index].adapter.setOnItemClickListener(new MyAdapter.ClickListener() {
               @Override
               public void onItemClick(int position, View v) {
                      Boolean  move = g.makeMove(position, i + 1, rViews[i].adapter);
                   if (!move){
                       g.PleaseJustMakeItBig(position);
                   }
               }
           });

           }
       }

}

