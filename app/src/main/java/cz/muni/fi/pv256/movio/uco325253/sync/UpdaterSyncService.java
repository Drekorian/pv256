package cz.muni.fi.pv256.movio.uco325253.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import cz.muni.fi.pv256.movio.uco325253.L;

/**
 * This class serves as the sync service.
 * <p/>
 * Created by xosvald on 31.01.2016.
 */
public class UpdaterSyncService extends Service {

    private static final String TAG = UpdaterSyncService.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static UpdaterSyncAdapter sUpdaterSyncAdapter = null;

    @Override
    public void onCreate() {
        L.d(TAG, "onCreate() called");
        synchronized (LOCK) {
            if (sUpdaterSyncAdapter == null) {
                sUpdaterSyncAdapter = new UpdaterSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        L.d(TAG, "onBind() called");
        return sUpdaterSyncAdapter.getSyncAdapterBinder();
    }

}
