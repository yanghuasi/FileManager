package seekbar.ggh.com.file.classify.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.io.File;
import java.util.List;
import seekbar.ggh.com.file.R;
import seekbar.ggh.com.file.bean.Song;


public class AudioAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {
    public AudioAdapter(@Nullable List<Song> data) {
        super(R.layout.item_song, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Song item) {
        File file = new File(item.getFileUrl());
        if (file.isFile()) {
            helper.setText(R.id.file_name_text, item.getFileName());
            helper.setText(R.id.file_length_text, item.getSize());
        helper.setText(R.id.file_date_added_text, item.getYear());
            ImageView imageView = (ImageView) helper.itemView.findViewById(R.id.imageView);
//            Glide.with( mContext ).load( R.drawable.ic_music ).asBitmap().into( imageView ) ;
            imageView.setImageResource(R.drawable.ic_music);
//            Glide.with(mContext).load(item.getAlbum()).into(imageView);
        }else {
            helper.setText(R.id.file_name_text, "未知名");
//            helper.setText(R.id.file_length_text, item.getDuration());
//        helper.setText(R.id.file_date_added_text, item.getDuration());
            ImageView imageView = (ImageView) helper.itemView.findViewById(R.id.imageView);
//            Glide.with( mContext ).load( R.drawable.ic_unknow ).asBitmap().into( imageView ) ;
            imageView.setImageResource(R.drawable.ic_unknow);
//            Glide.with(mContext).load(R.drawable.ic_unknow).into(imageView);
        }
    }
}