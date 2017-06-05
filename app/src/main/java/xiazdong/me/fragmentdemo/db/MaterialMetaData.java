package xiazdong.me.fragmentdemo.db;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xiazdong on 17/6/4.
 */

public final class MaterialMetaData implements Parcelable {
    public int cid;
    public String name;
    public int downloaded;
    public int _id;

    public MaterialMetaData(int cid, String name, int downloaded) {
        this.cid = cid;
        this.name = name;
        this.downloaded = downloaded;
    }

    public MaterialMetaData(int _id, int cid, String name, int downloaded) {
        this._id = _id;
        this.cid = cid;
        this.name = name;
        this.downloaded = downloaded;
    }

    public static MaterialMetaData load(Cursor cursor) {
        int _id = cursor.getInt(cursor.getColumnIndex(DBContract.Material._ID));
        int id = cursor.getInt(cursor.getColumnIndex(DBContract.Material.COLUM_NAME_CID));
        String name = cursor.getString(cursor.getColumnIndex(DBContract.Material.COLUM_NAME_NAME));
        int downloaded = cursor.getInt(cursor.getColumnIndex(DBContract.Material.COLUM_NAME_DOWNLOADED));
        return new MaterialMetaData(_id, id, name, downloaded);
    }

    @Override
    public String toString() {
        return "[" + _id + "," + cid + "," + name + "," + downloaded + "]";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(cid);
        out.writeString(name);
        out.writeInt(downloaded);
        out.writeInt(_id);
    }

    private MaterialMetaData(Parcel in) {
        cid = in.readInt();
        name = in.readString();
        downloaded = in.readInt();
        _id = in.readInt();
    }

    public static final Parcelable.Creator<MaterialMetaData> CREATOR
            = new Parcelable.Creator<MaterialMetaData>() {
        public MaterialMetaData createFromParcel(Parcel in) {
            return new MaterialMetaData(in);
        }

        public MaterialMetaData[] newArray(int size) {
            return new MaterialMetaData[size];
        }
    };

}
