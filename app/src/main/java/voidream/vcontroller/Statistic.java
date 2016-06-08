package voidream.vcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class Statistic extends Activity {

    ListView statistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        AdapterStatistic adapterStatistic = new AdapterStatistic(this);
        statistic = (ListView)findViewById(R.id.listview_statistic_output);
        statistic.setAdapter(adapterStatistic);

    }
}
