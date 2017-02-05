package example.happy;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * So I threw in this Activity to keep my spirits up while I was learning how to do this.
 * Basically in between games this Activity will tell a silly joke and the highest score
 */
public class JokeActivity extends Activity {
    private String line1;//First line of the joke
    private String line2;//punch line (if it exist)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);


        setLines();
        TextView t1 = (TextView) findViewById(R.id.line1);
        t1.setText(line1);
        Handler handler = new Handler();
        //reveal the second line a few seconds later
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView t2 = (TextView) findViewById(R.id.line2);
                t2.setText(line2);
            }
        }, 1500);

        //set the highscore
        TextView t3 = (TextView) findViewById(R.id.highScore);
        String score = getScore();
        t3.setText("Least amount of cards:" +score);

        //start a new game when the button is clicked
        View.OnClickListener butt_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JokeActivity.this, MainActivity.class);
                startActivity(intent);
            }


        };
        Button butt = (Button) findViewById(R.id.button);
        butt.setOnClickListener(butt_listener);

    }
    /**
     * This reads in a random joke from the joke File and sets the line1 and line2
     */
private void setLines(){
    Random r=new Random();
    //pick a random joke
    int index=r.nextInt(9)+1;//REMEMBER TO UPDATE THIS FOR EVERY LINE ADDED
    //IMPORTANT to do: create a method that would format file automatically and update the number
    //of lines
    String jokeIndex=""+index;
    line1=jokeIndex;//if something goes wrong assigning it to line 1 for debugging
    boolean found=false;

    //For reference the file format is "[index]/[numberoflines(1or2)l/[first line of joke]/[second line]
    //1/2l/What does a vegan zombie eat?/Graaaaaaaaaaains
    //2/1l/A baby seal walked into a club.

    try{
        InputStream inputStream = getResources().openRawResource(
                getResources().getIdentifier("jokefile",
                        "raw", getPackageName()));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        while (!found&&in.ready()) {
            String line = in.readLine();
            String[] parts=line.split("/");

            if (parts[0].equals(jokeIndex)){
                found=true;
                if (parts[1].equals("1l")){
                    line1=parts[2];///need to update this for double digits, roger that I am on it
                }
                else if (parts[1].equals("2l")){
                    line1=parts[2];
                    line2=parts[3];
                }
                else{
                    line2="Problem here";
                }
            }
        }
        in.close();
    }catch(Exception e)
    {
        line1="Could not read instances: "+e;
    }
}

    /**
     * This reads in the high score
     */
    public String getScore() {
        String score="";
        try {
            FileInputStream fin = openFileInput("highScore.txt");
            int c;
            while( (c = fin.read()) != -1){
                score = score + Character.toString((char)c);
            }

            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }
        return score;
    }

}
