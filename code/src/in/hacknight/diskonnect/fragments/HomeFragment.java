package in.hacknight.diskonnect.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import in.hacknight.diskonnect.AppState;
import in.hacknight.diskonnect.MainActivity;
import in.hacknight.diskonnect.R;
import in.hacknight.model.Event;
import in.hacknight.model.Profile;
import in.hacknight.model.ScoreCalculator;
import in.hacknight.storage.DataStorage;

public class HomeFragment extends Fragment {

	Button disconnected = null;
    View view = null;
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    view = inflater.inflate(R.layout.frag_home, container, false);
		disconnected = (Button) view.findViewById(R.id.button_diconnected);
		final Button disconnected = (Button) view.findViewById(R.id.button_diconnected);
		TextView score = (TextView) view.findViewById(R.id.score);
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        Integer score1 = scoreCalculator.getScore(getActivity());
        score.setText(score1.toString());
		disconnected.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (AppState.getIsDisconnected(getActivity())) {
					AppState.setCurrentProfileId(0, getActivity());
					AppState.setIsDisconnected(false, getActivity());
					disconnected.setText("Disconnect");

					long startTime = AppState.getStartTime(getActivity());
					Profile profile = (new DataStorage(getActivity()).getProfileById(AppState.getCurrentProfileId(getActivity())));
					
					new DataStorage(getActivity()).storeEvent(new Event(profile.duration, (int)startTime, (int)System.currentTimeMillis(), profile.id));
					

				} else {
					MainActivity ma = (MainActivity) getActivity();
					ma.addFragmentToStack(new ProfileSelectionFragment());
				}
			}
		});

		return view;
	}

	@Override
	public void onResume() {
		if (AppState.getIsDisconnected(getActivity())) {
			disconnected.setText("Connect");
		} else {
            Button disconnected = (Button) view.findViewById(R.id.button_diconnected);
            TextView score = (TextView) view.findViewById(R.id.score);
            ScoreCalculator scoreCalculator = new ScoreCalculator();
            Integer score1 = scoreCalculator.getScore(getActivity());
            score.setText(score1.toString());
            disconnected.setText("Disconnect");
		}
		super.onResume();
	}
}
