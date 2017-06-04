package xiazdong.me.fragmentdemo.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
        return CategoryFragment.newInstance();
    }

    @Override
    public int getCount() {
        return mTabCount;
    }
}
