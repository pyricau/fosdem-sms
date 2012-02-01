package info.piwai.fosdemsms;

import static java.lang.System.currentTimeMillis;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.googlecode.androidannotations.annotations.EReceiver;
import com.googlecode.androidannotations.annotations.Inject;
import com.googlecode.androidannotations.annotations.SystemService;

/**
 * This sms broadcast received has a high priority, it should run prior to any
 * other sms receiver.
 * 
 * If one of the SMS is a Siine command, it is handled, and the broadcast is
 * aborted.
 */
@EReceiver
public class SMSReceiver extends BroadcastReceiver {

	@SystemService
	NotificationManager notificationManager;

	@Inject
	SmsHelper smsHelper;

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
			Bundle extras = intent.getExtras();

			boolean matchingFosdemSms = false;
			if (extras != null) {
				for (SmsMessage smsMessage : smsHelper.smsMessagesFromExtras(extras)) {

					if (smsHelper.isFosdemSms(smsMessage)) {
						matchingFosdemSms = true;

						Intent showMessageIntent = FosdemSmsActivity_.intent(context) //
								.sender(smsMessage.getOriginatingAddress()) //
								.message(smsMessage.getMessageBody()) //
								.get();

						Notification notification = new Notification(R.drawable.ic_launcher, "New Fosdem Message!", currentTimeMillis());
						notification.flags = Notification.FLAG_AUTO_CANCEL;
						PendingIntent contentIntent = PendingIntent.getActivity(context, 0, showMessageIntent, 0);
						Context applicationContext = context.getApplicationContext();
						notification.setLatestEventInfo(applicationContext, "Fosdem Message from " + smsMessage.getOriginatingAddress(), "Click here to let Android God read it", contentIntent);

						notificationManager.notify(1, notification);
					}
				}
			}

			if (matchingFosdemSms) {
				abortBroadcast();
			}
		}
	}

}
