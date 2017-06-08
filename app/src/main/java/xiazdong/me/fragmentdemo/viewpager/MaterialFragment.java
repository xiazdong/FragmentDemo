package xiazdong.me.fragmentdemo.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;

import timber.log.Timber;
import xiazdong.me.fragmentdemo.Demo3Activity;
import xiazdong.me.fragmentdemo.R;
import xiazdong.me.fragmentdemo.db.DBOperator;
import xiazdong.me.fragmentdemo.db.MaterialMetaData;
import xiazdong.me.fragmentdemo.util.PrefUtils;

/**
 * Created by xiazdong on 17/6/4.
 */

public class MaterialFragment extends Fragment {

    private static final String ARG_KEY_LIST = "material_in_page";
    private static final String ARG_KEY_TAB_INDEX = "tab_index";
    private static final String ARG_KEY_PAGE_INDEX = "page_index";

    private int mTabIndex;
    private int mPageIndex;
    private ArrayList<MaterialMetaData> mData;

    private RecyclerView mRecyclerView;
    private MaterialAdapter mAdapter;
    private Demo3Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Demo3Activity) context;
        mTabIndex = getArguments().getInt(ARG_KEY_TAB_INDEX);
        mPageIndex = getArguments().getInt(ARG_KEY_PAGE_INDEX);
        mData = getArguments().getParcelableArrayList(ARG_KEY_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.material, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mAdapter = new MaterialAdapter(R.layout.item_recyclerview, mData);
        Timber.d("[onCreateView] tab = " + mTabIndex + ", page = " + mPageIndex + ", " + mData.toString());
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, MaterialPagerAdapter.COLUMN));
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                int oldTabIndex = PrefUtils.getInt(PrefUtils.PREFS_KEY_SELECTED_TAB, -1);
                int oldPageIndex = PrefUtils.getInt(PrefUtils.PREFS_KEY_SELECTED_PAGE, -1);
                int oldSelectedId = PrefUtils.getInt(PrefUtils.PREFS_KEY_SELECTED_MATERIAL, -1);
                int oldPosition = getPositionByMaterialId(oldSelectedId);

                int tabIndex = getTabIndex();
                int pageIndex = getPageIndex();
                mActivity.updateNoneView();
                if (oldTabIndex == tabIndex && oldPageIndex == pageIndex && oldPosition == position)
                    return;
                updateCurrentPage(adapter, position, oldPosition);
                if (oldTabIndex == -1) {
                    updateWhenNoOldSelected();
                }
                if (tabIndex >= 2) {
                    updateWhenTabLargerThan2(oldTabIndex, oldPageIndex, tabIndex, pageIndex);
                } else if (tabIndex == 1) {
                    updateWhenTabIs1(oldTabIndex, oldPageIndex, tabIndex, pageIndex);
                } else if (tabIndex == 0) {
                    updateWhenTabIs0(oldTabIndex, oldPageIndex, tabIndex, pageIndex);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }

    private void updateCurrentPage(BaseQuickAdapter adapter, int position, int oldPosition) {
        MaterialMetaData data = mData.get(position);
        adapter.notifyItemChanged(position);
        DBOperator.updateMaterialDownloaded(data._id);
        PrefUtils.putInt(PrefUtils.PREFS_KEY_SELECTED_MATERIAL, data._id);
        PrefUtils.putInt(PrefUtils.PREFS_KEY_SELECTED_PAGE, mPageIndex);
        PrefUtils.putInt(PrefUtils.PREFS_KEY_SELECTED_TAB, mTabIndex);
        data.downloaded = 1;
        /**
         * 如果同一页找到了原来选择的元素，那么更新
         */
        if (oldPosition != -1) {
            adapter.notifyItemChanged(oldPosition);
        }
    }

    private void updateWhenNoOldSelected() {
        int tabIndex = getTabIndex();
        /**
         * 如果原来没选择元素
         * 1. 当前tab＝1，那么更新tab0
         * 2. 当前tab＝0，那么更新tab1
         */
        if (tabIndex == 1) {
            mActivity.updateCategoryViewPager(0);
        } else if (tabIndex == 0) {
            mActivity.updateCategoryViewPager(1);
        }
        return;
    }

    private void updateWhenTabLargerThan2(int oldTabIndex, int oldPageIndex, int tabIndex, int pageIndex) {
        CategoryFragment cFragment = (CategoryFragment) getParentFragment();
        /**
         * 1. 原来选择的元素的tab=现在选择的元素tab
         *      如果不在同一页，那么更新old page
         * 2. 如果原来选择的元素tab在相邻页
         *      更新该页
         */
        if (oldTabIndex == tabIndex) {
            if (oldPageIndex != pageIndex) {
                cFragment.updateMaterialViewPager(mPageIndex, oldPageIndex);
            }
        } else if (Math.abs(tabIndex - oldTabIndex) == 1) {
            mActivity.updateCategoryViewPager(oldTabIndex);
        } else if (oldTabIndex == 0) {
            cFragment.updateMaterialViewPager(mPageIndex, -1);
            mActivity.updateCategoryViewPager(CategoryPagerAdapter.FLAG_UPDATE_LEFT_AND_RIGHT);
        }
    }

    private void updateWhenTabIs1(int oldTabIndex, int oldPageIndex, int tabIndex, int pageIndex) {
        CategoryFragment cFragment = (CategoryFragment) getParentFragment();
        /**
         * 1. 如果原来选择的元素tab==现在的tab
         *      更新已下载tab
         *      如果不在当前页，那么刷新那一页
         * 2. 如果原来选择的tab是tab 0
         *      更新除了当前page的其他页
         * 3. 如果原来选择的tab是tab 2
         *      更新左右两个tab
         */
        if (oldTabIndex == tabIndex) {
            mActivity.updateCategoryViewPager(0);
            if (oldPageIndex != pageIndex) {
                cFragment.updateMaterialViewPager(mPageIndex, oldPageIndex);
            }
        } else if (oldTabIndex == 0) {
            cFragment.updateMaterialViewPager(mPageIndex, -1);
            mActivity.updateCategoryViewPager(CategoryPagerAdapter.FLAG_UPDATE_LEFT_AND_RIGHT);
        } else if (oldTabIndex == 2) {
            mActivity.updateCategoryViewPager(CategoryPagerAdapter.FLAG_UPDATE_LEFT_AND_RIGHT);
        } else {
            mActivity.updateCategoryViewPager(0);
        }
    }

    private void updateWhenTabIs0(int oldTabIndex, int oldPageIndex, int tabIndex, int pageIndex) {
        CategoryFragment cFragment = (CategoryFragment) getParentFragment();
        mActivity.updateCategoryViewPager(CategoryPagerAdapter.FLAG_UPDATE_LEFT_AND_RIGHT);
        /**
         * 1. 如果原来选择的tab=当前tab
         *      如果原来选择元素页不是当前页，更新那一页
         * 2. 如果原来选择的tab不是当前tab
         *      更新除了当前页以外的所有页
         */
        if (oldTabIndex == tabIndex) {
            if (oldPageIndex != pageIndex) {
                cFragment.updateMaterialViewPager(mPageIndex, oldPageIndex);
            }
        } else {
            cFragment.updateMaterialViewPager(mPageIndex, -1);
        }
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
        if (id < 0) return -1;
        for (int i = 0; i < mData.size(); i++) {
            MaterialMetaData data = mData.get(i);
            if (data._id == id) {
                return i;
            }
        }
        return -1;
    }

    public void setSourceData(ArrayList<MaterialMetaData> data) {
        this.mData = data;
        if (mRecyclerView != null && mRecyclerView.getAdapter() != null) {
            mAdapter = new MaterialAdapter(R.layout.item_recyclerview, mData);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public int getPageIndex() {
        return mPageIndex;
    }

    public int getTabIndex() {
        return mTabIndex;
    }

    public void updateItem(int materialId) {
        int position = getPositionByMaterialId(materialId);
        if (position != -1) {
            mAdapter.notifyItemChanged(position);
        }
    }
}
