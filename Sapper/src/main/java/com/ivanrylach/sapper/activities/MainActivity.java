package com.ivanrylach.sapper.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.games.GamesClient;
import com.ivanrylach.sapper.R;
import com.ivanrylach.sapper.fragments.AboutDialogFragment;
import com.ivanrylach.sapper.utilits.AchievementsProgressStorage;
import com.ivanrylach.sapper.utilits.BaseGameActivity;

public class MainActivity extends BaseGameActivity {

    private static int REQUEST_ACHIEVEMENTS = 0x201;
    
	private Button newGameBtn;
	private Button aboutBtn;
    private SignInButton buttonSignIn;
    private Button buttonSignOut;
    private OnClickListener clickListener;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initButtons();
        initSingletones();
        
		}

    private void initSingletones() {
        AchievementsProgressStorage.getInstance().init(this);
    }

    private void initButtons() {
        initClickListener();

        newGameBtn = (Button) findViewById(R.id.newGameButton);
		newGameBtn.setOnClickListener(clickListener);

		aboutBtn = (Button) findViewById(R.id.aboutButton);
		aboutBtn.setOnClickListener(clickListener);

        buttonSignIn = (com.google.android.gms.common.SignInButton) findViewById(R.id.sign_in_button);
        buttonSignIn.setOnClickListener(clickListener);
        buttonSignOut = (Button) findViewById(R.id.sign_out_button);
        buttonSignOut.setOnClickListener(clickListener);
        findViewById(R.id.achievemtns_button).setOnClickListener(clickListener);
	}

    private void initClickListener() {
        clickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.newGameButton: {
                        startActivity(new Intent(MainActivity.this, GameField.class));
                        break;
                    }

                    case R.id.aboutButton: {
//                        startActivity(new Intent(MainActivity.this, AboutDialog.class));
                        showAboutDialog();
                        break;
                    }

                    case R.id.sign_in_button: {
                        beginUserInitiatedSignIn();
                        break;
                    }

                    case  R.id.sign_out_button:{
                        signOut();
                        buttonSignOut.setVisibility(View.GONE);
                        buttonSignIn.setVisibility(View.VISIBLE);
                        findViewById(R.id.achievemtns_button).setEnabled(false);
                        break;
                    }

                    case R.id.achievemtns_button: {
                        startActivityForResult(getGamesClient().getAchievementsIntent(), REQUEST_ACHIEVEMENTS);
                    }
                }
            }
        };
    }

    private void showAboutDialog() {
        DialogFragment dialogAbout = AboutDialogFragment.newInstance();
        dialogAbout.show(getSupportFragmentManager(), AboutDialogFragment.TAG);
    }


    @Override
    public void onSignInFailed() {
        findViewById(R.id.achievemtns_button).setEnabled(false);
        findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
    }

    @Override
    public void onSignInSucceeded() {
        findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        findViewById(R.id.achievemtns_button).setEnabled(true);
    }
}