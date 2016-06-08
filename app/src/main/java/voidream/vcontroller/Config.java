package voidream.vcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Config extends Activity {

    RadioGroup tcp_or_mqtt;
    LinearLayout config_tcp;
    LinearLayout config_mqtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        tcp_or_mqtt = (RadioGroup)findViewById(R.id.radiogroup_config);
        config_tcp = (LinearLayout) findViewById(R.id.linearlayout_config_tcp);
        config_mqtt = (LinearLayout) findViewById(R.id.linearlayout_config_mqtt);

        tcp_or_mqtt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                boolean checked = ((RadioButton) group.findViewById(checkedId)).isChecked();
                switch(checkedId) {
                    case R.id.radiobutton_config_tcp:
                        if (checked)
                            config_tcp.setVisibility(View.VISIBLE);
                            config_mqtt.setVisibility(View.GONE);
                        break;
                    case R.id.radiobutton_config_mqtt:
                        if (checked)
                            config_tcp.setVisibility(View.GONE);
                            config_mqtt.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

    }
}
