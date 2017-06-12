package xiazdong.me.fragmentdemo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xiazdong.me.fragmentdemo.R;

/**
 * Created by xiazdong on 17/6/10.
 */

public class LazyFragment extends Fragment {
    public static final String ARG_KEY = "key";

    private Context mContext;
    private View mRootView;
    private String mKey;
    private boolean mIsInited;
    private boolean mIsPrepared;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mKey = getArguments().getString(ARG_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_lazy, container, false);
        mIsPrepared = true;
        lazyLoad();
        return mRootView;
    }

    public void lazyLoad() {
        if (getUserVisibleHint() && mIsPrepared && !mIsInited) {
            //异步初始化，在初始化后显示正常UI
            loadData();
        }
    }

    private void loadData() {
        new Thread() {
            public void run() {
                SystemClock.sleep(1000);
                Activity activity = (Activity) mContext;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mRootView != null) {
                            View emptyView = mRootView.findViewById(R.id.empty);
                            emptyView.setVisibility(View.GONE);
                            View container = mRootView.findViewById(R.id.container);
                            TextView text = (TextView) container.findViewById(R.id.text);
                            text.setText(mKey);
                            container.setVisibility(View.VISIBLE);
                        }
                        mIsInited = true;
                    }
                });

            }
        }.start();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
    }

    public static LazyFragment newInstance(String title) {
        LazyFragment fragment = new LazyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_KEY, title);
        fragment.setArguments(bundle);
        return fragment;
    }
}
