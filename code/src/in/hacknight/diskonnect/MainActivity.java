package in.hacknight.diskonnect;

import in.hacknight.diskonnect.fragments.HomeFragment;
import in.hacknight.diskonnect.fragments.ProfileSelectionFragment;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addFragment(new HomeFragment());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_settings: {
			ProfileSelectionFragment fragment = new ProfileSelectionFragment();
			fragment.isSettingsMode = true;
			addFragmentToStack(fragment);
			return true;
		}

		}

		return super.onOptionsItemSelected(item);
	}

	public void addFragmentToStack(Fragment fragment) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.main_view, fragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	private void addFragment(Fragment fragment) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.main_view, fragment);
		ft.commit();
	}

}
