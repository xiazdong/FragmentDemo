package xiazdong.me.fragmentdemo.config;

import timber.log.Timber;

/**
 * Created by xiazdong on 17/5/21.
 */

public class ThreadAwareDebugTree extends Timber.DebugTree {

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (tag != null) {
            String threadName = Thread.currentThread().getName();
            tag = "<" + threadName + "> " + tag;
        }
        super.log(priority, tag, message, t);
    }

    @Override
    protected String createStackElementTag(StackTraceElement element) {
        return super.createStackElementTag(element) + "(Line " + element.getLineNumber() + ")";  //日志显示行号
    }
}
