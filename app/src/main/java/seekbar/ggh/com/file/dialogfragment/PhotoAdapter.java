package seekbar.ggh.com.file.dialogfragment;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.util.List;

import seekbar.ggh.com.file.R;
import seekbar.ggh.com.file.manager.ImgFolderBean;



public class PhotoAdapter extends BaseQuickAdapter<ImgFolderBean, BaseViewHolder> {
    public PhotoAdapter(@Nullable List<ImgFolderBean> data) {
        super(R.layout.item_dialog_file, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImgFolderBean item) {
//        helper.setText(R.id.iv_file,item.isFile()?"类型：图片":"类型：文件夹");
        //image如果是图片
        if (item.isFile()){
            helper.setText(R.id.tv_name,item.getName());
            ImageView imageView = (ImageView) helper.itemView.findViewById (R.id.iv_file);
            Glide.with (mContext).load (item.getDir()).into (imageView);

        }else {
        helper.setText(R.id.tv_name,item.getName());
        ImageView imageView = (ImageView) helper.itemView.findViewById (R.id.iv_file);
        imageView.setImageResource(R.drawable.ic_file);
       // Glide.with (mContext).load (R.drawable.ic_file).asBitmap().into (imageView);
        }
    }
}