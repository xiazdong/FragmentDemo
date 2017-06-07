package xiazdong.me.fragmentdemo.loader;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.ArrayList;

import xiazdong.me.fragmentdemo.config.GlobalContext;
import xiazdong.me.fragmentdemo.db.DBOperator;
import xiazdong.me.fragmentdemo.db.MaterialMetaData;

/**
 * Created by xiazdong on 17/6/4.
 */

public class MaterialLoader implements ILoader, LoaderManager.LoaderCallbacks<Cursor> {
    private int mCategoryId;
    private Fragment mFragment;

    private OnMaterialLoadedListener mListener;

    public MaterialLoader(Fragment fragment, int cid) {
        this.mFragment = fragment;
        mCategoryId = cid;
    }

    public void setOnMaterialLoadedListener(OnMaterialLoadedListener listener) {
        this.mListener = listener;
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
        int id = loader.getId();
        ArrayList<MaterialMetaData> list = null;
        if (id == LOADER_MATERIAL_ID) {
            list = processMaterial(data);
        }
        if (mListener != null) {
            mListener.onLoadFinished(list);
        }
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

    public ArrayList<MaterialMetaData> processMaterial(Cursor cursor) {
        ArrayList<MaterialMetaData> materialList = new ArrayList<>();
        while (cursor.moveToNext()) {
            materialList.add(MaterialMetaData.load(cursor));
        }
        return materialList;
    }

    public interface OnMaterialLoadedListener {
        void onLoadFinished(ArrayList<MaterialMetaData> datas);
    }
}
