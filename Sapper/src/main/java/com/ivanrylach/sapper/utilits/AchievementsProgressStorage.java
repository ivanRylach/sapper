package com.ivanrylach.sapper.utilits;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Ivan Rylach on 11/17/13.
 * ivan.rylach@gmail.com
 */

public class AchievementsProgressStorage {

    public static final String TAG = AchievementsProgressStorage.class.getSimpleName();

    private static String SHARED_PREF_NAME = "shared_achievemetns";
    private static String KEY_GAMES_PLAYED = "games_played";
    private static String KEY_VICTORIES_SEQUENCE = "victories_sequence";
    private static String KEY_DEFEATS_WITH_ONE_CLICK = "defeats_with_one_click";
    private static String KEY_WAS_LAST_VICTORY = "was_last_victory";

    private Context context;
    private SharedPreferences sharedPreferences;

    /**
     * SINGLETON CODE START
     */
    private static final class SingletonHolder {
        static final AchievementsProgressStorage singleton = new AchievementsProgressStorage();
    }

    private AchievementsProgressStorage() {
    }

    public static AchievementsProgressStorage getInstance() {
        return SingletonHolder.singleton;
    }
    /**
     * SINGLETON CODE END
     */

    public void init(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void gamePlayed(boolean isVictory, int clickNumber) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Log.i(TAG, "Is Victory: " + isVictory + "; clickNumber: " + clickNumber);

        addGamePlayed(editor);
        if (isVictory) {
            addVictory(editor);
        } else {
            addDefeat(editor, clickNumber);
        }

        editor.commit();
    }

    private void addVictory(SharedPreferences.Editor editor) {
        boolean wasLastVictory = sharedPreferences.getBoolean(KEY_WAS_LAST_VICTORY, false);

        if (wasLastVictory) {
            int victoriesNumber = sharedPreferences.getInt(KEY_VICTORIES_SEQUENCE, 0);
            editor.putInt(KEY_VICTORIES_SEQUENCE, ++victoriesNumber);
        }


    }

    private void addDefeat(SharedPreferences.Editor editor, int clickNumber) {
        if (clickNumber == 1) {
            int defeatsNumber = sharedPreferences.getInt(KEY_DEFEATS_WITH_ONE_CLICK, 0);
            editor.putInt(KEY_DEFEATS_WITH_ONE_CLICK, ++defeatsNumber);
        }

    }


    private void addGamePlayed(SharedPreferences.Editor editor) {
        int gamesNumber = sharedPreferences.getInt(KEY_GAMES_PLAYED, 0);
        editor.putInt(KEY_GAMES_PLAYED, ++gamesNumber);
    }

}
