package voidream.vcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SettingOutputForm extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_output_form);

        TextView ouput_number = (TextView)findViewById(R.id.textView_output_number_output_form);
        final ImageView image_set = (ImageView)findViewById(R.id.imageview_setting_output_image_selected);
        final EditText nama_ouput = (EditText) findViewById(R.id.editText_nama_output);
        final EditText posisi_output = (EditText) findViewById(R.id.editText_posisi_output);
        final EditText power_output = (EditText)findViewById(R.id.editText_power_output);
        final ListView list_ouput = (ListView)findViewById(R.id.listview_output_option);
        Button add = (Button)findViewById(R.id.button_add);

        final CustomListOutputOptions customListOutputOptions = new CustomListOutputOptions(this);
        list_ouput.setAdapter(customListOutputOptions);

        final SQLiteAdapter sqLiteAdapter = new SQLiteAdapter(this);

        list_ouput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                image_set.setImageResource(CustomListOutputOptions.id[position]);
                image_set.setId(CustomListOutputOptions.id[position]);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image_set != null & nama_ouput.getText()!=null & posisi_output.getText() != null
                        & power_output.getText() != null){
                    int id_image = image_set.getId();
                    String nama_output_ = nama_ouput.getText().toString();
                    String posisi_output_ = posisi_output.getText().toString();
                    String power_output_ = power_output.getText().toString();
                    sqLiteAdapter.AddController(nama_output_, posisi_output_, power_output_, id_image);
                }
            }
        });

    }
}
