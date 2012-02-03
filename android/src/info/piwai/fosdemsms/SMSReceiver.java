package info.piwai.fosdemsms;

import static java.lang.System.currentTimeMillis;

import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EReceiver;
import com.googlecode.androidannotations.annotations.SystemService;

@EReceiver
public class SMSReceiver extends BroadcastReceiver {

	@SystemService
	NotificationManager notificationManager;

	@Bean
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
								.flags(Intent.FLAG_ACTIVITY_NEW_TASK) //
								.get();

						int someRandomId = new Random().nextInt();

						showMessageIntent.setData(Uri.parse("hack:" + someRandomId));

						Notification notification = new Notification(R.drawable.ic_launcher, "New Fosdem Sms!", currentTimeMillis());
						notification.flags = Notification.FLAG_AUTO_CANCEL;
						PendingIntent contentIntent = PendingIntent.getActivity(context, 0, showMessageIntent, PendingIntent.FLAG_CANCEL_CURRENT);
						Context applicationContext = context.getApplicationContext();
						notification.setLatestEventInfo(applicationContext, "Message from " + smsMessage.getOriginatingAddress(), "Click here to unleash Android God!", contentIntent);

						notificationManager.notify(someRandomId, notification);
					}
				}
			}

			if (matchingFosdemSms) {
				abortBroadcast();
			}
		}
	}

}
