package rxdx.minhaagenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rodrigo on 11/19/15.
 */
public class MyAdapter extends ArrayAdapter<User> {

    public MyAdapter(Context context, List<User> users) {
        super(context, R.layout.activity_main, R.id.name, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.my_row, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView email = (TextView) convertView.findViewById(R.id.email);

        User user = getItem(position);

        name.setText(user.getNome());
        email.setText(user.getEmail());

        return convertView;
    }
}
