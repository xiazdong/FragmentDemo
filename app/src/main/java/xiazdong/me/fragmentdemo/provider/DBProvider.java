package xiazdong.me.fragmentdemo.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import xiazdong.me.fragmentdemo.db.DBContract;
import xiazdong.me.fragmentdemo.db.DBOperator;

/**
 * Created by xiazdong on 17/6/4.
 */

public class DBProvider extends ContentProvider {

    private String AUTHORITY = ".provider.DBProvider";
    private UriMatcher mMatcher;

    private static final int CODE_CATEGORIES = 0x01;
    private static final int CODE_MATERIALS = 0x02;
    private static final int CODE_MATERIAL_DOWNLOADED = 0x03;

    @Override
    public boolean onCreate() {
        AUTHORITY = getContext().getPackageName() + AUTHORITY;
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(AUTHORITY, DBContract.Category.TABLE_NAME, CODE_CATEGORIES);
        mMatcher.addURI(AUTHORITY, DBContract.Material.TABLE_NAME, CODE_MATERIALS);
        mMatcher.addURI(AUTHORITY, DBContract.Material.TABLE_NAME + "/downloaded", CODE_MATERIAL_DOWNLOADED);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (mMatcher.match(uri)) {
            case CODE_CATEGORIES:
                return DBOperator.queryCategories();
            case CODE_MATERIALS:
                return DBOperator.queryMaterials(selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknow URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int code = mMatcher.match(uri);
        switch (code) {
            case CODE_CATEGORIES:
                return DBContract.Category.ITEM_TYPE;
            case CODE_MATERIALS:
            case CODE_MATERIAL_DOWNLOADED:
                return DBContract.Material.ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknow URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (mMatcher.match(uri)) {
            case CODE_MATERIAL_DOWNLOADED:
                return DBOperator.updateMaterialDownloaded(values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknow URI: " + uri);
        }
    }
}
