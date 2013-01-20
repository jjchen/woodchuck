package com.ptzlabs.wc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DynamicList extends ListActivity {

	class DownloadReadingsTask extends DownloadTask {

		@Override
		String updateString(String[] arg0) {
			String uri = "http://wc.ptzlabs.com/reading?";
			uri += arg0[0] + "=" + arg0[1];
			uri += "&" + arg0[2] + "=" + arg0[3];
			Log.d("update", "check2");

			return uri;
		}

		@Override
		protected void onPostExecute(HttpResponse result) {

			if (result.getEntity() == null) {
				return;
			}

			Log.d("update", result.toString());

			try {

				String jsonString = EntityUtils.toString(result.getEntity());
				Log.e("result", jsonString);

				JSONArray json_arr = new JSONArray(jsonString);
				int max = json_arr.length();

				for (int i = 0; i < max; i ++) {
					JSONObject curr = (JSONObject) json_arr.get(i);

					String s = (String) curr.get("name");
					//	Integer id = (Integer) curr.get("id");

					addRow(s, curr);
				}

			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("boo", "something went wrong");
				// Toast.makeText(getActivity(), "something went wrong", Toast.LENGTH_SHORT).show();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	//LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
	ArrayList<String> listItems = new ArrayList<String>();
	ArrayList<JSONObject> items = new ArrayList<JSONObject>();
	Map<Integer, String> allItems = new HashMap<Integer, String> ();

	//DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
	ArrayAdapter<String> adapter;

	//RECORDING HOW MUCH TIMES BUTTON WAS CLICKED
	int clickCounter=0;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.list);

		Intent i = getIntent();
		String fbid = i.getStringExtra("fbid");
		String [] home_req_array = {"mode", "getReadings", "fbid", fbid};
		new DownloadReadingsTask().execute(home_req_array);
		new AlertDialog.Builder(this).setTitle("Argh").setMessage("ohai").setNeutralButton("Close", null).show();  

		adapter = new ObjectAdapter<String>(this,
				android.R.layout.simple_list_item_1, items, listItems);
		setListAdapter(adapter);


	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Do something with the data

		try {
			JSONObject o = (JSONObject) v.getTag();
			String readid = (String) o.get("id").toString();
			
			/* Toast.makeText(getApplicationContext(),
					"Click ListItem Number " + readid, Toast.LENGTH_LONG)
					.show(); */
			
			Intent nextScreen = new Intent(getApplicationContext(), DynamicChunkActivity.class);

			//Sending data to another Activity
			nextScreen.putExtra("readingId", readid);
			nextScreen.putExtra("id", (String) o.get("currentChunk").toString());
			
			// starting new activity
			startActivity(nextScreen);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		

	}
	//METHOD WHICH WILL HANDLE DYNAMIC INSERTION
	public void addItems(View v) {
		listItems.add("Clicked : "+clickCounter++);
		adapter.notifyDataSetChanged();
	}

	public void addRow(String s, JSONObject o) {
		listItems.add(s);
		items.add(o);
		adapter.notifyDataSetChanged();
	}


}
