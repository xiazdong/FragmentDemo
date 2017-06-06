package xiazdong.me.fragmentdemo.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import timber.log.Timber;
import xiazdong.me.fragmentdemo.util.PrefUtils;

/**
 * Created by xiazdong on 17/6/4.
 */

public class CategoryPagerAdapter extends FragmentStatePagerAdapter {
    private int mTabCount;
    private boolean updateCurrentTab;

    public CategoryPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.mTabCount = count;
    }

    @Override
    public Fragment getItem(int position) {
        return CategoryFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Timber.d("[instantiateItem] " + position);
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        Timber.d("[destroyItem] tab = " + position);
    }

    @Override
    public int getItemPosition(Object object) {
        if (this.updateCurrentTab) {
            return POSITION_NONE;
        }
        CategoryFragment fragment = (CategoryFragment) object;
        int index = PrefUtils.getInt(PrefUtils.PREFS_KEY_SELECTED_TAB, -1);
        if (index != -1) {
            if (index != fragment.getIndex()) {
                return POSITION_NONE;
            } else {
                return POSITION_UNCHANGED;
            }
        }
        return super.getItemPosition(object);
    }

    public void setUpdateCurrentTab(boolean updateCurrentTab) {
        this.updateCurrentTab = updateCurrentTab;
    }
}
