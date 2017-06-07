package xiazdong.me.fragmentdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import xiazdong.me.fragmentdemo.test.DBTestData;

import static xiazdong.me.fragmentdemo.db.DBContract.DB_NAME;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String SQL_CATEGORY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + DBContract.Category.TABLE_NAME +
            " (" + DBContract.Category.COLUM_NAME_ID + " INTEGER PRIMARY KEY, "
            + DBContract.Category.COLUM_NAME_NAME + " TEXT)";
    private static final String SQL_MATERIAL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + DBContract.Material.TABLE_NAME +
            " (" + DBContract.Material._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DBContract.Material.COLUM_NAME_CID + " INTEGER, "
            + DBContract.Material.COLUM_NAME_NAME + " TEXT, "
            + DBContract.Material.COLUM_NAME_DOWNLOADED + " INTEGER)";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CATEGORY_CREATE_TABLE);
        sqLiteDatabase.execSQL(SQL_MATERIAL_CREATE_TABLE);
    }

    private void insertCategoryData(CategoryMetaData data) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO " + DBContract.Category.TABLE_NAME + " (id, name) VALUES (?,?)";
        db.execSQL(sql, new Object[]{data.id, data.name});
    }

    private void insertMaterialData(MaterialMetaData data) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO " + DBContract.Material.TABLE_NAME + " (cid, name, downloaded) VALUES (?,?,?)";
        db.execSQL(sql, new Object[]{data.cid, data.name, data.downloaded});
    }

    public void insertData() {
        List<CategoryMetaData> cList = DBTestData.Category.genTestData();
        List<MaterialMetaData> mList = DBTestData.Material.genTestData();
        for (CategoryMetaData data : cList) {
            insertCategoryData(data);
        }
        for (MaterialMetaData data : mList) {
            insertMaterialData(data);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql1 = "DROP TABLE IF EXISTS " + DBContract.Category.TABLE_NAME;
        String sql2 = "DROP TABLE IF EXISTS " + DBContract.Material.TABLE_NAME;
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        onCreate(sqLiteDatabase);
    }


}