package voidream.vcontroller;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import org.apache.commons.lang.ArrayUtils;

/**
 * Created by RnD2 on 4/19/2016.
 */
public class AdapterController extends BaseAdapter {

    private int[] Id_outputimage;
    private String[] outputName;
    private String[] position;
    private String[] power;
    public static String[] status;
    public static boolean tcp_or_mqtt;

    private CheckBox output_image;
    private TextView output_status;

    static Context context;
    private Handler handler;
    public AdapterController(Context ini){
        context = ini;
    }

    public void updateData(int[] id_outputimage, String[] outputname, String[] position, String[] power
            , String[] status){
        Id_outputimage = id_outputimage;
        outputName = outputname;
        this.position = position;
        this.power = power;
        this.status = status;
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

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        handler = new Handler(Looper.getMainLooper());
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_list_controller, null);

        TextView output_number = (TextView)convertView.findViewById(R.id.textview_output_number);
        output_image = (CheckBox) convertView.findViewById(R.id.imageview_controller_output_image);
        final TextView output_name = (TextView)convertView.findViewById(R.id.textview_controller_output_name);
        TextView output_position = (TextView)convertView.findViewById(R.id.textview_controller_output_position);
        TextView output_power = (TextView)convertView.findViewById(R.id.textview_controller_output_power);
        output_status = (TextView) convertView.findViewById(R.id.textview_controller_output_status);
        ImageButton button_push = (ImageButton)convertView.findViewById(R.id.imagebutton_controller_push_button);
        ImageButton button_timer = (ImageButton)convertView.findViewById(R.id.imagebutton_controller_timer);

        output_number.setText(String.format("%0" + (2 - String.valueOf(position).length()) + "d%s", 0, String.valueOf(position)));
        output_image.setButtonDrawable(Id_outputimage[position]);
        output_image.setClickable(false);
        output_image.setChecked(status[position].equals("on"));
        output_name.setText(outputName[position]);

        output_position.setText(this.position[position]);
        output_power.setText(power[position]);
        output_status.setText(status[position]);

        final MqttPublisher publisher = new MqttPublisher(context);
        button_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tcp_or_mqtt){
                    if (!output_image.isChecked()) {
                        publisher.publishMqttMessage(outputName[position] + "/" + position + "/on");
                    }else {
                        publisher.publishMqttMessage(outputName[position] + "/" + position + "/off");
                    }
                }else {
                    if (!output_image.isChecked()) {
                        TCPClient.sendData(outputName[position] + "/" + position + "/on");
                    }else {
                        TCPClient.sendData(outputName[position] + "/" + position + "/off");
                    }
                }
            }
        });

        button_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, SetTimer.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}
