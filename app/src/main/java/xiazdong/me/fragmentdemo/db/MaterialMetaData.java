package xiazdong.me.fragmentdemo.db;

import android.database.Cursor;

/**
 * Created by xiazdong on 17/6/4.
 */

public final class MaterialMetaData {
    public int cid;
    public String name;
    public int downloaded;
    public MaterialMetaData(int cid, String name, int downloaded) {
        this.cid = cid;
        this.name = name;
        this.downloaded = downloaded;
    }
    public static MaterialMetaData load(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DBContract.Material.COLUM_NAME_CID));
        String name = cursor.getString(cursor.getColumnIndex(DBContract.Material.COLUM_NAME_NAME));
        int downloaded = cursor.getInt(cursor.getColumnIndex(DBContract.Material.COLUM_NAME_DOWNLOADED));
        return new MaterialMetaData(id, name, downloaded);
    }
}
