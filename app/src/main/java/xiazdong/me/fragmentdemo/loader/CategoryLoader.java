package xiazdong.me.fragmentdemo.loader;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.List;

import xiazdong.me.fragmentdemo.config.GlobalContext;
import xiazdong.me.fragmentdemo.db.CategoryMetaData;
import xiazdong.me.fragmentdemo.db.DBOperator;

/**
 * Created by xiazdong on 17/6/4.
 */

public class CategoryLoader implements ILoader, LoaderManager.LoaderCallbacks<Cursor> {

    private FragmentActivity mActivity;
    private OnCategoryLoadedListener mListener;

    public interface OnCategoryLoadedListener {
        void onLoadFinished(List<CategoryMetaData> datas);
    }

    public CategoryLoader(FragmentActivity activity) {
        mActivity = activity;
    }

    public void setOnCategoryLoadedListener(OnCategoryLoadedListener listener) {
        this.mListener = listener;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = null;
        if (id == LOADER_CATEGORY_ID) {
            loader = DBOperator.loadCategorys(GlobalContext.getContext());
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int id = loader.getId();
        if (id == LOADER_CATEGORY_ID) {
            List<CategoryMetaData> list = processCategory(data);
            if (mListener != null) {
                mListener.onLoadFinished(list);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private List<CategoryMetaData> processCategory(Cursor cursor) {
        List<CategoryMetaData> categoryList = new ArrayList<>();
        while (cursor.moveToNext()) {
            categoryList.add(CategoryMetaData.load(cursor));
        }
        return categoryList;
    }

    @Override
    public void start() {
        mActivity.getSupportLoaderManager().restartLoader(LOADER_CATEGORY_ID, null, this);
    }

    @Override
    public void destroy() {
    }
}
