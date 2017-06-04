package xiazdong.me.fragmentdemo.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import xiazdong.me.fragmentdemo.db.MaterialMetaData;

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
    public int getCount() {
        int offset = mData.size() % COUNT;
        int page = mData.size() / COUNT;
        if (offset == 0) {
            return page;
        } else {
            return page + 1;
        }
    }

    private ArrayList<MaterialMetaData> getMaterials(int index) {
        int begin = index * COUNT;
        int end = Math.min(mData.size(), begin + COUNT);
        return new ArrayList<>(mData.subList(begin, end));
    }
}
