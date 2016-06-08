package voidream.vcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SetTimer extends Activity {

    TextView output_number;
    TextView output_name;
    TextView output_status;
    TextView output_position;
    TextView output_power;
    RadioGroup timer_action;
    Button timer_pick_time;
    Button timer_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);

        output_number = (TextView)findViewById(R.id.textview_output_number);
        output_name = (TextView)findViewById(R.id.textview_timer_output_name);
        output_status = (TextView)findViewById(R.id.textview_timer_output_status);
        output_position = (TextView)findViewById(R.id.textview_timer_output_position);
        output_power = (TextView)findViewById(R.id.textview_timer_output_power);
        timer_action = (RadioGroup)findViewById(R.id.radiobutton_timer_action);
        timer_pick_time = (Button)findViewById(R.id.button_timer_pick_time);
        timer_set = (Button)findViewById(R.id.button_timer_set_timer);

        timer_action.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                boolean checked = ((RadioButton) group.findViewById(checkedId)).isChecked();
                switch(checkedId) {
                    case R.id.radiobutton_timer_action_on:
                        if (checked)
                            Toast.makeText(getApplicationContext(), "Action ON", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radiobutton_timer_action_off:
                        if (checked)
                            Toast.makeText(getApplicationContext(), "Action OFF", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        timer_pick_time.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Set Time", Toast.LENGTH_SHORT).show();

            }
        });

        timer_set.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Timer set", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }
}
