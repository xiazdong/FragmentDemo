package xiazdong.me.fragmentdemo.viewpager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;

import timber.log.Timber;
import xiazdong.me.fragmentdemo.R;
import xiazdong.me.fragmentdemo.config.GlobalContext;
import xiazdong.me.fragmentdemo.db.MaterialMetaData;
import xiazdong.me.fragmentdemo.util.PrefUtils;

/**
 * Created by xiazdong on 17/6/4.
 */

public class MaterialFragment extends Fragment {

    public static final String PREFS_KEY_SELECTED_MATERIAL = "selected_material";

    private static final String ARG_KEY_LIST = "material_in_page";
    private static final String ARG_KEY_TAB_INDEX = "tab_index";
    private static final String ARG_KEY_PAGE_INDEX = "page_index";

    private int mTabIndex;
    private int mPageIndex;
    private ArrayList<MaterialMetaData> mData;

    private RecyclerView mRecyclerView;
    private MaterialAdapter mAdapter;
    private Context mContext;
    private SharedPreferences mSf;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mTabIndex = getArguments().getInt(ARG_KEY_TAB_INDEX);
        mPageIndex = getArguments().getInt(ARG_KEY_PAGE_INDEX);
        mData = getArguments().getParcelableArrayList(ARG_KEY_LIST);
        Timber.d(mData.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.material, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
        mAdapter = new MaterialAdapter(R.layout.item_recyclerview, mData);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, MaterialPagerAdapter.COLUMN));
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                MaterialMetaData data = mData.get(position);
                int oldSelectedId = PrefUtils.getInt(PrefUtils.PREFS_KEY_SELECTED_MATERIAL, -1);
                int oldPosition = getPositionByMaterialId(oldSelectedId);
                if (oldSelectedId != data._id) {
                    PrefUtils.putInt(PrefUtils.PREFS_KEY_SELECTED_MATERIAL, data._id);
                    adapter.notifyItemChanged(oldPosition);
                    adapter.notifyItemChanged(position);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }

    public static MaterialFragment newInstance(int tabIndex, int pageIndex, ArrayList<MaterialMetaData> data) {
        MaterialFragment fragment = new MaterialFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_KEY_TAB_INDEX, tabIndex);
        bundle.putInt(ARG_KEY_PAGE_INDEX, pageIndex);
        bundle.putParcelableArrayList(ARG_KEY_LIST, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int getPositionByMaterialId(int id) {
        for(int i=0; i < mData.size(); i++) {
            MaterialMetaData data = mData.get(i);
            if (data._id == id) {
                return i;
            }
        }
        return -1;
    }
}
