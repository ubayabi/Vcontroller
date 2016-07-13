package voidream.vcontroller;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Statistic extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        final AdapterStatistic adapterStatistic = new AdapterStatistic(this);
        final ListView statistic = (ListView)findViewById(R.id.listview_statistic_output);

        final GraphView graphView = (GraphView)findViewById(R.id.graph);
        graphView.setBackgroundColor(Color.WHITE);
        final LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statistic.setAdapter(adapterStatistic);
                graphView.addSeries(lineGraphSeries);
            }
        });

    }
}
