package com.ptzlabs.wc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Window;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class WoodchuckActivity extends Activity {



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main);
		
		Typeface tf = Typeface.createFromAsset(getAssets(),
		        "Oswald.ttf");
		TextView tv = (TextView) findViewById(R.id.main_text);
		tv.setTypeface(tf);
		tv.setPadding(0, 40, 0, 0);


		// start Facebook Login
		Session.openActiveSession(this, true, new Session.StatusCallback() {

			// callback when session changes state
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				if (session.isOpened()) {
					// make request to the /me API
					Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

						// callback after Graph API response with user object
						@Override
						public void onCompleted(GraphUser user, Response response) {
							if (user != null) {
								/* LinearLayout lay = (LinearLayout) findViewById(R.id.homepage);
								TextView tv = new TextView(WoodchuckActivity.this);
								tv.setText("Hello " + user.getName() + "!");
								lay.addView(tv); */
								
								String fbid = user.getId();
								// new AlertDialog.Builder(WoodchuckActivity.this).setTitle("getting fb id").setMessage(fbid).setNeutralButton("Close", null).show(); 

								Intent nextScreen = new Intent(getApplicationContext(), DynamicList.class);

								//Sending data to another Activity
								nextScreen.putExtra("fbid", fbid);

								// starting new activity
								startActivity(nextScreen);
							}
						}
					});
				}
			}
		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}

}