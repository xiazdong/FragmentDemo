package xiazdong.me.fragmentdemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;

public class LazyActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    PageBottomTabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy);
        ButterKnife.bind(this);
        LazyPagerAdapter adapter = new LazyPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);
        mPager.setOffscreenPageLimit(3);
        NavigationController navigationController = mTabLayout.custom()
                .addItem(newItem(R.drawable.weixin_2_normal, R.drawable.weixin_2_selected, "微信"))
                .addItem(newItem(R.drawable.weixin_2_normal, R.drawable.weixin_2_selected, "通信录"))
                .addItem(newItem(R.drawable.weixin_2_normal, R.drawable.weixin_2_selected, "发现"))
                .addItem(newItem(R.drawable.weixin_2_normal, R.drawable.weixin_2_selected, "我"))
                .build();
        navigationController.setupWithViewPager(mPager);

    }

    private NormalItemView newItem(int drawable, int checkdrawable, String text) {
        NormalItemView itemView = new NormalItemView(this);
        itemView.initialize(drawable, checkdrawable, text);
        itemView.setTextDefaultColor(0xff000000);
        itemView.setTextCheckedColor(0xff4CAF50);
        return itemView;
    }
}
