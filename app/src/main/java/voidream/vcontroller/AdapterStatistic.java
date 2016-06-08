package voidream.vcontroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Abi Karami on 5/17/2016.
 */
public class AdapterStatistic extends BaseAdapter {

    int[] id_image = {R.drawable.out_1};
    String[] output_name={"Lampu Kuning", "Lampu LED", "Lampu Belajar", "Test", "Test", "Test", "Test"};;
    String[] output_position={"Ruang Tamu", "Dapur", "Kamar", "Test", "Test", "Test", "Test"};;
    String[] output_power={"10", "20", "5", "10", "1", "2", "3"};
    String[] output_number={"01", "02", "03", "04","05","06","07"};

    Context context;

    AdapterStatistic(Context ini){
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
        convertView = inflater.inflate(R.layout.custom_list_statistic, null);

        TextView statistic_output_number = (TextView)convertView.findViewById(R.id.textview_statistic_output_number);
        ImageView statistic_output_image = (ImageView)convertView.findViewById(R.id.imageview_statistic_output_image);
        TextView statistic_output_name = (TextView)convertView.findViewById(R.id.textview_statistic_output_name);
        TextView statistic_output_position = (TextView)convertView.findViewById(R.id.textview_statistic_output_position);
        TextView statistic_output_power = (TextView)convertView.findViewById(R.id.textview_statistic_output_power);
        TextView statistic_day = (TextView)convertView.findViewById(R.id.textview_statistic_day);
        TextView statistic_week = (TextView)convertView.findViewById(R.id.textview_statistic_week);
        TextView statistic_month = (TextView)convertView.findViewById(R.id.textview_statistic_month);
        TextView statistic_average = (TextView)convertView.findViewById(R.id.textview_statistic_average);

        statistic_output_number.setText(output_number[position]);
        statistic_output_image.setImageResource(id_image[0]);
        statistic_output_name.setText(output_name[position]);
        statistic_output_position.setText(output_position[position]);
        statistic_output_power.setText(output_power[position]);
        statistic_day.setText("5h");
        statistic_week.setText("35h");
        statistic_month.setText("200h");
        statistic_average.setText("4,5h");

        return convertView;
    }

}
