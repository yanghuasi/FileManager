package seekbar.ggh.com.file.manager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.apache.tools.ant.types.resources.selectors.None;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import seekbar.ggh.com.file.dialogfragment.BackgoundAdapter;
import seekbar.ggh.com.file.dialogfragment.MultiBackgoundFragment;
import seekbar.ggh.com.myapplication.R;
import utils.ScreenUtil;

public class ShareDialogFragment extends DialogFragment {

    private ShareAdapter mAdapter;
    private List<String> mPicturePaths;
    public RecyclerView recyclerView;



    public static MultiBackgoundFragment newInstance(Context context) {
//        context.startActivity(new Intent().putExtra("path",filepath));
        MultiBackgoundFragment fg = new MultiBackgoundFragment();
        return fg;
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
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_dialog_share, null);
        recyclerView=view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        mAdapter = new ShareAdapter(mPicturePaths);

        //报错：Caused by: android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, int position) {
                //EventBus.getDefault().post(new PhtoEvent(FileUtil.getAssetPicPath(getActivity()).get(position)));
//                EventBus.getDefault().post(new PhtoEvent(mAdapter.getData().get(position)));

            }
        });

        return view;
    }


}

