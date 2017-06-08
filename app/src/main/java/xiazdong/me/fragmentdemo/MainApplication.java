package xiazdong.me.fragmentdemo;

import android.app.Application;
import com.facebook.stetho.Stetho;
import timber.log.Timber;
import xiazdong.me.fragmentdemo.config.GlobalContext;
import xiazdong.me.fragmentdemo.config.ThreadAwareDebugTree;
import xiazdong.me.fragmentdemo.db.DBOperator;
import xiazdong.me.fragmentdemo.util.PrefUtils;

import static xiazdong.me.fragmentdemo.util.PrefUtils.PREFS_KEY_IS_FIRST_LAUNCH;

/**
 * Created by xiazdong on 17/5/24.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new ThreadAwareDebugTree());
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
        GlobalContext.setContext(this);

        boolean isFirstLaunch = PrefUtils.getBoolean(PREFS_KEY_IS_FIRST_LAUNCH, true);
        Timber.d("is first launch = " + isFirstLaunch);
        if (isFirstLaunch) {
            DBOperator.insertData();
            PrefUtils.putBoolean(PREFS_KEY_IS_FIRST_LAUNCH, false);
        }
    }
}
