package seekbar.ggh.com.file.dialogfragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import seekbar.ggh.com.myapplication.R;
import utils.ScreenUtil;


/**
 * resume dialog style
 */
public class PhotoSeletorDialog extends DialogFragment implements ImageFilesFragment.OnItemClickListener{
    public OnItemClickListener listener;
    public ChoseImageCallback  mChoseImageCallback;


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setChoseImage(ChoseImageCallback callBack) {
        this.mChoseImageCallback = callBack;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_photo_seletor,null);
        initFragment(Environment.getExternalStorageDirectory().getAbsolutePath(),Environment.getExternalStorageDirectory().getName());
        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();//获取dialog的窗口
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置控件背景颜色，透明
        WindowManager.LayoutParams windowParams = window.getAttributes();//获取窗口属性
        windowParams.dimAmount = 0.0f;//dimAmount在0.0f和1.0f之间，0.0f完全不暗，即背景是可见的 ，1.0f时候，背景全部变黑暗。
        windowParams.y = 0;//居中时windowParams.x=0,windowParams.y=0,即屏幕的坐标原点正好位于屏幕的正中间
        window.setAttributes(windowParams);//设置窗口属性
        Dialog dialog = getDialog();
        if (dialog != null) {
//            DisplayMetrics dm = new DisplayMetrics();//DisplayMetrics类获取屏幕大小
//            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);//获取到Activity的实际屏幕尺寸信息。
//            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.65), (int) (dm.heightPixels * 0.6));// 屏幕宽(高)度（像素）
            WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
            lp.height = (20 * ScreenUtil.getScreenHeight(getContext()) / 32);//获取屏幕的宽度，定义自己的宽度
            lp.width = (6 * ScreenUtil.getScreenWidth(getContext()) / 9);
            if (window != null) {
                window.setLayout(lp.width, lp.height);
        }
    }}
    private void initFragment(String imgpath,String name) {
        ImageFilesFragment filesFragment = ImageFilesFragment.newInstance(imgpath,name);
        filesFragment.setOnItemClickListener(this);
        if (mChoseImageCallback!=null){
            filesFragment.setChoseImageCallback(mChoseImageCallback);
        }
        getChildFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.layout, filesFragment).commit();


    }


    @Override
    public void onItemClick(String imgpath,String name) {
        initFragment(imgpath,name);
    }


    @Override
    public void onBackClick() {
        if (getChildFragmentManager().getBackStackEntryCount() > 0){
            getChildFragmentManager().popBackStack();
        }

    }
    public interface OnItemClickListener {
        void onItemClick(String imgpath);
    }

}
