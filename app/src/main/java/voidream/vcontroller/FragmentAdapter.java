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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class FragmentAdapter extends Fragment {

	private static final String ARG_POSITION = "position";

	private int position;

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

		position = getArguments().getInt(ARG_POSITION);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view;

		SQLiteAdapter sqLiteAdapter = new SQLiteAdapter(getActivity());
		//Controller
		if(position==0){

			view=inflater.inflate(R.layout.controller,container,false);
			AdapterController adapterController = new AdapterController(getContext());

			ListView controller = (ListView)view.findViewById(R.id.listController);
			//final int[] idimage = {R.drawable.out_1on, R.drawable.out_1, R.drawable.out_1, R.drawable.out_1,
			//		R.drawable.out_1on,R.drawable.out_1on,R.drawable.out_1on};
			//String[] outputname = {"Lampu Kuning", "Lampu LED", "Lampu Belajar", "Test", "Test","Test","test"};
			//String[] position = {"Ruang Tamu", "Dapur", "Kamar", "Test", "Test", "Test", "Test",};
			//String[] power = {"10", "20", "5", "10","1","2","3"};
			//String[] status = {"ON", "OFF", "Waiting Response", "OFF", "ON", "ON", "ON"};

			if (sqLiteAdapter.getController() != null) {
                String[][] data_controller = sqLiteAdapter.getController();
                int[] id_image = new int[data_controller[4].length];
                for (int a=0;a<data_controller[4].length;a++){
                    id_image[a] = Integer.valueOf(data_controller[4][a]);
                }
                adapterController.updateData
                        (id_image, data_controller[0], data_controller[1]
                                , data_controller[2], data_controller[3]);
				controller.setAdapter(adapterController);
			}
			return  view;
		}

		//Log
		if(position==1){

			view=inflater.inflate(R.layout.log,container,false);

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
			return  view;
		}

		//Customize
		if(position==2){

			view=inflater.inflate(R.layout.customize,container,false);

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

			return  view;}

		return  null;

	}

}