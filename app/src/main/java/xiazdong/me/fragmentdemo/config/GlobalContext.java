package xiazdong.me.fragmentdemo.config;

import android.content.Context;

/**
 * Created by xiazdong on 17/6/4.
 */

public class GlobalContext {
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    public static void setContext(Context context) {
        sContext = context;
    }
}
