package example.happy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StackAdapter extends ArrayAdapter<String> {

    private ArrayList<String> items;
    private Context ctx;

    public StackAdapter(Context context, int textViewResourceId,
                        ArrayList<String> objects) {
        super(context, textViewResourceId, objects);

        this.items = objects;
        this.ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)
                    ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.stackitem, null);
        }

        String m = items.get(position);

        if (m != null) {
            TextView mv = (TextView) v.findViewById(R.id.textView1);
            TextView cv = (TextView) v.findViewById(R.id.textView4);



            if (mv != null) {
                mv.setText("here");
                cv.setText("there");

            }
        }
        return v;
    }
}
