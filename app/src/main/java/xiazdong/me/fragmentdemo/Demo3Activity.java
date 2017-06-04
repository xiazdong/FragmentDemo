package xiazdong.me.fragmentdemo;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import xiazdong.me.fragmentdemo.db.CategoryMetaData;
import xiazdong.me.fragmentdemo.loader.CategoryLoader;
import xiazdong.me.fragmentdemo.viewpager.CategoryPagerAdapter;

public class Demo3Activity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mPager;

    private CategoryLoader mCategoryLoader;
    private CategoryPagerAdapter mCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);
        ButterKnife.bind(this);
        mCategoryLoader = new CategoryLoader(this);
        mCategoryLoader.setOnCategoryLoadedListener(new CategoryLoader.OnCategoryLoadedListener() {
            @Override
            public void onLoadFinished(List<CategoryMetaData> datas) {
                setTabLayout(datas);
            }
        });
        mCategoryLoader.start();
        mTabLayout.addOnTabSelectedListener(this);
        mPager.addOnPageChangeListener(this);

    }

    private void setTabLayout(List<CategoryMetaData> datas) {
        for(CategoryMetaData data : datas) {
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
            mTabLayout.getTabAt(0).select();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTabLayout.getTabAt(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
