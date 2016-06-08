package voidream.vcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SettingOutputForm extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_output_form);

        TextView ouput_number = (TextView)findViewById(R.id.textView_output_number_output_form);
        ImageView image_set = (ImageView)findViewById(R.id.imageview_setting_output_image_selected);
        EditText nama_ouput = (EditText) findViewById(R.id.editText_nama_output);
        EditText posisi_output = (EditText) findViewById(R.id.editText_posisi_output);
        EditText power_output = (EditText)findViewById(R.id.editText_power_output);
        ScrollView image_list = (ScrollView)findViewById(R.id.scrollView_output_list);
        Button add = (Button)findViewById(R.id.button_add);

    }
}
