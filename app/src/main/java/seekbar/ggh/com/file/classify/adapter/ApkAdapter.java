package seekbar.ggh.com.file.classify.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import seekbar.ggh.com.file.bean.Music;
import seekbar.ggh.com.myapplication.R;

public class ApkAdapter extends BaseQuickAdapter<Music, BaseViewHolder> {
    public ApkAdapter(@Nullable List<Music> data) {
        super(R.layout.item_song, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Music item) {

        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.file_length_text,item.getDuration());
//        helper.setText(R.id.file_date_added_text, item.getDuration());
        ImageView imageView = helper.itemView.findViewById (R.id.imageView);
        Glide.with (mContext).load (R.drawable.ic_music).into (imageView);

    }
}