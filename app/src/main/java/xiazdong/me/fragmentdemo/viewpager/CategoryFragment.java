package xiazdong.me.fragmentdemo.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xiazdong.me.fragmentdemo.R;

/**
 * Created by xiazdong on 17/6/4.
 */

public class CategoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category, container, false);
    }

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }
}
