package xiazdong.me.fragmentdemo.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;
import xiazdong.me.fragmentdemo.db.MaterialMetaData;
import xiazdong.me.fragmentdemo.util.PrefUtils;

/**
 * Created by xiazdong on 17/6/4.
 */

public class MaterialPagerAdapter extends FragmentPagerAdapter {

    public static final int COUNT = 8;
    public static final int ROW = 2;
    public static final int COLUMN = 4;

    private int mTabIndex;
    private ArrayList<MaterialMetaData> mData;

    public MaterialPagerAdapter(FragmentManager fm, int tabIndex, ArrayList<MaterialMetaData> datas) {
        super(fm);
        this.mTabIndex = tabIndex;
        this.mData = datas;
    }

    @Override
    public Fragment getItem(int position) {
        return MaterialFragment.newInstance(mTabIndex, position, getMaterials(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Timber.d("instantiateItem");
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
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
        int tabIndex = fragment.getTabIndex();
        int selectedTabIndex = PrefUtils.getInt(PrefUtils.PREFS_KEY_SELECTED_TAB, -1);
        int selectedPageIndex = PrefUtils.getInt(PrefUtils.PREFS_KEY_SELECTED_PAGE, -1);
        /**
         * 只刷新当前选择的item的tab下，当前page的左右两个page
         */
        if (selectedTabIndex != -1 && selectedPageIndex != -1 && tabIndex == selectedTabIndex) {
            int left = selectedPageIndex - 1;
            int right = selectedPageIndex + 1;
            if (left == pageIndex || right == pageIndex) {
                return POSITION_NONE;
            }
        }
        return super.getItemPosition(object);
    }
}
