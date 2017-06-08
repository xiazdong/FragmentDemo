package xiazdong.me.fragmentdemo.viewpager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import timber.log.Timber;
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
        int position = helper.getLayoutPosition();
        helper.setText(R.id.text, item.name);
        TextView text = helper.getView(R.id.text);
        ImageView download = helper.getView(R.id.download);
        int selectedId = PrefUtils.getInt(PrefUtils.PREFS_KEY_SELECTED_MATERIAL, -1);
        MaterialMetaData data = mData.get(position);
        Timber.d("convert " + data.name);
        if (data._id == selectedId) {
            download.setVisibility(View.GONE);
            text.setTextColor(GlobalContext.getContext().getResources().getColor(R.color.colorAccent));
        } else {
            download.setImageResource(R.mipmap.ic_download);
            download.setVisibility(data.downloaded == 1 ? View.GONE : View.VISIBLE);
            text.setTextColor(GlobalContext.getContext().getResources().getColor(android.R.color.black));
        }
    }
}

