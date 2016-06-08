package voidream.vcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class IoCommand extends Activity {

    ListView list_io_command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io_command);

        AdapterListIoCommand adapterListIoCommand = new AdapterListIoCommand(this);
        list_io_command = (ListView)findViewById(R.id.listview_io_command_list_output);
        list_io_command.setAdapter(adapterListIoCommand);

    }
}
