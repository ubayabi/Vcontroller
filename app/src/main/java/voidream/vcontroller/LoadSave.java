package voidream.vcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoadSave extends Activity {

    Button save;
    Button load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_save);

        save = (Button)findViewById(R.id.button_save_setting);
        load = (Button)findViewById(R.id.button_load_setting);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Save Setting", Toast.LENGTH_SHORT).show();

            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Load Setting", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
