package example.happy;

import android.app.Activity;
;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;




public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        View.OnClickListener rb_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Handler handler = new Handler();
               TextView text=(TextView)findViewById(R.id.textView);
                if (v.getId() == R.id.radioButton) {
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


        RadioButton rb1=(RadioButton) findViewById(R.id.radioButton);
        rb1.setOnClickListener(rb_listener);

        RadioButton rb2=(RadioButton) findViewById(R.id.radioButton2);
        rb2.setOnClickListener(rb_listener);

    }


}
