package com.ivanrylach.sapper.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.ivanrylach.sapper.R;

/**
 * Created by Ivan Rylach on 11/22/13.
 * ivan.rylach@gmail.com
 */

public class AboutDialogFragment extends SherlockDialogFragment {

    public static final String TAG = AboutDialogFragment.class.getSimpleName();

    private ImageButton imgbuttonGPlus;
    private ImageButton imgbuttonGithub;

    public static AboutDialogFragment newInstance() {
        AboutDialogFragment frag = new AboutDialogFragment();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LinearLayout dialogContent = new LinearLayout(getSherlockActivity());

        LayoutInflater inflater = (LayoutInflater)getSherlockActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_about, dialogContent);

        initButtons(dialogContent);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.word_about)
                .setView(dialogContent)
                .setPositiveButton(R.string.got_it,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .create();
    }

    private void initButtons(LinearLayout layout) {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.imgbutton_gplus: {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_g_plus)));
                        startActivity(i);
                        break;
                    }

                    case R.id.imgbutton_github: {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_github)));
                        startActivity(i);
                        break;
                    }
                }
            }
        };

        layout.findViewById(R.id.imgbutton_gplus).setOnClickListener(clickListener);
        layout.findViewById(R.id.imgbutton_github).setOnClickListener(clickListener);
    }
}
