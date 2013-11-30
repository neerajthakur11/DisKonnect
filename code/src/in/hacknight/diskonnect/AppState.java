package in.hacknight.diskonnect;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppState {

	public static void setIsDisconnected(boolean flag, Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("IS_DISCONNECTED", flag);
		editor.commit();
	}

	public static boolean getIsDisconnected(Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences.getBoolean("IS_DISCONNECTED", false);
	}

	public static void setCurrentProfileId(int _id, Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("IS_DISCONNECTED", _id);
		editor.commit();
	}

	public static int getCurrentProfileId(Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences.getInt("IS_DISCONNECTED", 0);
	}

	public static void setStartTime(long time, Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putLong("START_TIME", time);
		editor.commit();
	}

	public static long getStartTime(Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences.getLong("START_TIME", 0);
	}

}
