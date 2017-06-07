package xiazdong.me.fragmentdemo.db;

import android.database.Cursor;

/**
 * Created by xiazdong on 17/6/4.
 */

public final class CategoryMetaData {
    public int id;
    public String name;

    public CategoryMetaData(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CategoryMetaData load(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DBContract.Category.COLUM_NAME_ID));
        String name = cursor.getString(cursor.getColumnIndex(DBContract.Category.COLUM_NAME_NAME));
        return new CategoryMetaData(id, name);
    }

    @Override
    public String toString() {
        return "[" + id + "," + name + "]";
    }
}
