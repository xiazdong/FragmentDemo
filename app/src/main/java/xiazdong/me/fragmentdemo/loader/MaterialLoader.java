package xiazdong.me.fragmentdemo.loader;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import xiazdong.me.fragmentdemo.config.GlobalContext;
import xiazdong.me.fragmentdemo.db.DBOperator;

/**
 * Created by xiazdong on 17/6/4.
 */

public class MaterialLoader implements ILoader, LoaderManager.LoaderCallbacks<Cursor> {
    private int mCategoryId;
    private Fragment mFragment;

    public MaterialLoader(Fragment fragment, int cid) {
        this.mFragment = fragment;
        mCategoryId = cid;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> loader = null;
        if (id == LOADER_MATERIAL_ID) {
            if (mCategoryId != 0) {
                loader = DBOperator.loadMaterials(GlobalContext.getContext(), mCategoryId);
            } else {
                loader = DBOperator.loadDownloadedMaterials(GlobalContext.getContext());
            }
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void start() {
        mFragment.getLoaderManager().restartLoader(LOADER_MATERIAL_ID, null, this);
    }

    @Override
    public void destroy() {

    }
}
