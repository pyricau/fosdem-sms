package info.piwai.fosdemsms;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.telephony.SmsMessage;

import com.googlecode.androidannotations.annotations.Enhanced;

@Enhanced
public class SmsHelper {

	private static final String SMS_EXTRA_NAME = "pdus";

	public Iterable<SmsMessage> smsMessagesFromExtras(Bundle extras) {
		Object[] smsExtra = (Object[]) extras.get(SMS_EXTRA_NAME);

		List<SmsMessage> smsMessages = new ArrayList<SmsMessage>();
		for (int i = 0; i < smsExtra.length; i++) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) smsExtra[i]);
			smsMessages.add(smsMessage);
		}

		return smsMessages;
	}

	public boolean isFosdemSms(SmsMessage smsMessage) {
		return smsMessage.getMessageBody().toLowerCase().contains("fosdem");
	}

}
