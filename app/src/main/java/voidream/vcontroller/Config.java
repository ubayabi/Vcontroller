package voidream.vcontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;

public class Config extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        final SQLiteAdapter sqLiteAdapter = new SQLiteAdapter(this);

        RadioGroup tcp_or_mqtt = (RadioGroup)findViewById(R.id.radiogroup_config);
        final LinearLayout config_tcp = (LinearLayout) findViewById(R.id.linearlayout_config_tcp);
        final LinearLayout config_mqtt = (LinearLayout) findViewById(R.id.linearlayout_config_mqtt);
        final EditText broker_url = (EditText)findViewById(R.id.edittext_config_mqtt_broker_url);
        final EditText port = (EditText)findViewById(R.id.edittext_config_mqtt_port);
        final EditText username = (EditText)findViewById(R.id.edittext_config_mqtt_username);
        final EditText password = (EditText)findViewById(R.id.edittext_config_mqtt_password);
        final EditText topic = (EditText)findViewById(R.id.edittext_config_mqtt_topic);
        final EditText ip_domain = (EditText)findViewById(R.id.edittext_config_tcp_ip_or_domain_name);
        final EditText port_tcp = (EditText)findViewById(R.id.edittext_config_tcp_port);

        if (!ArrayUtils.isEmpty(sqLiteAdapter.getMqttSetting())){
            String[] set_mqtt = sqLiteAdapter.getMqttSetting();
            String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            String deviceId = Md5.md5(android_id).toUpperCase();
            deviceId = deviceId.substring(deviceId.length() - 25);
            if (set_mqtt.length < 5 ){
                broker_url.setText(set_mqtt[0]);
                port.setText(set_mqtt[1]);
                topic.setText(set_mqtt[2]);
            }else {
                broker_url.setText(set_mqtt[0]);
                port.setText(set_mqtt[1]);
                username.setText(set_mqtt[4]);
                password.setText(set_mqtt[5]);
                topic.setText(set_mqtt[2]);
            }
        }else {
            broker_url.setText(R.string.default_mqtt_broker);
            port.setText(R.string.default_mqtt_port);
        }

        if (ArrayUtils.isEmpty(sqLiteAdapter.getTcpSetting())){
            String[] set_tcp = sqLiteAdapter.getTcpSetting();
            ip_domain.setText(set_tcp[0]);
            port_tcp.setText(set_tcp[1]);
        }

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

        Button ok = (Button)findViewById(R.id.button_config_set);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (config_mqtt.getVisibility() == View.VISIBLE){
                    sqLiteAdapter.deleteMqttSetting();
                    String url = broker_url.getText().toString();
                    String port_ = port.getText().toString();
                    String username_ = username.getText().toString();
                    String password_ = password.getText().toString();
                    String topic_ = topic.getText().toString();
                    if (!StringUtils.isBlank(url) & !StringUtils.isBlank(port_) & !StringUtils.isBlank(topic_)) {
                        sqLiteAdapter.addMqttSetting(url, port_, username_, password_, topic_, "true");
                        restart();
                    }else {
                        if (StringUtils.isBlank(url)) {
                            broker_url.setError("must be filled in");
                        }
                        if (StringUtils.isBlank(port_)) {
                            port.setError("must be filled in");
                        }
                        if (StringUtils.isBlank(topic_)) {
                            topic.setError("must be filled in");
                        }
                    }
                }/*
                else {
                    sqLiteAdapter.deleteTcpSetting();
                    String ip = ip_domain.getText().toString();
                    String port_tcp_ = port_tcp.getText().toString();
                    sqLiteAdapter.addTcpSetting(ip, port_tcp_, "true");
                    if (sqLiteAdapter.getMqttSetting().length > 0){
                        String[] set_mqtt = sqLiteAdapter.getMqttSetting();
                        sqLiteAdapter.addMqttSettingStatus(set_mqtt[0], "false");
                    }
                    restart();
                }
                */
            }
        });

    }

    private void restart(){
        finish();
        Intent config = new Intent(Config.this, Config.class);
        startActivity(config);
    }
}
