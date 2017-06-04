package xiazdong.me.fragmentdemo.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import timber.log.Timber;

/**
 * Created by xiazdong on 17/6/4.
 */

public class CategoryPagerAdapter extends FragmentStatePagerAdapter {
    private int mTabCount;

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
        Timber.d("[destroyItem] " + position);
    }
}
