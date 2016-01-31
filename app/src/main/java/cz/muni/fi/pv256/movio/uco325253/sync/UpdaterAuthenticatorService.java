package cz.muni.fi.pv256.movio.uco325253.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import cz.muni.fi.pv256.movio.uco325253.L;

/**
 * This class serves as the sync authenticator service.
 * <p/>
 * Created by xosvald on 31.01.2016.
 */
public class UpdaterAuthenticatorService extends Service {

    private static final String TAG = UpdaterAuthenticatorService.class.getSimpleName();

    private UpdaterAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        L.d(TAG, "onCreate() called");
        super.onCreate();
        mAuthenticator = new UpdaterAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        L.d(TAG, "onBind() called");
        return mAuthenticator.getIBinder();
    }

}
