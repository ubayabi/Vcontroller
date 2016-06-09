package voidream.vcontroller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by RnD2 on 4/19/2016.
 */
public class AdapterController extends BaseAdapter {

    int[] Id_outputimage;
    String[] outputName;
    String[] position;
    String[] power;
    String[] status;
    String[] buttonState;

    Context context;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_list_controller, null);

        TextView output_number = (TextView)convertView.findViewById(R.id.textview_output_number);
        CheckBox output_image = (CheckBox) convertView.findViewById(R.id.imageview_controller_output_image);
        TextView output_name = (TextView)convertView.findViewById(R.id.textview_controller_output_name);
        TextView output_position = (TextView)convertView.findViewById(R.id.textview_controller_output_position);
        TextView output_power = (TextView)convertView.findViewById(R.id.textview_controller_output_power);
        TextView output_status = (TextView)convertView.findViewById(R.id.textview_controller_output_status);
        ImageButton button_push = (ImageButton)convertView.findViewById(R.id.imagebutton_controller_push_button);
        ImageButton button_timer = (ImageButton)convertView.findViewById(R.id.imagebutton_controller_timer);

        output_number.setText(String.format("%0" + (2 - String.valueOf(position+1).length()) + "d%s", 0, String.valueOf(position+1)));
        output_image.setButtonDrawable(Id_outputimage[position]);
        output_name.setText(outputName[position]);


        output_position.setText(this.position[position]);
        output_power.setText(this.power[position]);
        output_status.setText(this.status[position]);

        button_push.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

            }
        });

        button_timer.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent= new Intent(context, SetTimer.class);
                intent.putExtra("",String.valueOf(position));
                context.startActivity(intent);

            }
        });

        return convertView;
    }
}
