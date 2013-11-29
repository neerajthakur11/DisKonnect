package in.hacknight.diskonnect.fragments;

import in.hacknight.adaptors.ProfileAdaptor;
import in.hacknight.diskonnect.MainActivity;
import in.hacknight.diskonnect.R;
import in.hacknight.model.Profile;
import in.hacknight.storage.DataStorage;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ProfileSelectionFragment extends Fragment implements
		OnItemClickListener {

	public boolean isSettingsMode = false;

	private ProfileAdaptor adapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.frag_profile_selection,
				container, false);
		ListView lv = (ListView) view.findViewById(R.id.list_view);
		ArrayList<Profile> allProfiles = (ArrayList<Profile>) new DataStorage(
				getActivity()).getAllProfiles();

		// Bad hack
		if (isSettingsMode)
			allProfiles.add(new Profile("", 0, 0, true));

		adapter = new ProfileAdaptor(getActivity(), allProfiles);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);

		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Profile profile = (Profile) this.adapter.cachedObjs.get(position);
		if (isSettingsMode) {
			((MainActivity) getActivity())
					.addFragmentToStack(new ProfileSettingsFragment());
		} else {
			FragmentManager fm = getActivity().getFragmentManager();
			fm.popBackStack();
		}

	}

}
