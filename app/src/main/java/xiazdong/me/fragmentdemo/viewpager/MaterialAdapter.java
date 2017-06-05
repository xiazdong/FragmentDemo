package xiazdong.me.fragmentdemo.viewpager;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import xiazdong.me.fragmentdemo.R;
import xiazdong.me.fragmentdemo.config.GlobalContext;
import xiazdong.me.fragmentdemo.db.MaterialMetaData;
import xiazdong.me.fragmentdemo.util.PrefUtils;

/**
 * Created by xiazdong on 17/6/4.
 */

public class MaterialAdapter extends BaseQuickAdapter<MaterialMetaData, BaseViewHolder> {
    public MaterialAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, MaterialMetaData item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.text, item.name);
        TextView text = helper.getView(R.id.text);
        int selectedId = PrefUtils.getInt(PrefUtils.PREFS_KEY_SELECTED_MATERIAL, -1);
        MaterialMetaData data = mData.get(position);
        if (data._id == selectedId) {
            text.setTextColor(GlobalContext.getContext().getResources().getColor(R.color.colorAccent));
        } else {
            text.setTextColor(GlobalContext.getContext().getResources().getColor(android.R.color.black));
        }
    }
}
