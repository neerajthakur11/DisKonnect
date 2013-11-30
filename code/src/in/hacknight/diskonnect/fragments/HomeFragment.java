package in.hacknight.diskonnect.fragments;

import in.hacknight.diskonnect.AppState;
import in.hacknight.diskonnect.MainActivity;
import in.hacknight.diskonnect.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

	Button disconnected = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_home, container, false);
		disconnected = (Button) view.findViewById(R.id.button_diconnected);
		disconnected.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (AppState.getIsDisconnected(getActivity())) {
					AppState.setCurrentProfileId(0, getActivity());
					AppState.setIsDisconnected(false, getActivity());
					disconnected.setText("Disconnect");

					// TODO Add an event.

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
			disconnected.setText("Disconnect");
		}
		super.onResume();
	}

}
