package in.hacknight.adaptors;

import in.hacknight.model.Profile;

import java.util.List;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProfileAdaptor extends ArrayAdapter<Profile> {

	private LayoutInflater inflator;
	public List<Profile> cachedObjs = null;

	public ProfileAdaptor(Context context, List<Profile> objects) {
		super(context, 0, objects);
		this.cachedObjs = objects;
		this.inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView;
		if (convertView == null) {
			rowView = this.inflator.inflate(
					android.R.layout.simple_list_item_1, parent, false);
		} else {
			rowView = convertView;
		}

		TextView tv = (TextView) rowView.findViewById(R.id.text1);

		if (this.cachedObjs != null) {
			Profile profile = (Profile) this.cachedObjs.get(position);
			if (profile.isAddNewRow)
				tv.setText("Add new profile...");
			else
				tv.setText(profile.name);
		}

		return rowView;
	}
}
