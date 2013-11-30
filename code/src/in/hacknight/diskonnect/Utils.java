package in.hacknight.diskonnect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import in.hacknight.model.Event;
import in.hacknight.model.Profile;
import in.hacknight.storage.DataStorage;

import java.lang.reflect.Method;

public class Utils {

	public static void startDisconnected(Context ctx, int profileId) {
		long epoch = System.currentTimeMillis();
		AppState.setStartTime(epoch, ctx);
		AppState.setCurrentProfileId(profileId, ctx);
		AppState.setIsDisconnected(true, ctx);
        setProfileProperty(ctx, profileId);
	}

	public static void startConnected(Context ctx, int profileId) {
        AppState.setCurrentProfileId(0, ctx);
		AppState.setIsDisconnected(false, ctx);
		long startTime = AppState.getStartTime(ctx);
		Profile profile = (new DataStorage(ctx).getProfileById(profileId));
		new DataStorage(ctx).storeEvent(new Event(profile.duration, (int) startTime, (int) System.currentTimeMillis(),
				profile.id));
		AppState.setIsDisconnected(false, ctx);
        startBlockingCalls();
        startBlockingWifi(ctx, true);
        startBlockingData(ctx, false);
        startBlockingSms(ctx);
	}

    public static void setProfileProperty(Context ctx, int profileId){
        DataStorage ds = new DataStorage(ctx);
        int property = ds.getProfileById(profileId).property;
        if(property / 8 == 1 ){
            property = property /8;
            startBlockingData(ctx, true);
        }
        if(property / 4 == 1 ){
            property = property /4;
            startBlockingWifi(ctx, false);
        }
        if(property / 2 == 1 ){
            property = property /2;
            startBlockingCalls();
        }
        if(property == 1 ){
            startBlockingSms(ctx);
        }
    }

    private static void startBlockingWifi(Context ctx, Boolean flag){
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(flag);
    }

    private static void startBlockingData(Context paramContext, boolean enable){
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) paramContext.getSystemService("connectivity");
            Method setMobileDataEnabledMethod = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);
            setMobileDataEnabledMethod.invoke(connectivityManager, enable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void startBlockingCalls(){
        //yet to implement
    }

    private static void startBlockingSms(Context ctx){
        //Yet to implement
    }


}
