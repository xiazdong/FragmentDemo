package xiazdong.me.fragmentdemo.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import xiazdong.me.fragmentdemo.config.GlobalContext;

/**
 * Created by xiazdong on 17/6/4.
 */

public class DBOperator {
    private static DBHelper mDBHelper = new DBHelper(GlobalContext.getContext());

    public static void insertData() {
        mDBHelper.insertData();
    }

    public static Loader<Cursor> loadCategorys(Context context) {
        return new CursorLoader(context, DBContract.Category.CONTENT_URI, null, null, null, null);
    }

    public static Loader<Cursor> loadMaterials(Context ctx, int cid) {
        return new CursorLoader(ctx, DBContract.Material.CONTENT_URI, null, "cid = ?", new String[]{String.valueOf(cid)}, null);
    }

    public static Loader<Cursor> loadDownloadedMaterials(Context ctx) {
        return new CursorLoader(ctx, DBContract.Material.CONTENT_URI, null, "downloaded = 1", null, null);
    }

    public static void updateMaterialDownloaded(int id) {
        ContentResolver resolver = GlobalContext.getContext().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(DBContract.Material.COLUM_NAME_DOWNLOADED, 1);
        resolver.update(DBContract.Material.CONTENT_URI_DOWNLOADED, values, DBContract.Material._ID + " = " + id, null);
    }


    public static Cursor queryCategories() {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        builder.setTables(DBContract.Category.TABLE_NAME);
        return builder.query(db, null, null, null, null, null, null);
    }

    public static Cursor queryMaterials(String selection, String[] selectArgs) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        builder.setTables(DBContract.Material.TABLE_NAME);
        return builder.query(db, null, selection, selectArgs, null, null, null);
    }

    public static int updateMaterialDownloaded(ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        return db.update(DBContract.Material.TABLE_NAME, values, selection, selectionArgs);
    }
}
