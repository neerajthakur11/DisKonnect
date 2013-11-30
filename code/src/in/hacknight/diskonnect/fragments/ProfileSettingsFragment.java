package in.hacknight.diskonnect.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import in.hacknight.diskonnect.Params;
import in.hacknight.diskonnect.R;
import in.hacknight.model.Profile;
import in.hacknight.storage.DataStorage;

import static in.hacknight.diskonnect.Params.*;

public class ProfileSettingsFragment extends Fragment {

    private View view = null;
    public Profile profile = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_profile_settings, container,
                false);
        Button buttonSave = (Button) view.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DataStorage dataStorage = new DataStorage(getActivity());
                String name = ((EditText) view.findViewById(R.id.editTextProfileName)).getText().toString();
                int duration = Integer.parseInt(((TextView) view.findViewById(R.id.duration)).getText().toString().replace(" mins", ""));
                int sms = ((CheckBox)view.findViewById(R.id.checkBoxSMS)).isChecked() ? 1 : 0;
                int wifi = ((CheckBox)view.findViewById(R.id.checkBoxWifi)).isChecked()? 1 : 0;
                int data = ((CheckBox)view.findViewById(R.id.checkBoxData)).isChecked()? 1 : 0;
                int calls = ((CheckBox)view.findViewById(R.id.checkBoxCalls)).isChecked()? 1 : 0;
                int property = (PERMISSION_CALL * calls) + (PERMISSION_DATA * data) + (PERMISSION_SMS * sms) + (PERMISSION_WIFI * wifi);
                if(profile == null)
                dataStorage.storeProfile(new Profile(name, property, duration * 60 *1000, false));
                else
                dataStorage.updateProfile(new Profile(name, property, duration * 60 *1000, false));

			}
		});

		return view;
    }

	private void updateViews() {

		Button buttonSave = (Button) view.findViewById(R.id.buttonSave);
		buttonSave.setText("Save Changes");

		CheckBox cb = (CheckBox) view.findViewById(R.id.checkBoxSMS);
		if ((profile.property & Params.PERMISSION_SMS) != 0)
			cb.setChecked(true);

		cb = (CheckBox) view.findViewById(R.id.checkBoxCalls);
		if ((profile.property & Params.PERMISSION_CALL) != 0)
			cb.setChecked(true);

		cb = (CheckBox) view.findViewById(R.id.checkBoxWifi);
		if ((profile.property & Params.PERMISSION_WIFI) != 0)
			cb.setChecked(true);

		cb = (CheckBox) view.findViewById(R.id.checkBoxData);
		if ((profile.property & Params.PERMISSION_DATA) != 0)
			cb.setChecked(true);

		EditText ed = (EditText) view.findViewById(R.id.edittextProfileName);
		ed.setText(profile.name);

	}

}
