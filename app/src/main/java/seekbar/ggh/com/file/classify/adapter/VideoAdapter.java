package seekbar.ggh.com.file.classify.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import seekbar.ggh.com.file.bean.Video;
import seekbar.ggh.com.myapplication.R;

public class VideoAdapter extends BaseQuickAdapter<Video, BaseViewHolder> {
    public VideoAdapter(@Nullable List<Video> data) {
        super(R.layout.item_song, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Video item) {

            helper.setText(R.id.file_name_text,item.getName());
        ImageView imageView = helper.itemView.findViewById (R.id.imageView);
        Glide.with (mContext).load (R.drawable.ic_video).into (imageView);

    }
}