package info.piwai.fosdemsms;

import static android.widget.Toast.LENGTH_LONG;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.Fullscreen;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.StringRes;

@NoTitle
@Fullscreen
@EActivity(R.layout.main)
public class FosdemSmsActivity extends Activity {

	@Extra("sender")
	String sender;

	@Extra("message")
	String message;

	@ViewById
	TextView fosdemTextView;
	
	@StringRes
	String senderToast;
	
	@AfterViews
	void fillText() {
		fosdemTextView.setText(sender + "\n\n" + message);
	}
	
	@Click
	void fosdemTextViewClicked() {
		Toast.makeText(this, String.format(senderToast, sender), LENGTH_LONG).show();
	}

}