package com.ptzlabs.wc;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadTask extends AsyncTask<String, Integer, HttpResponse> {

	protected HttpResponse doInBackground(String... arg0) {

		String uri = updateString(arg0);
		
		// InputStream is = null;
		
		Log.d("update", uri);


		if (uri.contains("http") == true) {// Get JSON from URL
			try {


				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(uri);

				httpPost.setHeader("Content-type", "application/json");
				httpPost.setHeader("Accept", "application/json");

				/*				List<NameValuePair> params = new ArrayList<NameValuePair>();
		            params.add(new BasicNameValuePair("mode", "get"));
		            params.add(new BasicNameValuePair("id", "25001"));
		            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
		            httpPost.setEntity(ent); */

				return httpClient.execute(httpPost);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	String updateString(String [] arg0) {
		// do nothing, will get overridden later
		return "";
	}

	@Override
	protected void onPostExecute(HttpResponse result) {
		// do nothing, will get overridden later
	}
}
