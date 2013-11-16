package com.ivanrylach.sapper.activities;

import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.ivanrylach.sapper.R;

public class AboutDialog extends SherlockActivity {
	TextView content;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);
        content = (TextView) findViewById(R.id.text);
        content.setAutoLinkMask(Linkify.EMAIL_ADDRESSES);
        content.setText(R.string.text_about_content);
    }
}
