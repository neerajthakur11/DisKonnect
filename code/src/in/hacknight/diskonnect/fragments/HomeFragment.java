package in.hacknight.diskonnect.fragments;

import in.hacknight.diskonnect.AppState;
import in.hacknight.diskonnect.MainActivity;
import in.hacknight.diskonnect.R;
import in.hacknight.diskonnect.Utils;
import in.hacknight.model.Profile;
import in.hacknight.model.ScoreCalculator;
import in.hacknight.storage.DataStorage;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	Button disconnected = null;
	View view = null;

	TextView header = null;
	TextView score = null;

	private CountDownTimer countDownTimer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.frag_home, container, false);
		disconnected = (Button) view.findViewById(R.id.button_diconnected);
		final Button disconnected = (Button) view.findViewById(R.id.button_diconnected);
		score = (TextView) view.findViewById(R.id.score);
		header = (TextView) view.findViewById(R.id.header);

		disconnected.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (AppState.getIsDisconnected(getActivity())) {
					disconnected.setText("Disconnect");
					int profileId = AppState.getCurrentProfileId(getActivity());
					Utils.startConnected(getActivity(), profileId);
				} else {
					MainActivity ma = (MainActivity) getActivity();
					ma.addFragmentToStack(new ProfileSelectionFragment());
				}
			}
		});

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

		OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
			public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
				if (key.equals("IS_DISCONNECTED"))
					manageHomeScreenState();
			}
		};
		prefs.registerOnSharedPreferenceChangeListener(listener);
		manageHomeScreenState();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void manageHomeScreenState() {
		if (AppState.getIsDisconnected(getActivity())) {
			int profileId = AppState.getCurrentProfileId(getActivity());
			Profile profile = (new DataStorage(getActivity()).getProfileById(profileId));
			startTimer(profile.duration);
			header.setText("Timer");
			disconnected.setText("Connect");
		} else {
			stopTimer();
			Button disconnected = (Button) view.findViewById(R.id.button_diconnected);
			TextView score = (TextView) view.findViewById(R.id.score);
			ScoreCalculator scoreCalculator = new ScoreCalculator();
			long score1 = scoreCalculator.getScore(getActivity());
			score.setText(Long.toString(score1));
			header.setText("Your determination over time:");
			disconnected.setText("Disconnect");
		}
	}

	public void startTimer(int interval) {
		if (countDownTimer != null) {
			countDownTimer = new MyCountDownTimer(interval, 1000);
			countDownTimer.start();
		}
	}

	public void stopTimer() {
		if (countDownTimer != null) {
			countDownTimer.cancel();
			countDownTimer = null;
		}
	}

	public class MyCountDownTimer extends CountDownTimer {
		public MyCountDownTimer(long startTime, long interval) {
			super(startTime * 1000, interval);
		}

		@Override
		public void onFinish() {
			int profileId = AppState.getCurrentProfileId(getActivity());
			Utils.startConnected(getActivity(), profileId);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			score.setText("" + millisUntilFinished / 1000);
		}
	}
}
