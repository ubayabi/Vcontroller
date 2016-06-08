package voidream.vcontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class SettingOutput extends Activity {

    ListView setting_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_output);

        AdapterSettingOutput adapterSettingOutput = new AdapterSettingOutput(this);
        setting_output = (ListView)findViewById(R.id.listview_setting_output);
        setting_output.setAdapter(adapterSettingOutput);

        Button add_new = (Button)findViewById(R.id.button_setting_output_add);
        assert add_new != null;
        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setting_output_form = new Intent(SettingOutput.this, SettingOutputForm.class);
                startActivity(setting_output_form);
            }
        });
    }
}
