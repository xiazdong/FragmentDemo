package xiazdong.me.fragmentdemo.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

import timber.log.Timber;
import xiazdong.me.fragmentdemo.db.MaterialMetaData;

/**
 * Created by xiazdong on 17/6/4.
 */

public class MaterialPagerAdapter extends FragmentPagerAdapter {

    public static final int COUNT = 8;
    public static final int COLUMN = 4;

    private int mTabIndex;
    private ArrayList<MaterialMetaData> mData;

    /**
     * mUpdatePageIndex为要更新的page
     * mCurrentPageIndex为当前是哪个page
     * 1、mUpdatePageIndex=-1，那么除了mCurrentPageIndex，其他都更新
     * 2、>=0，那么只更新该索引
     */
    private int mCurrentPageIndex = -1;
    private int mUpdatePageIndex = -1;

    public MaterialPagerAdapter(FragmentManager fm, int tabIndex) {
        super(fm);
        this.mTabIndex = tabIndex;
    }

    public void setSourceData(ArrayList<MaterialMetaData> data) {
        this.mData = data;
    }

    @Override
    public Fragment getItem(int position) {
        return MaterialFragment.newInstance(mTabIndex, position, getMaterials(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MaterialFragment fragment = (MaterialFragment) super.instantiateItem(container, position);
        Timber.d("[instantiateItem], tab = " + mTabIndex + ", page = " + position + ", material = " + getMaterials(position));
        fragment.setSourceData(getMaterials(position));
        return fragment;
    }

    @Override
    public int getCount() {
        if (mData == null || mData.size() == 0) return 0;
        int offset = mData.size() % COUNT;
        int page = mData.size() / COUNT;
        if (offset == 0) {
            return page;
        } else {
            return page + 1;
        }
    }

    public ArrayList<MaterialMetaData> getMaterials(int index) {
        int begin = index * COUNT;
        int end = Math.min(mData.size(), begin + COUNT);
        return new ArrayList<>(mData.subList(begin, end));
    }

    @Override
    public int getItemPosition(Object object) {
        MaterialFragment fragment = (MaterialFragment) object;
        int pageIndex = fragment.getPageIndex();
        if (mUpdatePageIndex == -1) {
            if (mCurrentPageIndex != pageIndex) {
                return POSITION_NONE;
            }
        }
        if (mUpdatePageIndex == pageIndex) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        Timber.d("[destroyItem], tab = " + mTabIndex + ", page = " + position);
    }

    public void setUpdatePage(int currentPageIndex, int pageIndex) {
        this.mCurrentPageIndex = currentPageIndex;
        this.mUpdatePageIndex = pageIndex;
    }

    public MaterialFragment getMaterialFragment(ViewGroup container, int position) {
        return (MaterialFragment) super.instantiateItem(container, position);
    }
}
