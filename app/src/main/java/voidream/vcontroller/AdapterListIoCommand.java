package voidream.vcontroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Abi Karami on 5/30/2016.
 */
public class AdapterListIoCommand extends BaseAdapter {

    String[] output_name={"Lampu Kuning", "Lampu LED", "Lampu Belajar", "Test", "Test", "Test", "Test"};

    Context context;

    AdapterListIoCommand(Context ini){
        context = ini;
    }
    @Override
    public int getCount() {
        return output_name.length;
    }

    @Override
    public Object getItem(int position) {
        return output_name[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_list_io_command, null);

        TextView list_output_name = (TextView)convertView.findViewById(R.id.textview_list_io_command_output_name);
        TextView list_output_number = (TextView)convertView.findViewById(R.id.textview_list_io_command_output_number);

        list_output_number.setText("0"+position);
        list_output_name.setText(output_name[position]);

        return convertView;
    }


}
