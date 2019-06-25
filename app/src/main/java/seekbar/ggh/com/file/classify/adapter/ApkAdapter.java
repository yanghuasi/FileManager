package seekbar.ggh.com.file.classify.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import seekbar.ggh.com.file.bean.AppInfo;
import seekbar.ggh.com.file.bean.FileBean;
import seekbar.ggh.com.file.bean.Music;
import seekbar.ggh.com.file.manager.ImgFolderBean;
import seekbar.ggh.com.myapplication.R;

public class ApkAdapter extends BaseQuickAdapter<AppInfo, BaseViewHolder> {
    public ApkAdapter(@Nullable List<AppInfo> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppInfo item) {

        helper.setText(R.id.tv_name,item.getApkName());
//        helper.setText(R.id.file_length_text,item.getDuration());
//        helper.setText(R.id.file_date_added_text, item.getDuration());
        ImageView imageView = helper.itemView.findViewById (R.id.iv_video);
//        Glide.with (mContext).load (item.getIcon()).into (imageView);
        imageView.setImageDrawable(item.getIcon());
//        Glide.with( mContext ).load( item.getIcon()).asBitmap().into( imageView ) ;

    }
}