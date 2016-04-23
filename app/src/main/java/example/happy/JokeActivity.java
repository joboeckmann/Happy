package example.happy;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;


public class JokeActivity extends Activity {
    private String line1;
    private String line2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);


        setLines();
        TextView t=(TextView)findViewById(R.id.textView);
        t.setText(line1);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                    TextView t2 = (TextView) findViewById(R.id.textView2);
                                    t2.setText(line2);
                                }
                         },1500);
        View.OnClickListener butt_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JokeActivity.this, MainActivity.class);
                startActivity(intent);
            }


        };
        Button butt=(Button)findViewById(R.id.button);
        butt.setOnClickListener(butt_listener);

    }

private void setLines(){
    Random r=new Random();
    int index=r.nextInt(10)+1;//REMEMBER TO UPDATE THIS FOR EVERY LINE ADDED
    String i=""+index;
    line1=i;
    boolean found=false;

    try{
        InputStream inputStream = getResources().openRawResource(
                getResources().getIdentifier("jokefile",
                        "raw", getPackageName()));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        while (!found&&in.ready()) {
            String line = in.readLine();
            String[] parts=line.split("/");
            //Log.i("WhatZeHell", parts[2]);


            if (parts[0].equals(i)){
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

}
