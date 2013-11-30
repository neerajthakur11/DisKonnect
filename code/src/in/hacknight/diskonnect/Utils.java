package in.hacknight.diskonnect;

import in.hacknight.model.Event;
import in.hacknight.model.Profile;
import in.hacknight.storage.DataStorage;
import android.content.Context;

public class Utils {

	public static void startDisconnected(Context ctx, int profileId) {
		long epoch = System.currentTimeMillis();
		AppState.setStartTime(epoch, ctx);
		AppState.setCurrentProfileId(profileId, ctx);
		AppState.setIsDisconnected(true, ctx);
	}

	public static void startConnected(Context ctx, int profileId) {
		AppState.setCurrentProfileId(0, ctx);
		AppState.setIsDisconnected(false, ctx);
		long startTime = AppState.getStartTime(ctx);
		Profile profile = (new DataStorage(ctx).getProfileById(profileId));
		new DataStorage(ctx).storeEvent(new Event(profile.duration,
				(int) startTime, (int) System.currentTimeMillis(), profile.id));
	}

}
