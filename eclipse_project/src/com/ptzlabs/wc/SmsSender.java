package com.ptzlabs.wc;

// Download the twilio-java library from http://twilio.com/docs/libraries
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.HashMap;
import java.util.Map;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Sms;
 
public class SmsSender {
 
    /* Find your sid and token at twilio.com/user/account */
    public static final String ACCOUNT_SID = "ACe55c6c977847f4f99d9cd0c6b0798ae6";
    public static final String AUTH_TOKEN = "4455aefcaf4e8146541c85c2bca963d8";
 
    public static void sendChunk(Reading reading) throws TwilioRestException {

		
		User user = ofy().load().type(User.class).id(reading.user).get();
		Chunk chunk = reading.getCurrentChunk();
		if (chunk == null) return;

	    try {
   /* 	    URL url = new URL("https://"+ACCOUNT_SID+":"+AUTH_TOKEN+"@api.twilio.com/2010-04-01/Accounts/"
    	    	+ACCOUNT_SID+"/SMS/Messages");
*/
			 URL url = new URL("https://"api.twilio.com/2010-04-01/Accounts/"
    	    	+ACCOUNT_SID+"/SMS/Messages");


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");

			String message = URLEncoder.encode("{\"To\":\""+String.valueOf(user.phone)+
				"\", \"From\":\"+13238440271\",\"Body\":\""+chunk.data+"\"}", "UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(message);
            writer.close();
    
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // OK
            } else {
                // Server returned HTTP error code.
            }
        } catch (MalformedURLException e) {
            // ...
        } catch (IOException e) {
            // ...
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
