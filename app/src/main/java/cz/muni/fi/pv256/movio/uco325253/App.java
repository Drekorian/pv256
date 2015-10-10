package cz.muni.fi.pv256.movio.uco325253;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

/**
 * This class serves as a custom extension of Android Application class that enforces the Strict
 * mode.
 * <p/>
 * Created by xosvald on 21.09.2015.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            initStrictMode();
        }
    }

    private void initStrictMode() {
        StrictMode.ThreadPolicy.Builder tpb = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            tpb.penaltyFlashScreen();
        }

        StrictMode.setThreadPolicy(tpb.build());

        StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            vmPolicyBuilder.detectLeakedClosableObjects();
        }

        StrictMode.setVmPolicy(vmPolicyBuilder.build());
    }

}
