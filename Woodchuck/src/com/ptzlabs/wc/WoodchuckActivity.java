package com.ptzlabs.wc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

public class WoodchuckActivity extends Activity {



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// TODO: somehow get a facebook id
		String fbid = "701479008";
		/* 		String [] home_req_array = {"mode", "getReadings", "fbid", fb_id};
		new DownloadReadingsTask().execute(home_req_array); */
		new AlertDialog.Builder(this).setTitle("getting fb id").setMessage(fbid).setNeutralButton("Close", null).show(); 

		Intent nextScreen = new Intent(getApplicationContext(), DynamicList.class);

		//Sending data to another Activity
		nextScreen.putExtra("fbid", fbid);

		// starting new activity
		startActivity(nextScreen);

	}

}