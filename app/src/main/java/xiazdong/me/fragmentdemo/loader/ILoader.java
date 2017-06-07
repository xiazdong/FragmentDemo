package xiazdong.me.fragmentdemo.loader;

/**
 * Created by xiazdong on 17/6/4.
 */

public interface ILoader {
    public static final int LOADER_CATEGORY_ID = 1;
    public static final int LOADER_MATERIAL_ID = 2;

    public void start();

    public void destroy();
}
