package seekbar.ggh.com.file.manager.add;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import seekbar.ggh.com.file.R;
import seekbar.ggh.com.file.manager.ImgFolderBean;
import utils.FileManager;

public class UploadPhotoAdapter extends BaseQuickAdapter<ImgFolderBean, BaseViewHolder> {
    public UploadPhotoAdapter(@Nullable List<ImgFolderBean> data) {
        super(R.layout.item_uploadphoto, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImgFolderBean item) {

        helper.setText(R.id.tv_name, FileManager.extractFileName(item.getDir()));
        //解决下标越界异常
        if (FileManager.getImagePathFromFolder(item.getDir()).size() > 1) {
            ImageView imageView = (ImageView) helper.itemView.findViewById(R.id.image_one);
            Glide.with(mContext).load(FileManager.getImagePathFromFolder(item.getDir()).get(0)).into(imageView);
        }
        if (FileManager.getImagePathFromFolder(item.getDir()).size() > 2) {
            ImageView imageView2 = (ImageView) helper.itemView.findViewById(R.id.image_two);
            Glide.with(mContext).load(FileManager.getImagePathFromFolder(item.getDir()).get(1)).into(imageView2);
        }
        if (FileManager.getImagePathFromFolder(item.getDir()).size() > 3) {
            ImageView imageView3 = (ImageView) helper.itemView.findViewById(R.id.image_three);
            Glide.with(mContext).load(FileManager.getImagePathFromFolder(item.getDir()).get(2)).into(imageView3);
        }
        if (FileManager.getImagePathFromFolder(item.getDir()).size() > 4) {
            ImageView imageView4 = (ImageView) helper.itemView.findViewById(R.id.image_four);
            Glide.with(mContext).load(FileManager.getImagePathFromFolder(item.getDir()).get(3)).into(imageView4);
        }
    }
}