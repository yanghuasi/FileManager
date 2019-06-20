package seekbar.ggh.com.myapplication.dialogfragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import seekbar.ggh.com.myapplication.FileUtils;
import seekbar.ggh.com.myapplication.ImgFolderBean;
import seekbar.ggh.com.myapplication.R;
import utils.ScreenUtil;

public class ImageFilesFragment extends DialogFragment {
    private String path;
    private String name;
    private RecyclerView recyclerView;
    PhotoAdapter fileImagesAdapter;
    private OnItemClickListener listener;
    private TextView address;
    private ImageView back;
    public final static String KEY_PATH = "path";
    public ChoseImageCallback mChoseImageCallback;

    public void setChoseImageCallback(ChoseImageCallback callBack) {
        this.mChoseImageCallback = callBack;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static ImageFilesFragment newInstance(String imgpath,String name) {
        Bundle bundle = new Bundle();
        ImageFilesFragment filesFragment = new ImageFilesFragment();
        bundle.putString("path", imgpath);
        bundle.putString("name", name);
//        Bitmap bm = BitmapFactory.decodeFile(imgpath);
        filesFragment.setArguments(bundle);
        return filesFragment;

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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getArguments().getString("path");
        name = getArguments().getString("name");

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_photo, null);
        recyclerView = view.findViewById(R.id.recycleview);
        address= view.findViewById(R.id.tv_address);
        if (name .equals("0")){
            address.setText("album");
        }else {
            address.setText(name);
        }
//        address.setText("album");
        back=view.findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onBackClick();
                }
                dismiss();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));
        fileImagesAdapter = new PhotoAdapter(getFiles(path));
        fileImagesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImgFolderBean current = fileImagesAdapter.getItem(position);
                String imgpath=current.getDir();
                String name=current.getName();
                fileImagesAdapter.setNewData(getFiles(imgpath));

//                if (listener!=null){
//                        listener.onItemClick(imgpath,name);//打开文件夹
//                        Log.i("imgPath", "发送成功");
//
//                }

                if (mChoseImageCallback!=null && current.isFile()){
                    mChoseImageCallback.onChoseImage(imgpath);
                }
            }
        });

        recyclerView.setAdapter(fileImagesAdapter);
        return view;
    }

    private List<ImgFolderBean> getFiles(String path) {
        List<ImgFolderBean> list = new ArrayList<>();
        if (!TextUtils.isEmpty(path)) {
            if (FileUtils.isFileExists(path)) {
                File file = FileUtils.getFileByPath(path);
                try {
                    String[] files = file.list();
                    if (files != null) {
                        for (String fs : files) {
                            ImgFolderBean bean = new ImgFolderBean();
                            String filePath = path + "/" + fs;
                            boolean isFile = FileUtils.isFile(filePath);
                            bean.setFile(isFile);
                            bean.setName(FileUtils.getFileName(filePath));
                            bean.setCount(FileUtils.getFileLines(filePath));
                            bean.setDir(filePath);
                            if (isFile) {
                                if (checkIsImageFile(bean.getName())) {
                                    list.add(bean);
                                }
                            } else {
                                list.add(bean);
                            }

                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
        return list;
    }

    /**
     * 检查扩展名，得到图片格式的文件
     *
     * @param fName 文件名
     * @return
     */
    @SuppressLint("DefaultLocale")
    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png")
                || FileEnd.equals("jpeg") || FileEnd.equals("bmp")) {
            //|| FileEnd.equals("gif")
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    public interface OnItemClickListener{
        void onItemClick(String imgpath, String name);
        void onBackClick();
    }
}
