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
    /**
     * mUpdateFlag决定了对某个Fragment，getItemPosition返回的是POSITION_NONE还是POSITION_UNCHANGED
     * 1. FLAG_UPDATE_LEFT_AND_RIGHT: 只更新mCurrentTabIndex相邻的tab
     * 2. FLAG_UPDATE_ALL: 更新所有tab
     * 3. >=0的整数，只更新这个索引的tab
     *
     * 所以每次要调用adapter.notifyDataSetChanged()之前要调用setUpdateFlag()设置更新策略。
     */
    private int mUpdateFlag = -3;
    private int mCurrentTabIndex;  //始终为当前滑到的tab索引

    public static final int FLAG_UPDATE_LEFT_AND_RIGHT = -1;

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
        CategoryFragment fragment = (CategoryFragment) object;
        if (mUpdateFlag == FLAG_UPDATE_LEFT_AND_RIGHT) {
            if (mCurrentTabIndex != fragment.getIndex()) {
                return POSITION_NONE;
            }
        } else {
            if (mUpdateFlag == fragment.getIndex()) {
                return POSITION_NONE;
            }
        }
        return super.getItemPosition(object);
    }

    public void setUpdateFlag(int flag) {
        this.mUpdateFlag = flag;
    }

    public void setCurrentTabIndex(int tabIndex) {
        this.mCurrentTabIndex = tabIndex;
    }
}
