package tn.esprit;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.log4j.BasicConfigurator;

public class TwilioSendSms {
    String accountSID = "ACb7699b0b6314abd7941be7a60f5822b5";
    String authToken = "7b8c6ffeae034e44852e82280ab5a7c3";
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
