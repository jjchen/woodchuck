// Download the twilio-java library from http://twilio.com/docs/libraries
import java.util.Map;
import java.util.HashMap;
 
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;
 
public class SmsSender {
 
    /* Find your sid and token at twilio.com/user/account */
    public static final String ACCOUNT_SID = "ACe55c6c977847f4f99d9cd0c6b0798ae6";
    public static final String AUTH_TOKEN = "4455aefcaf4e8146541c85c2bca963d8";
 
    public static void sendChunk(Reading reading) throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
 
        Account account = client.getAccount();

		User user = ofy().load().type(User.classes).id(reading.user).get();
		Chunk chunk = reading.getCurrentChunk();
		if (chunk == null) return;
		reading.nextChunk();
		

        SmsFactory smsFactory = account.getSmsFactory();
        Map<String, String> smsParams = new HashMap<String, String>();
        smsParams.put("To", user.getphoneNumber); 
        smsParams.put("From", "+13238440271"); // Replace with a valid phone
        // number in your account
        smsParams.put("Body", chunk.data);
        Sms sms = smsFactory.create(smsParams);
    }
}
