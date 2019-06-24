package seekbar.ggh.com.file.classify.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import seekbar.ggh.com.file.bean.FileBean;
import seekbar.ggh.com.file.bean.Song;
import seekbar.ggh.com.myapplication.R;
import utils.FileManager;

public class DocAdapter extends BaseQuickAdapter<FileBean, BaseViewHolder> {
    public DocAdapter(@Nullable List<FileBean> data) {
        super(R.layout.item_song, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileBean item) {
        String name = FileManager.extractFileName(item.path);
        helper.setText(R.id.tv_name, name);
        ImageView imageView = helper.itemView.findViewById(R.id.imageView);
        Glide.with(mContext).load(R.drawable.ic_doc).into(imageView);

    }
}