package xiazdong.me.fragmentdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import xiazdong.me.fragmentdemo.fragment.LazyFragment;

/**
 * Created by xiazdong on 17/6/10.
 */

public class LazyPagerAdapter extends FragmentPagerAdapter {
    private String[] tabs = {"微信", "通讯录", "发现", "我"};
    public LazyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return LazyFragment.newInstance(tabs[position]);
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
