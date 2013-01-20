package com.ptzlabs.wc;

// Download the twilio-java library from http://twilio.com/docs/libraries
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestException;
 
public class SmsSender {
 
    /* Find your sid and token at twilio.com/user/account */
    public static final String ACCOUNT_SID = "ACe55c6c977847f4f99d9cd0c6b0798ae6";
    public static final String AUTH_TOKEN = "4455aefcaf4e8146541c85c2bca963d8";
 
    public static void sendChunk(Reading reading) throws TwilioRestException {

 		HttpClient httpClient = new DefaultHttpClient();
		
		User user = ofy().load().type(User.class).id(reading.user).get();
		Chunk chunk = reading.getCurrentChunk();
		if (chunk == null) return;

	    try {
    	    HttpPost request = new HttpPost("https://"+ACCOUNT_SID+":"+AUTH_TOKEN+"@api.twilio.com/2010-04-01/Accounts/"
    	    	+ACCOUNT_SID+"/SMS/Messages");

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
    		nameValuePairs.add(new BasicNameValuePair("Body", chunk.data));
    		nameValuePairs.add(new BasicNameValuePair("To", String.valueOf(user.phone)));
    		nameValuePairs.add(new BasicNameValuePair("From",  "+13238440271"));    		
    		request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

    		// Execute HTTP Post Request
   	 		HttpResponse response = httpClient.execute(request);

		} catch (ClientProtocolException e) {
    		// TODO Auto-generated catch block
		} catch (IOException e) {
    		// TODO Auto-generated catch block
		}
		/*
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
 
        Account account = client.getAccount();

		User user = ofy().load().type(User.class).id(reading.user).get();
		Chunk chunk = reading.getCurrentChunk();
		if (chunk == null) return;
		

        SmsFactory smsFactory = account.getSmsFactory();
        Map<String, String> smsParams = new HashMap<String, String>();
        smsParams.put("To", String.valueOf(user.phone)); 
        smsParams.put("From", "+13238440271"); // Replace with a valid phone
        // number in your account
        smsParams.put("Body", chunk.data);
        Sms sms = smsFactory.create(smsParams); */
    }
}
