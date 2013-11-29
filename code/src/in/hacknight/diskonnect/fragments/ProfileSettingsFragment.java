package in.hacknight.diskonnect.fragments;

import in.hacknight.diskonnect.R;
import in.hacknight.model.Profile;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ProfileSettingsFragment extends Fragment {

	private View view = null;
	public Profile profile = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.frag_profile_settings, container,
				false);
		Button buttonSave = (Button) view.findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		return view;
	}

}
