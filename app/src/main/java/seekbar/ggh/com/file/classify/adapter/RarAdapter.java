package seekbar.ggh.com.file.classify.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import seekbar.ggh.com.file.bean.Video;
import seekbar.ggh.com.file.manager.ImgFolderBean;
import seekbar.ggh.com.myapplication.R;
import utils.FileManager;

public class RarAdapter extends BaseQuickAdapter<ImgFolderBean, BaseViewHolder> {
    public RarAdapter(@Nullable List<ImgFolderBean> data) {
        super(R.layout.item_doc, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImgFolderBean item) {


        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.file_date_added_text, FileManager.fileModifyTime(item.getDir()));
        helper.setText(R.id.file_length_text, FileManager.getFileSize(item.getDir()).toString());
        ImageView imageView = helper.itemView.findViewById(R.id.imageView);
//        Glide.with(mContext).load(R.drawable.ic_doc).into(imageView);
        imageView.setImageResource(R.drawable.ic_doc);

    }
}