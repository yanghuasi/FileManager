package seekbar.ggh.com.myapplication.manager;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seekbar.ggh.com.myapplication.ImgFolderBean;
import seekbar.ggh.com.myapplication.R;

public class MutilFileAdapter extends BaseQuickAdapter<ImgFolderBean, BaseViewHolder> {

    private Set<Integer> checkStatus;//保证元素不重复
    //控制是否显示Checkbox。true 显示 false 隐藏
    private boolean showCheckBox = false;

    private boolean allCheckBox = false;

    //显示checkbox
    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
    }

    //设置选中状态
    public void setCheckStatus(int pos) {
        checkStatus.add(pos);
    }

    //
    public boolean getShowCheckBox() {
        return showCheckBox;
    }

    //取消选中
    public void removeCheckBoxStuaus(int pos) {
        if (checkStatus.contains(pos)) {
            checkStatus.remove(pos);
        }
    }

    public void setAllCheckBox(boolean setAllCheckBox) {
        this.allCheckBox = allCheckBox;
    }

    public MutilFileAdapter(@Nullable List<ImgFolderBean> data) {
        super(R.layout.item_login_file, data);
        checkStatus = new HashSet<>();
    }


    @Override
    protected void convert(final BaseViewHolder helper, ImgFolderBean item) {
//        ImageView imageView = helper.itemView.findViewById(R.id.img);
//        Glide.with(mContext).load(R.mipmap.ic_launcher).into(imageView);
        if (item.isFile()) {
            helper.setText(R.id.tv_name, item.getName());
            ImageView imageView = helper.itemView.findViewById(R.id.iv_file);
            Glide.with(mContext).load(item.getDir()).into(imageView);

        } else {
            helper.setText(R.id.tv_name, item.getName());
            ImageView imageView = helper.itemView.findViewById(R.id.iv_file);
            imageView.setImageResource(R.drawable.ic_file);
        }
        helper.setVisible(R.id.cb, showCheckBox);//初次进入，隐藏checkbox
        CheckBox checkBox = helper.itemView.findViewById(R.id.cb);
        //当前进入选中状态，且当前item是被选中
        //getLayoutPosition获取当前item的position
        //boolean contains(Object o);判断集合中是否存在某个元素
        if (showCheckBox && checkStatus.contains(helper.getLayoutPosition())) {
            checkBox.setChecked(true);//勾选
        }else {
            checkBox.setChecked(false);//不勾选
        }

    }


}
