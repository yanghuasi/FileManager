package seekbar.ggh.com.myapplication.manager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utils.FileUtils;
import seekbar.ggh.com.myapplication.R;
import utils.FileManager;

public class MutilFileFragment extends Fragment {
    private RecyclerView mRcv;
    private TextView upload;
    private TextView menu;
    private CheckBox all;
    private MutilFileAdapter mMutilFileAdapter;
    private List<String> list;
    int i;
    private boolean select = true;

    /**
     * 记录选中的ｃｈｅｃｋｂｏｘ
     */
    private List<String> checkList;
    private List<String> checkListPath;
    private List<MutilFileEntity> entities;

    public static MutilFileFragment newInstance() {
        Bundle bundle = new Bundle();
        MutilFileFragment filesFragment = new MutilFileFragment();
        return filesFragment;

    }

    public static MutilFileFragment newInstance(String imgpath, String name) {
        Bundle bundle = new Bundle();
        MutilFileFragment filesFragment = new MutilFileFragment();
        bundle.putString("path", imgpath);
        bundle.putString("name", name);
//        Bitmap bm = BitmapFactory.decodeFile(imgpath);
        filesFragment.setArguments(bundle);
        return filesFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manager, null);
        //view.findViewById();
        upload = (TextView) view.findViewById(R.id.upload);
        menu = (TextView) view.findViewById(R.id.menu);
        mRcv = (RecyclerView) view.findViewById(R.id.rv);
        all = view.findViewById(R.id.all);
        checkList = new ArrayList<>();
        checkListPath= new ArrayList<>();
        entities = new ArrayList<>();
        MutilFileEntity newEntity = new MutilFileEntity("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542721963681&di=e7247350673c1e4dca62862ee9c3368b&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F0%2F53b4b747b9094.jpg");
        MutilFileEntity newEntity1 = new MutilFileEntity("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542721963681&di=47f5c7a5dfcec7a144dabf36f04b3a8d&imgtype=0&src=http%3A%2F%2Fwww.znsfagri.com%2Fuploadfile%2Feditor%2Fimage%2F20170626%2F20170626151136_11631.jpg");
        MutilFileEntity newEntity2 = new MutilFileEntity("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542721963680&di=e574b93a7e706f5bb8463b2adac00959&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201501%2F02%2F20150102204647_dj2t8.jpeg");
        MutilFileEntity newEntity3 = new MutilFileEntity("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542721963679&di=94b173763aa94c330fe8168f8fd63d78&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201505%2F06%2F20150506112813_skniy.jpeg");
        MutilFileEntity newEntity4 = new MutilFileEntity("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542721963679&di=3ebaa5dac1253587243605900ee1c114&imgtype=0&src=http%3A%2F%2Fimg0.ph.126.net%2FoM3Ux_qm9BNW6fp1HxJ8_Q%3D%3D%2F1687723960457254251.jpg");
        MutilFileEntity newEntity5 = new MutilFileEntity("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542721963678&di=c0b6cf83e73f0d587671ac2cfe74d9fd&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201409%2F15%2F20140915215605_WZwQW.jpeg");

        entities.add(newEntity);
        entities.add(newEntity1);
        entities.add(newEntity2);
        entities.add(newEntity3);
        entities.add(newEntity4);
        entities.add(newEntity5);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        mMutilFileAdapter = new MutilFileAdapter(getFiles(path));

        mRcv.setAdapter(mMutilFileAdapter);
        mRcv.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        //点击
        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 通过这个方法，来监听当前的checkbox是否被选中
                if (isChecked) {
                    mMutilFileAdapter.setAllCheckBox(true);

                } else {
                    mMutilFileAdapter.setAllCheckBox(false);
                }
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //全选键被勾选时
                if (all.isChecked()) {
                    mMutilFileAdapter.setAllCheckBox(true);//全选状态
                    //遍历所有item 获取其position——i
                    for (i = 0; i < mMutilFileAdapter.getItemCount(); i++) {
                        checkList.add(String.valueOf(i));//添加勾选的item——全部
//                        checkListPath.add();
                        //position就刷新第几个的checkbox的选中状态
                        mMutilFileAdapter.setCheckStatus(i);//勾选item——全部
                    }

                    mMutilFileAdapter.notifyDataSetChanged();//刷新
                    //全选键被取消时
                } else if (all.isChecked() == false) {
                    mMutilFileAdapter.setAllCheckBox(false);//非全选状态
                    for (i = 0; i < mMutilFileAdapter.getItemCount(); i++) {
                        checkList.remove(String.valueOf(i));//移除取消勾选的item——全部
                        //觉得难看不想选择则移除，取消选中框选中状态
                        mMutilFileAdapter.removeCheckBoxStuaus(i);//取消勾选item——全部
                    }

                    mMutilFileAdapter.notifyDataSetChanged();
                }
            }
        });
        mMutilFileAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("ADAPTER", "0");
                Toast.makeText(getActivity(), "点击了第" + (position + 1) + "条条目", Toast.LENGTH_SHORT).show();
                /*if (checkList.contains(String.valueOf(position))) {
                    checkList.remove(String.valueOf(position));
                    //觉得难看不想选择则移除，取消选中框选中状态
                    mPhotoSeletorAdapter.removeCheckBoxStuaus(position);
                } else {
                    checkList.add(String.valueOf(position));
                    //position就刷新第几个的checkbox的选中状态
                    mPhotoSeletorAdapter.setCheckStatus(position);
                }

                //只刷新点击的那一条记录
                mPhotoSeletorAdapter.notifyItemChanged(position);*/
                ImgFolderBean current = mMutilFileAdapter.getItem(position);
                String imgpath = current.getDir();
                String name = current.getName();

                if (listener != null) {
                    listener.onItemClick(imgpath, name);//打开文件夹
                    Log.i("imgPath", "发送成功");

                }

                if (checkList.contains(String.valueOf(position))) {
                    checkList.remove(String.valueOf(position));
                    //觉得难看不想选择则移除，取消选中框选中状态
                    mMutilFileAdapter.removeCheckBoxStuaus(position);
                    all.setChecked(false);//如果有一个item处于未选中状态，取消全选键的勾选
                } else {
                    checkList.add(String.valueOf(position));
                    //position就刷新第几个的checkbox的选中状态
                    mMutilFileAdapter.setCheckStatus(position);
                    //如果全部item处于选中状态，勾选全选键
                    if (checkList.size() == mMutilFileAdapter.getItemCount()) {
                        all.setChecked(true);
                    }
                }


                //只刷新点击的那一条记录
                mMutilFileAdapter.notifyItemChanged(position);
            }
        });

        mMutilFileAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                if (select = !select) {
                    mMutilFileAdapter.setShowCheckBox(true);//长按Item出现勾选框checkbox
                    all.setVisibility(View.VISIBLE);//显示全选键
                    mMutilFileAdapter.notifyDataSetChanged();//刷新
                } else {
                    mMutilFileAdapter.setShowCheckBox(false);//长按Item隐藏勾选框checkbox
                    all.setVisibility(View.INVISIBLE);//隐藏全选键
                    mMutilFileAdapter.notifyDataSetChanged();//刷新
                    //长按勾选框消失，取消所有勾选框的勾选
                    for (position = 0; position < mMutilFileAdapter.getItemCount(); position++) {
                        checkList.clear();
                        //觉得难看不想选择则移除，取消选中框选中状态
                        mMutilFileAdapter.removeCheckBoxStuaus(position);
                        all.setChecked(false);//如果有一个item处于未选中状态，取消全选键的勾选
                    }
                }
                return false;
            }
        });


        return view;


    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //导入菜单布局
        inflater.inflate(R.menu.menu_main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //创建菜单项的点击事件
        switch (item.getItemId()) {
            case R.id.delete:
                Toast.makeText(getActivity(), "点击了删除", Toast.LENGTH_SHORT).show();
//                List<String> deleteFile=new ArrayList<>();
//                deleteFile.add();
                for (String path : checkList) {
                    if (path != null) {
                        FileManager.deleteFile(path);
                    }
                }
                break;
            case R.id.copy:
                Toast.makeText(getActivity(), "点击了复制", Toast.LENGTH_SHORT).show();

                break;
            case R.id.cut:
                Toast.makeText(getActivity(), "点击了剪切", Toast.LENGTH_SHORT).show();
                break;
            case R.id.paste:
                Toast.makeText(getActivity(), "点击了粘贴", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rename:
                Toast.makeText(getActivity(), "点击了重命名", Toast.LENGTH_SHORT).show();
                String newName="新名字";
                for (String path : checkList) {
                    if (path != null) {
                        String oldName=FileManager.extractFileName(path);
                        FileManager.reNamePath(oldName,newName);
                    }
                }

                break;
            case R.id.share:
                Toast.makeText(getActivity(), "点击了分享", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("0", "e" + e);
                    Toast.makeText(getActivity(), "" + e, Toast.LENGTH_LONG);
                }

            }
        }
        return list;
    }

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

    private MutilFileFragment.OnItemClickListener listener;

    public void setOnItemClickListener(MutilFileFragment.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String imgpath, String name);
    }
}