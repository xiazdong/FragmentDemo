package xiazdong.me.fragmentdemo;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.stetho.Stetho;

import timber.log.Timber;
import xiazdong.me.fragmentdemo.config.GlobalContext;
import xiazdong.me.fragmentdemo.config.ThreadAwareDebugTree;
import xiazdong.me.fragmentdemo.db.DBOperator;

/**
 * Created by xiazdong on 17/5/24.
 */

public class MainApplication extends Application {
    private static final String PREFS_KEY_IS_FIRST_LAUNCH = "is_first_launch";

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new ThreadAwareDebugTree());
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
        GlobalContext.setContext(this);
        SharedPreferences sf = getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        boolean isFirstLaunch = sf.getBoolean(PREFS_KEY_IS_FIRST_LAUNCH, true);
        Timber.d("is first launch = " + isFirstLaunch);
        if (isFirstLaunch) {
            DBOperator.insertData();
            sf.edit().putBoolean(PREFS_KEY_IS_FIRST_LAUNCH, false).apply();
        }
    }
}
