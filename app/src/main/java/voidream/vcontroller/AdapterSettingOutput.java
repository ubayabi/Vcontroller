package voidream.vcontroller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by RnD2 on 4/19/2016.
 */
public class AdapterSettingOutput extends BaseAdapter {

    SQLiteAdapter sqLiteAdapter;
    int[] id_image = null;// = {R.drawable.out_1};
    String[] output_name = null;//={"Lampu Kuning", "Lampu LED", "Lampu Belajar", "Test", "Test", "Test", "Test"};;
    String[] output_position = null;//={"Ruang Tamu", "Dapur", "Kamar", "Test", "Test", "Test", "Test"};;
    String[] output_power = null;//={"10", "20", "5", "10", "1", "2", "3"};
    String[] output_number = null;//String[] output_number;//={"01", "02", "03", "04","05","06","07"};

    Context context;

    AdapterSettingOutput(Context ini){
        context = ini;
        sqLiteAdapter = new SQLiteAdapter(context);
        if (sqLiteAdapter.getController() != null) {
            String[][] data_controller = sqLiteAdapter.getController();
            int[] id_image = new int[data_controller[4].length];
            for (int a=0;a<data_controller[4].length;a++){
                output_number[a] = String.format(Locale.getDefault(), "%02d", a);
                id_image[a] = Integer.valueOf(data_controller[4][a]);
            }
            this.id_image = id_image;
            output_name = data_controller[0];
            output_position = data_controller[1];
            output_power = data_controller[2];
        }
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

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_list_setting_output, null);
        Handler handler = new Handler(Looper.getMainLooper());

        final TextView setting_output_number = (TextView)convertView.findViewById(R.id.textview_setting_output_number);
        final ImageView setting_output_image = (ImageView)convertView.findViewById(R.id.imageview_setting_output_image);
        final TextView setting_output_name = (TextView)convertView.findViewById(R.id.textview_setting_output_name);
        final TextView setting_output_position = (TextView)convertView.findViewById(R.id.textview_setting_output_position);
        final TextView setting_output_power = (TextView)convertView.findViewById(R.id.textview_setting_output_power);
        Button setting_output_edit = (Button)convertView.findViewById(R.id.button_edit_output_edit);

        handler.post(new Runnable() {
            @Override
            public void run() {
                setting_output_number.setText(output_number[position]);
                setting_output_name.setText(output_name[position]);
                setting_output_position.setText(output_position[position]);
                setting_output_power.setText(output_power[position]);
                setting_output_image.setImageResource(id_image[0]);
                setting_output_image.setTag(id_image[position]);
            }
        });

        setting_output_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, SettingOutputForm.class);
                //intent.putExtra("",String.valueOf(position));
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
