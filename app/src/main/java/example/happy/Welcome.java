package example.happy;

import android.app.Activity;
;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;



/**
 * The simple Welcome activity that opens when the app is opened
 */
public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        View.OnClickListener rb_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Handler handler = new Handler();
                //A textview that ask how the person is doing
               TextView text=(TextView)findViewById(R.id.line1);
                if (v.getId() == R.id.radioButton) {
                    //If they are having a good day, set the good day response and then
                    //wait a few second and move to the game activity
                    text.setText(R.string.good_day_response);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Welcome.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }, 1000);
                }
                else  if (v.getId() == R.id.radioButton2) {
                    //If they are having a bad day, set the bad day response and then
                    //wait a few second and move to the joke activity to cheer them up
                    text.setText(R.string.bad_day_response);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Welcome.this, JokeActivity.class);
                            startActivity(intent);
                        }
                    }, 1000);
                }

            }
        };

        //Radio buttons to choose if the user is having a good or bad day
        RadioButton rb1=(RadioButton) findViewById(R.id.radioButton);
        rb1.setOnClickListener(rb_listener);

        RadioButton rb2=(RadioButton) findViewById(R.id.radioButton2);
        rb2.setOnClickListener(rb_listener);

    }


}
