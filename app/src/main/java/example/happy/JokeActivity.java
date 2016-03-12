package example.happy;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    int index=r.nextInt(9)+1;//REMEMBER TO UPDATE THIS FOR EVERY LINE ADDED
    String i=" "+index+" ";
    line1=i;
    boolean found=false;

    try{
        InputStream inputStream = getResources().openRawResource(
                getResources().getIdentifier("jokefile",
                        "raw", getPackageName()));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        while (!found&&in.ready()) {
            String line = in.readLine();

            if (line.contains(i)){
                found=true;
                if (line.contains("1l")){
                    line1=line.substring(6);
                }
                else if (line.contains("2l")){
                    String[] lines=line.split("/");
                    line1=lines[0].substring(6);
                    line2=lines[1];
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
