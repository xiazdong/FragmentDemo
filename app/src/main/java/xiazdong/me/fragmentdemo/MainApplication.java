package xiazdong.me.fragmentdemo;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by xiazdong on 17/5/24.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new ThreadAwareDebugTree());
    }
}
