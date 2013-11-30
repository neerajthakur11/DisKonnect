package in.hacknight.diskonnect.fragments;

import in.hacknight.diskonnect.AppState;
import in.hacknight.diskonnect.MainActivity;
import in.hacknight.diskonnect.R;
import in.hacknight.diskonnect.Utils;
import in.hacknight.model.ScoreCalculator;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
        Long score1 = scoreCalculator.getScore(getActivity());
        score.setText(score1.toString());
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
            Long score1 = scoreCalculator.getScore(getActivity());
            score.setText(score1.toString());
            disconnected.setText("Disconnect");
		}
		super.onResume();
	}
}
