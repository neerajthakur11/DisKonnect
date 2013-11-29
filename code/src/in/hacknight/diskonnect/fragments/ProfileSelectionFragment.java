package in.hacknight.diskonnect.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import in.hacknight.adaptors.ProfileAdaptor;
import in.hacknight.diskonnect.R;

import java.util.ArrayList;

public class ProfileSelectionFragment extends Fragment {


    private ProfileAdaptor adapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.frag_profile_selection, container, false);
        ListView lv = (ListView)view.findViewById(R.id.list_view);
        ArrayList a = new ArrayList();
        a.add("a");
        a.add("a");
        a.add("a");
        a.add("a");
        adapter = new ProfileAdaptor(getActivity(), a);
        lv.setAdapter(adapter);

		return view;
	}

}
