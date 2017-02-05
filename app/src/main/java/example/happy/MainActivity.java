package example.happy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends Activity {



    private example.happy.RView[] rViews;
    private Game g;
    private Button deck;

    /**
     * So we create all the important GUI components to interact with the user
     * including the game, the views, and adapters
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //This is where it all begins. Everything.
        g=new Game();

        //The r stands for recyclerview. It creates an array of the four recyclerViews for the four
        //stacks
        rViews= new RView[]
                {new RView((RecyclerView) findViewById(R.id.my_recycler_view), new MyAdapter(g.stack1, 1), new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)),
                new RView((RecyclerView) findViewById(R.id.my_recycler_view2), new MyAdapter(g.stack2, 2), new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)),
                new RView((RecyclerView) findViewById(R.id.my_recycler_view3), new MyAdapter(g.stack3, 3), new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)),
                new RView((RecyclerView) findViewById(R.id.my_recycler_view4), new MyAdapter(g.stack4, 4), new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))
                };

        //After the views are created, we set the layout manager and the adapter
        for (int i=0;i<4;i++){
            rViews[i].view.setLayoutManager(rViews[i].mLayoutManager);
            rViews[i].view.setAdapter(rViews[i].adapter);
        }


        //Next we create a button to deal "numbers" out from the deck to each of the stacks
        deck = (Button) findViewById(R.id.deal);
        //The number after "Deck" represents the numbers remaining
        deck.setText("Deck (48)");
        deck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (g.deck.size() > 0) {
                    g.dealNumbers(rViews[0].adapter, rViews[1].adapter, rViews[2].adapter, rViews[3].adapter);
                    deck.setText("Deck (" + g.deck.size() + ")");
                }
            }

        });
        //Next we create a button to initiate a new game and store the high score if applicable
       Button newGame = (Button) findViewById(R.id.newGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //we save the high score in a file so it is persistent
                //This code is here and not in Game.java because openFileOpen/openFileInput needs a
                //context to work meaning it must be in an Activity
                //if the file doesn't exist create it
                if (!fileExistance("highScore.txt")) {
                    try {
                        FileOutputStream fOut = openFileOutput("highScore.txt", MODE_WORLD_READABLE);
                        String str = "" + g.getScore();
                        fOut.write(str.getBytes());
                        fOut.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //Get the previous high score
                else {
                    String score = "";
                    try {
                        FileInputStream fin = openFileInput("highScore.txt");
                        int c;
                        while ((c = fin.read()) != -1) {
                            score = score + Character.toString((char) c);
                        }

                        fin.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //If the high score is better than the previous score, overwrite the last score
                    if (g.getScore() < Integer.parseInt(score)) {
                        try {
                            FileOutputStream fOut = openFileOutput("highScore.txt", MODE_WORLD_READABLE);
                            String str = "" + g.getScore();
                            fOut.write(str.getBytes());
                            fOut.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //Start the joke activity
                Intent intent = new Intent(MainActivity.this, JokeActivity.class);
                startActivity(intent);
            }

        });
        Button undo = (Button) findViewById(R.id.undo);//so many butts
        undo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int r = g.undoThatSheet();

                //Sometimes it makes sense to undo 2(pair) or 4 moves(deck was dealt)
                if (r == 2) {
                    g.undoThatSheet();
                } else if (r == 4) {
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

        //The bulk of the game is controlled here. It sets the onClickListener for the recyclerViews
        //which then makes a move.
       for ( int index=0;index<4;index++){
          final int i=index;//is this a sketchy line of code or what, I don't like it be the
           //alternative is to copy and paste a bunch of code. Any ideas?
           rViews[index].adapter.setOnItemClickListener(new MyAdapter.ClickListener() {
               @Override
               public void onItemClick(int position, View v) {
                   //Calcuates if a move is valid and makes the move if thats the case
                      Boolean  move = g.makeMove(position, i + 1, rViews[i].adapter);
                   if (!move){
                       //When a number is selected it will get bigger to make it easy to remember
                       //which are selected
                       g.PleaseJustMakeItBig(position);
                   }
               }
           });

           }
       }
    /**
     * A simple method to check if a file exist
     */
    public boolean fileExistance(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
}

