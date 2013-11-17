package com.ivanrylach.sapper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ivanrylach.sapper.R;
import com.ivanrylach.sapper.utilits.BaseGameActivity;

public class MainActivity extends BaseGameActivity {
    
	private Button newGameBtn;
	private Button aboutBtn;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initButtons();
        
		}

	private void initButtons() {
		newGameBtn = (Button) findViewById(R.id.newGameButton);
		newGameBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, GameField.class));
			}
		});
		
		aboutBtn = (Button) findViewById(R.id.aboutButton);
		aboutBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, AboutDialog.class));
			}
		});
		
	}


    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSucceeded() {

    }
}