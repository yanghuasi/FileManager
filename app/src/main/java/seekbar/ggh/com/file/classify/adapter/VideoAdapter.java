package seekbar.ggh.com.file.classify.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import seekbar.ggh.com.file.R;
import seekbar.ggh.com.file.bean.MMM;
import seekbar.ggh.com.file.bean.Video;

import utils.VideoUtils;

public class VideoAdapter extends BaseQuickAdapter<Video, BaseViewHolder> {
    public VideoAdapter(@Nullable List<Video> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Video item) {

            helper.setText(R.id.tv_name,item.getName());
        ImageView imageView = (ImageView) helper.itemView.findViewById (R.id.iv_video);
//        Glide.with (mContext).load (VideoUtils.getVideoThumbnail(item.getId())).into (imageView);
        //加载缩略图
        Glide.with( mContext ).load( item.getPath() ).thumbnail(0.1f).into( imageView ) ;
    }
}