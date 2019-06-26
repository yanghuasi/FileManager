package seekbar.ggh.com.file.manager;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import seekbar.ggh.com.myapplication.R;
import utils.FileUtilss;

public class ShareAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ShareAdapter(@Nullable List<String> data) {
        super(R.layout.item_share, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.itemView.findViewById(R.id.iv_share);
        imageView.setImageResource(R.drawable.ic_doc);

//        Glide.with(mContext)
//                .load(FileUtil.PATH_BACKGOUND+item)
//                .into(imageView);
        helper.setText(R.id.tv_share,item);
        Log.e("1","图片路径"+FileUtilss.PATH_BACKGOUND+item);
    }


}