package example.happy;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.StackView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StackView stk = (StackView) this.findViewById(R.id.stackView);
        StackView stk2=(StackView) this.findViewById(R.id.stackView2);
        StackView stk3=(StackView) this.findViewById(R.id.stackView3);
        StackView stk4=(StackView) this.findViewById(R.id.stackView4);

        ArrayList<String> items = new ArrayList<String>();
        items.add("1");
        items.add("2");
        items.add("3");

        stk.setAdapter(new StackAdapter(this, R.layout.stackitem, items));
        stk2.setAdapter(new StackAdapter(this, R.layout.stackitem, items));
        stk3.setAdapter(new StackAdapter(this, R.layout.stackitem, items));
        stk4.setAdapter(new StackAdapter(this, R.layout.stackitem, items));

    }





}
