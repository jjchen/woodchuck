package com.ptzlabs.wc;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DynamicChunkActivity extends Activity {

	class DownloadChunkTask extends DownloadTask {

		@Override
		String updateString(String[] arg0) {
			String uri = "http://wc.ptzlabs.com/chunk?";
			uri += arg0[0] + "=" + arg0[1];
			uri += "&" + arg0[2] + "=" + arg0[3];
			uri += "&" + arg0[4] + "=" + arg0[5];
			return uri;
		}
		
		@Override
		protected void onPostExecute(HttpResponse result) {
			try {
				String jsonString = EntityUtils.toString(result.getEntity());
				Log.e("result", jsonString);

				JSONObject json_obj = new JSONObject(jsonString);
				chunk_text = json_obj.get("data").toString();
				Log.d("id", chunk_text);
				
				LinearLayout lay = (LinearLayout) findViewById(R.id.detail);
				
				TextView tv = new TextView(DynamicChunkActivity.this);
				tv.setText(chunk_text);
				lay.addView(tv);

				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private String chunk_text;
	private int group1Id = 1;

	int prevId = Menu.FIRST;
	int commentId = Menu.FIRST + 1;
	int nextId = Menu.FIRST + 2;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.detail);

		Intent i = getIntent();
		String readingId = i.getStringExtra("readingId");
		String id = i.getStringExtra("id");
		
		Log.d("readingId", readingId);
		Log.d("id", id);
		
		
		String [] home_req_array = {"mode", "get", "readingId", readingId, "id", id};
		new DownloadChunkTask().execute(home_req_array);
		// new AlertDialog.Builder(this).setTitle("Argh").setMessage(chunk_text).setNeutralButton("Close", null).show();  


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(group1Id, prevId, prevId, "").setIcon(R.drawable.ic_launcher);
		menu.add(group1Id, commentId, commentId, "").setIcon(R.drawable.ic_launcher);
		menu.add(group1Id, nextId, nextId, "").setIcon(R.drawable.ic_launcher);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case 1:
			Toast msg1 = Toast.makeText(DynamicChunkActivity.this, "Menu 1", Toast.LENGTH_LONG);
			msg1.show();
			return true;

		case 2:
			Toast msg2 = Toast.makeText(DynamicChunkActivity.this, "Menu 2", Toast.LENGTH_LONG);
			msg2.show();

			return true;

		case 3:
			Toast msg3 = Toast.makeText(DynamicChunkActivity.this, "Menu 3", Toast.LENGTH_LONG);
			msg3.show();
			return true;


		}
		return false;
	}
}
