package info.piwai.fosdemsms;

import android.app.Activity;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.main)
public class FosdemSmsActivity extends Activity {

	@Extra("sender")
	String sender;

	@Extra("message")
	String message;

	@ViewById
	TextView fosdemTextView;

	@AfterViews
	void fillText() {
		setTitle("From " + sender);
		fosdemTextView.setText(message);
	}

}