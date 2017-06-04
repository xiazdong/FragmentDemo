package xiazdong.me.fragmentdemo.viewpager;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import xiazdong.me.fragmentdemo.R;
import xiazdong.me.fragmentdemo.db.MaterialMetaData;

/**
 * Created by xiazdong on 17/6/4.
 */

public class MaterialAdapter extends BaseQuickAdapter<MaterialMetaData, BaseViewHolder> {
    public MaterialAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, MaterialMetaData item) {
        helper.setText(R.id.text, item.name);
    }
}
