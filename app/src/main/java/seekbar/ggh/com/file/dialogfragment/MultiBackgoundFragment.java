package seekbar.ggh.com.file.dialogfragment;

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

import seekbar.ggh.com.myapplication.R;
import utils.ScreenUtil;


public class MultiBackgoundFragment extends DialogFragment {
    private GridView gv_ProductPic;
    private BackgoundAdapter mAdapter;
    private List<String> mPicturePaths;
    public final static String KEY_PATH = "backgroundpath";
    public RecyclerView recyclerView;
    public TextView address;
    public OnItemClickListener listener;
    public ImageView back;
    public AsynAdapt asynAdapt;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


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

        final View view = inflater.inflate(R.layout.fragment_dialog_asset, null);
        // 获取所有资源图片
//        mPicturePaths = FileUtil.getAssetPicPath(getActivity());
        back = view.findViewById(R.id.iv_back);
        recyclerView = view.findViewById(R.id.recycleview);
        address = (TextView) view.findViewById(R.id.tv_address);
/**
 * 步骤2：创建AsyncTask子类的实例对象（即 任务实例）
 * 注：AsyncTask子类的实例必须在UI线程中创建
 */
        asynAdapt = new AsynAdapt();
        asynAdapt.execute();
        return view;
    }


    public interface OnItemClickListener {
        void onItemClick(String path);

        void onBackClick();
    }

    private class AsynAdapt extends AsyncTask<None, None, None> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (listener!=null){
//                        listener.onBackClick();
//                    }
                    dismiss();
                }
            });
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));

            address.setText("background");

        }

        @Override
        protected None doInBackground(None... nones) {
            mPicturePaths = new ArrayList<>();
            try {
                String[] fileName = getActivity().getAssets().list("background");
                for (String path : fileName) {
                    mPicturePaths.add(path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Collections.sort(mPicturePaths, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    int params1 = Integer.valueOf(o1.split("\\.")[0].substring(2));
                    int params2 = Integer.valueOf(o2.split("\\.")[0].substring(2));

                    return params1 - params2;
                    // return o1.compareTo(o2);
                }
            });
            mAdapter = new BackgoundAdapter(mPicturePaths);
            //报错：Caused by: android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
            recyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter a, View view, int position) {
                    //EventBus.getDefault().post(new PhtoEvent(FileUtil.getAssetPicPath(getActivity()).get(position)));
//                EventBus.getDefault().post(new PhtoEvent(mAdapter.getData().get(position)));
                    if (listener != null) {
                        listener.onItemClick(mAdapter.getData().get(position));

                        Log.i("imgPath", "发送成功");
                    }
                }
            });
            return null;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        asynAdapt.cancel(true);
    }
}

