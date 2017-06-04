package xiazdong.me.fragmentdemo.viewpager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;
import xiazdong.me.fragmentdemo.R;
import xiazdong.me.fragmentdemo.db.MaterialMetaData;
import xiazdong.me.fragmentdemo.loader.MaterialLoader;

/**
 * Created by xiazdong on 17/6/4.
 */

public class CategoryFragment extends Fragment {
    private static final String ARG_KEY_POSITION = "position";
    private Activity mActivity;
    private int mPosition;
    private MaterialLoader mMaterialLoader;

    private ViewPager mPager;
    private CirclePageIndicator mIndicator;
    private MaterialPagerAdapter mMaterialAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
        mPosition = getArguments().getInt(ARG_KEY_POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.category, container, false);
        mPager = (ViewPager) root.findViewById(R.id.pager);
        mIndicator = (CirclePageIndicator) root.findViewById(R.id.indicator);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMaterialLoader = new MaterialLoader(this, mPosition);
        mMaterialLoader.setOnMaterialLoadedListener(new MaterialLoader.OnMaterialLoadedListener() {
            @Override
            public void onLoadFinished(ArrayList<MaterialMetaData> datas) {
                mMaterialAdapter = new MaterialPagerAdapter(getChildFragmentManager(), mPosition, datas);
                mPager.setAdapter(mMaterialAdapter);
                mIndicator.setViewPager(mPager);
            }
        });
        mMaterialLoader.start();
    }

    public static CategoryFragment newInstance(int position) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_KEY_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }
}
