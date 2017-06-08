package xiazdong.me.fragmentdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xiazdong.me.fragmentdemo.db.CategoryMetaData;
import xiazdong.me.fragmentdemo.loader.CategoryLoader;
import xiazdong.me.fragmentdemo.util.PrefUtils;
import xiazdong.me.fragmentdemo.viewpager.CategoryFragment;
import xiazdong.me.fragmentdemo.viewpager.CategoryPagerAdapter;
import xiazdong.me.fragmentdemo.viewpager.MaterialFragment;

public class Demo3Activity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener, View.OnClickListener {

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mPager;
    @BindView(R.id.text)
    TextView mNoneView;

    private CategoryLoader mCategoryLoader;
    private CategoryPagerAdapter mCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);
        ButterKnife.bind(this);
        mTabLayout.addOnTabSelectedListener(this);
        mPager.addOnPageChangeListener(this);
        mNoneView.setOnClickListener(this);
        loadTabCategory();
    }

    private void loadTabCategory() {
        mCategoryLoader = new CategoryLoader(this);
        mCategoryLoader.setOnCategoryLoadedListener(new CategoryLoader.OnCategoryLoadedListener() {
            @Override
            public void onLoadFinished(List<CategoryMetaData> datas) {
                setTabLayout(datas);
            }
        });
        mCategoryLoader.start();
    }

    private void setTabLayout(List<CategoryMetaData> datas) {
        for (CategoryMetaData data : datas) {
            TabLayout.Tab tab = mTabLayout.newTab();
            View customView = LayoutInflater.from(this).inflate(R.layout.tab_custom, null);
            TextView text = (TextView) customView.findViewById(R.id.text);
            text.setText(data.name);
            tab.setCustomView(customView);
            mTabLayout.addTab(tab, false);
        }
        mCategoryAdapter = new CategoryPagerAdapter(getSupportFragmentManager(), datas.size());
        mPager.setAdapter(mCategoryAdapter);
        if (mTabLayout.getTabCount() > 0) {
            mTabLayout.getTabAt(1).select();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mPager.setCurrentItem(tab.getPosition());
        mCategoryAdapter.setCurrentTabIndex(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTabLayout.getTabAt(position).select();
        mCategoryAdapter.setCurrentTabIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void updateCategoryViewPager(int tabIndex) {
        mCategoryAdapter.setUpdateFlag(tabIndex);
        mCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int currentTabIndex = mPager.getCurrentItem();
        int tabIndex = PrefUtils.getInt(PrefUtils.PREFS_KEY_SELECTED_TAB, -1);
        int pageIndex = PrefUtils.getInt(PrefUtils.PREFS_KEY_SELECTED_PAGE, -1);
        int materialId = PrefUtils.getInt(PrefUtils.PREFS_KEY_SELECTED_MATERIAL, -1);
        if (tabIndex == -1) {
            return;
        }
        clearSelectedPreference();
        CategoryFragment currentFragment = (CategoryFragment) mCategoryAdapter.instantiateItem(mPager, currentTabIndex);
        int currentPageIndex = currentFragment.getCurrentPageIndex();
        if (currentTabIndex >= 2) {
            updateWhenTabLargerThan1(currentFragment, currentTabIndex, currentPageIndex, tabIndex, pageIndex, materialId);
        } else if (currentTabIndex == 1){
            updateWhenTabIs1(currentFragment, currentPageIndex, tabIndex, pageIndex, materialId);
        } else {
            updateWhenTabIs0(currentFragment, currentPageIndex, tabIndex, materialId);
        }
        mNoneView.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    public void clearSelectedPreference() {
        PrefUtils.remove(PrefUtils.PREFS_KEY_SELECTED_TAB);
        PrefUtils.remove(PrefUtils.PREFS_KEY_SELECTED_PAGE);
        PrefUtils.remove(PrefUtils.PREFS_KEY_SELECTED_MATERIAL);
    }

    public int getCurrentTabIndex() {
        return mPager.getCurrentItem();
    }

    public void updateNoneView() {
        mNoneView.setTextColor(getResources().getColor(android.R.color.black));
    }

    public void updateWhenTabLargerThan1(CategoryFragment fragment, int currentTabIndex, int currentPageIndex, int tabIndex, int pageIndex, int materialId) {
        if (Math.abs(currentTabIndex - tabIndex) == 1) {
            updateCategoryViewPager(tabIndex);
        } else if (currentTabIndex == tabIndex){
            if (pageIndex != currentPageIndex) {
                fragment.updateMaterialViewPager(currentPageIndex, pageIndex);
            } else {
                MaterialFragment mFragment = fragment.getMaterialFragment(pageIndex);
                mFragment.updateItem(materialId);
            }
        }
    }

    public void updateWhenTabIs1(CategoryFragment fragment, int currentPageIndex, int tabIndex, int pageIndex, int materialId) {
        if (tabIndex == 2 || tabIndex == 0) {
            MaterialFragment mFragment = fragment.getMaterialFragment(currentPageIndex);
            mFragment.updateItem(materialId);
            fragment.updateMaterialViewPager(currentPageIndex, -1);
            updateCategoryViewPager(CategoryPagerAdapter.FLAG_UPDATE_LEFT_AND_RIGHT);
        } else if (tabIndex == 1) {
            updateCategoryViewPager(0);
            if (currentPageIndex != pageIndex) {
                fragment.updateMaterialViewPager(currentPageIndex, pageIndex);
            } else {
                MaterialFragment mFragment = fragment.getMaterialFragment(pageIndex);
                mFragment.updateItem(materialId);
            }
        } else {
            updateCategoryViewPager(0);
        }
    }

    public void updateWhenTabIs0(CategoryFragment fragment, int currentPageIndex, int tabIndex, int materialId) {
        MaterialFragment mFragment = fragment.getMaterialFragment(currentPageIndex);
        mFragment.updateItem(materialId);
        fragment.updateMaterialViewPager(currentPageIndex, -1);
        if (tabIndex <= 1) {
            updateCategoryViewPager(CategoryPagerAdapter.FLAG_UPDATE_LEFT_AND_RIGHT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearSelectedPreference();
    }
}
