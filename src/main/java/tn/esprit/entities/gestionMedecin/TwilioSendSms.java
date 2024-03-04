package tn.esprit.entities.gestionMedecin;



import com.twilio.Twilio;
import org.apache.log4j.BasicConfigurator;

public class TwilioSendSms {
    String accountSID = "ACb7699b0b6314abd7941be7a60f5822b5";
    String authToken = "46273f1820f21fb4408926dd5ed299bd";
    String fromNumberMyTwillioNumber = "+19784867818";

    public TwilioSendSms() {
        BasicConfigurator.configure();
        //        Authenticate the Twilio client
        Twilio.init(accountSID,authToken);
    }

    public String getFromNumberMyTwillioNumber() {
        return fromNumberMyTwillioNumber;
    }

    public void setFromNumberMyTwillioNumber(String fromNumberMyTwillioNumber) {
        this.fromNumberMyTwillioNumber = fromNumberMyTwillioNumber;
    }
}
