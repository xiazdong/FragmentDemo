package xiazdong.me.fragmentdemo.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import xiazdong.me.fragmentdemo.db.CategoryMetaData;
import xiazdong.me.fragmentdemo.db.MaterialMetaData;

/**
 * 生成测试数据
 * Created by xiazdong on 17/6/4.
 */

public final class DBTestData {
    public static class Category {
        private static String[] names = {"已下载", "变妆", "动物", "氛围", "变脸"};

        private static CategoryMetaData getMetaData(int id, String name) {
            return new CategoryMetaData(id, name);
        }

        public static List<CategoryMetaData> genTestData() {
            ArrayList<CategoryMetaData> list = new ArrayList<>();
            for (int i = 0; i < names.length; i++) {
                list.add(getMetaData(i, names[i]));
            }
            return list;
        }
    }

    public static class Material {
        private static int[] count = {0, 20, 15, 15, 15};

        private static MaterialMetaData getMetaData(int cid, String name, int downloaded) {
            return new MaterialMetaData(cid, name, downloaded);
        }

        public static List<MaterialMetaData> genTestData() {
            ArrayList<MaterialMetaData> list = new ArrayList<>();
            for (int i = 0; i < count.length; i++) {
                for (int j = 0; j < count[i]; j++) {
                    String name = i + "-" + j;
                    int downloaded = 0;
                    list.add(getMetaData(i, name, downloaded));
                }
            }
            return list;
        }
    }
}
