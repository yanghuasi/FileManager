package seekbar.ggh.com.file.classify.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import seekbar.ggh.com.file.R;
import seekbar.ggh.com.file.manager.ImgFolderBean;
import utils.FileManager;

public class DocAdapter extends BaseQuickAdapter<ImgFolderBean, BaseViewHolder> {
    public DocAdapter(@Nullable List<ImgFolderBean> data) {
        super(R.layout.item_doc, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImgFolderBean item) {
//        String name = FileManager.extractFileName(item.path);

//        try {
            helper.setText(R.id.tv_name, item.getName());
            helper.setText(R.id.file_date_added_text,FileManager.fileModifyTime(item.getDir()));
        helper.setText(R.id.file_length_text, FileManager.getFileSize(item.getDir()).toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        ImageView imageView = helper.itemView.findViewById(R.id.imageView);
//        Glide.with(mContext).load(R.drawable.ic_doc).into(imageView);
        imageView.setImageResource(R.drawable.ic_doc);
    }
}