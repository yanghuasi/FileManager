package seekbar.ggh.com.file.manager.add;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import seekbar.ggh.com.file.manager.ImgFolderBean;
import seekbar.ggh.com.myapplication.R;

public class UploadPhotoAdapter extends BaseQuickAdapter<ImgFolderBean, BaseViewHolder> {
    public UploadPhotoAdapter(@Nullable List<ImgFolderBean> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImgFolderBean item) {

            helper.setText(R.id.tv_name,item.getName());
        ImageView imageView = helper.itemView.findViewById (R.id.iv_video);
        Glide.with (mContext).load (item.getDir()).into (imageView);

    }
}