package voidream.vcontroller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by RnD2 on 4/19/2016.
 */
public class AdapterLog extends BaseAdapter {

    int[] Id_outputimage;
    String[] outputName;
    String[] expla;
    String[] ago;
    String[] loc;
    String[] stamp;
    String[] num;

    Context context;
    public AdapterLog(Context ini){
        context = ini;
    }

    public void updateData(int[] id_outputimage, String[] outputname, String[] explanation, String[] timeAgo
            , String[] location, String[] timeStamp, String[] number){
        Id_outputimage = id_outputimage;
        outputName = outputname;
        expla = explanation;
        ago = timeAgo;
        loc = location;
        stamp = timeStamp;
        num = number;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return outputName.length;
    }

    @Override
    public Object getItem(int position) {
        return outputName[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_list_log, null);

        TextView log_output = (TextView)convertView.findViewById(R.id.textview_log_output);
        ImageView log_image = (ImageView)convertView.findViewById(R.id.imageview_log_image);
        TextView log_explanation = (TextView)convertView.findViewById(R.id.textview_log_explanation);
        TextView log_time_ago = (TextView)convertView.findViewById(R.id.textview_log_time_ago);
        TextView log_location = (TextView)convertView.findViewById(R.id.textview_log_location);
        TextView log_time_stamp = (TextView)convertView.findViewById(R.id.textview_log_time_stamp);
        TextView log_number = (TextView)convertView.findViewById(R.id.textview_log_number);

        log_image.setImageResource(Id_outputimage[0]);
        log_output.setText(outputName[position]);
        log_explanation.setText(expla[position]);
        log_time_ago.setText(ago[position]);
        log_location.setText(loc[position]);
        log_time_stamp.setText(stamp[position]);
        log_number.setText(num[position]);

        return convertView;
    }
}
