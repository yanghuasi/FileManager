package seekbar.ggh.com.file.dialogfragment;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

import seekbar.ggh.com.file.R;
import utils.FileUtilss;

public class BackgoundAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public BackgoundAdapter(@Nullable List<String> data) {
        super(R.layout.item_dialog_asset, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.itemView.findViewById(R.id.iv_file);

        Bitmap bitmap = FileUtilss.getAssetsBitmap(mContext,"background/"+item);
        imageView.setImageBitmap(bitmap);

//        Glide.with(mContext)
//                .load(FileUtil.PATH_BACKGOUND+item)
//                .into(imageView);
        helper.setText(R.id.tv_name,item);
        Log.e("1","图片路径"+FileUtilss.PATH_BACKGOUND+item);
    }


}