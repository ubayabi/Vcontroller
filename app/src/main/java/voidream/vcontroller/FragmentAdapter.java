/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package voidream.vcontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FragmentAdapter extends Fragment {

	private static final String ARG_POSITION = "position";

	private int position;
    private static int[] id_image;
    private static String[][] data_controller;
    private SQLiteAdapter sqLiteAdapter;
    private AdapterController adapterController;
    private ListView controller;
    private Handler handler;

	public static FragmentAdapter newInstance(int position) {
		FragmentAdapter f = new FragmentAdapter();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        handler = new Handler(Looper.getMainLooper());
		position = getArguments().getInt(ARG_POSITION);
        sqLiteAdapter = new SQLiteAdapter(getActivity());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//Controller
		if(position==0){
            getActivity().registerReceiver(broadcastReceiver, new IntentFilter(MqttService.BROADCAST_ACTION));
			View view=inflater.inflate(R.layout.controller,container,false);
			adapterController = new AdapterController(getContext());

			controller = (ListView)view.findViewById(R.id.listController);
			//final int[] idimage = {R.drawable.out_1on, R.drawable.out_1, R.drawable.out_1, R.drawable.out_1,
			//		R.drawable.out_1on,R.drawable.out_1on,R.drawable.out_1on};
			//String[] outputname = {"Lampu Kuning", "Lampu LED", "Lampu Belajar", "Test", "Test","Test","test"};
			//String[] position = {"Ruang Tamu", "Dapur", "Kamar", "Test", "Test", "Test", "Test",};
			//String[] power = {"10", "20", "5", "10","1","2","3"};
			//String[] status = {"ON", "OFF", "Waiting Response", "OFF", "ON", "ON", "ON"};

			if (sqLiteAdapter.getController() != null) {
                data_controller = sqLiteAdapter.getController();
                id_image = new int[data_controller[4].length];
                for (int a=0;a<data_controller[4].length;a++){
                    id_image[a] = Integer.valueOf(data_controller[4][a]);
                }
                adapterController.updateData
                        (id_image, data_controller[0], data_controller[1]
                                , data_controller[2], data_controller[3]);
                if (PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getString("tcp_mqtt", "").equals("mqtt")){
                    startMqttService();
                    AdapterController.tcp_or_mqtt = true;
                }
                if (PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getString("tcp_mqtt", "").equals("tcp")){
                    connectTCP();
                    AdapterController.tcp_or_mqtt = false;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        controller.setAdapter(adapterController);
                    }
                });

			}

            return view;
		}

		//Log
		if(position==1){
			View view=inflater.inflate(R.layout.log,container,false);

            /*
			AdapterLog adapterLog = new AdapterLog(getContext());

			ListView loglist = (ListView)view.findViewById(R.id.loglist);
			int[] idimage = {R.drawable.out_log_1};
			String[] outputname = {"Lampu Kuning 1", "Lampu LED", "Lampu Belajar", "Test", "Lampu LED", "Lampu Belajar", "Test",
					 "Lampu LED", "Lampu Belajar", "Test"};
			String[] explanation = {"has turned on.", "has turned off.", "has turned on.", "has turned on.", "has turned off.", "has turned on.", "has turned on.",
					 "has turned off.", "has turned on.", "has turned on."};
			String[] timeago = {"52s", "10m", "1h", "1d","10m", "1h", "1d","10m", "1h", "1d"};
			String[] location = {"Ruang Tamu1", "Dapur", "Kamar", "Test", "Dapur", "Kamar", "Test", "Dapur", "Kamar", "Test"};
			String[] timestamp = {"2016-03-05 05:30am", "2016-03-05 05:03am", "2016-03-05 03:22am", "2016-04-05 09:54pm"
					, "2016-03-05 05:03am", "2016-03-05 03:22am", "2016-04-05 09:54pm", "2016-03-05 05:03am", "2016-03-05 03:22am", "2016-04-05 09:54pm"};
			String[] number = {"01", "02", "03", "04", "02", "03", "04", "02", "03", "04"};
			adapterLog.updateData(idimage, outputname, explanation, timeago, location, timestamp, number);
			loglist.setAdapter(adapterLog);
			*/
			return  view;
		}

		//Customize
		if(position==2){

			View view=inflater.inflate(R.layout.customize,container,false);

			RelativeLayout statistic = (RelativeLayout) view.findViewById(R.id.relativelayout_customize_statistic);
			RelativeLayout config = (RelativeLayout) view.findViewById(R.id.relativelayout_customize_config);
			RelativeLayout setting = (RelativeLayout) view.findViewById(R.id.relativelayout_customize_controller);
			RelativeLayout load_save = (RelativeLayout) view.findViewById(R.id.relativelayout_customize_loadsave);

			statistic.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent= new Intent(getContext(), Statistic.class);
					getContext().startActivity(intent);
				}
			});

			config.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent= new Intent(getContext(), Config.class);
					getContext().startActivity(intent);
				}
			});

			setting.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent= new Intent(getContext(), SettingOutput.class);
					getContext().startActivity(intent);
				}
			});

			load_save.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent= new Intent(getContext(),LoadSave.class);
					getContext().startActivity(intent);
				}
			});

			return  view;
        }

		return  null;

	}

    private void startMqttService(){
        Intent start_service = new Intent(getActivity(), MqttService.class);
        start_service.putExtra(getString(R.string.data_mqtt), sqLiteAdapter.getMqttSetting());
        getActivity().startService(start_service);
    }

    private void stopMqttService(){
        Intent start_service = new Intent(getActivity(), MqttService.class);
        getActivity().stopService(start_service);
    }

    private void connectTCP(){
        TCPClient.connect(sqLiteAdapter.getTcpSetting()[0], sqLiteAdapter.getTcpSetting()[1]);
    }

    private void disconnectTCP(){
        TCPClient.disconnect();
    }

    private void updateUI(Intent intent){
        if (intent != null) {
            int pos = Integer.valueOf(intent.getStringArrayExtra("update_controller")[1]);
            String status_ = intent.getStringArrayExtra("update_controller")[2];
            data_controller[3][pos] = status_;
            Runnable update = new Runnable() {
                @Override
                public void run() {
                    adapterController.updateData(id_image, data_controller[0], data_controller[1]
                            , data_controller[2], data_controller[3]);
                    controller.setAdapter(adapterController);
                }
            };
            handler.removeCallbacks(update);
            handler.post(update);
        }
    }

    public final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(SettingOutputForm.BROADCAST_ACTION));
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stopMqttService();
        disconnectTCP();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

}