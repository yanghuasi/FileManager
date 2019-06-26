package seekbar.ggh.com.file.manager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import seekbar.ggh.com.file.classify.ClassifyFragment;
import seekbar.ggh.com.file.manager.add.AddDialogFragment;
import utils.FileUtils;
import seekbar.ggh.com.myapplication.R;
import utils.FileManager;

public class MutilFileFragment extends Fragment {
    private RecyclerView mRcv;
    private TextView classify;
    private ImageView add;
    private CheckBox all;
    private ImageView back;
    private MutilFileAdapter mMutilFileAdapter;
    private RelativeLayout search;
    private List<String> list;
    private TextView title;
    int i;
    private boolean select = true;
    private RelativeLayout newFolder;
    private RelativeLayout paste;
    private RelativeLayout move;
    private String copySourcePath;
    private String copyTargetPath;
    private String moveSourcePath;
    private String moveTargetPath;
    private String renameOldPath;
    private String renameNewPath;


    /**
     * 记录选中的ｃｈｅｃｋｂｏｘ
     */
    private List<String> checkList;
    private List<String> checkListPath;
    private List<MutilFileEntity> entities;
    private boolean delete = false;
    ImgFolderBean imgFolderBean;
    private String path;
    private String name;
    private String path2;
    private String name2;

    public static MutilFileFragment newInstance(String path, String name) {
        Bundle bundle = new Bundle();
        MutilFileFragment filesFragment = new MutilFileFragment();
        bundle.putString("path", path);
        bundle.putString("name", name);
//        Bitmap bm = BitmapFactory.decodeFile(imgpath);
        filesFragment.setArguments(bundle);
        return filesFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getArguments().getString("path");
        name = getArguments().getString("name");
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manager, null);
        //view.findViewById();
        classify = view.findViewById(R.id.classify);
        add = view.findViewById(R.id.add);
        mRcv = view.findViewById(R.id.rv);
        all = view.findViewById(R.id.all);
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        search = view.findViewById(R.id.search);
        newFolder = view.findViewById(R.id.newFolder);
        paste = view.findViewById(R.id.paste);
        move = view.findViewById(R.id.move);
        checkList = new ArrayList<>();
        checkListPath = new CopyOnWriteArrayList<>();
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
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        mMutilFileAdapter = new MutilFileAdapter(getFiles(path));

        mRcv.setAdapter(mMutilFileAdapter);
        mRcv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        classify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.replace(R.id.container, new ClassifyFragment());
                ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft2.addToBackStack(null);
                ft2.commit();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.replace(R.id.container, new SearchFragemnt());
                ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft2.addToBackStack(null);
                ft2.commit();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                String name = Environment.getExternalStorageDirectory().getName();
                ft2.replace(R.id.container, AddDialogFragment.newInstance(path, name));
                ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft2.addToBackStack(null);
                ft2.commit();
            }
        });
        //点击
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String extractDirPath=FileManager.extractDirPath(path);
//                if (path == Environment.getExternalStorageDirectory().getAbsolutePath()){
                getActivity().getSupportFragmentManager().popBackStack();//suport.v4包
//                }else {
//                    mMutilFileAdapter.notifyDataSetChanged();
////                    String extractDirPath=FileManager.extractDirPath(path);
////                    mMutilFileAdapter.setNewData(getFiles(extractDirPath));
//                }
//                if (listener!=null){
//                    listener.onBackClick();
//                }
            }
        });

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
//                        checkListPath.add(path);
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
//                        checkListPath.remove(path);
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
                path2 = current.getDir();
                name2 = current.getName();
                //如果隐藏勾选框时，点击item进入文件夹，刷新数据
                if (select = !select && mMutilFileAdapter.setShowCheckBox(false) && checkList.isEmpty()) {
//                    mMutilFileAdapter.setShowCheckBox(true);
                    mMutilFileAdapter.setNewData(getFiles(path2));
                    imgFolderBean.setName(name2);
                    imgFolderBean.setDir(path2);
                    //如果显示勾选框时，已经有选中的勾选框，再次点击时取消勾选
                }
                if (select = !select && mMutilFileAdapter.setShowCheckBox(true) && checkList.contains(String.valueOf(position))) {

                    checkList.remove(String.valueOf(position));
                    title.setText("（共" + FileManager.getFileCount(path) + ")" + "已选中" + checkList.size() + "个文件");
                    checkListPath.remove(path2);
                    //觉得难看不想选择则移除，取消选中框选中状态
                    mMutilFileAdapter.removeCheckBoxStuaus(position);
                    all.setChecked(false);//如果有一个item处于未选中状态，取消全选键的勾选
                    //如果显示勾选框时，没有选中的勾选框，点击时选中勾选框
                } else if (select = !select && mMutilFileAdapter.setShowCheckBox(true)) {
                    checkList.add(String.valueOf(position));
                    title.setText("（共" + FileManager.getFileCount(path) + ")" + "已选中" + checkList.size() + "个文件");
                    checkListPath.add(path2);
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

        newFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewFolderDialog();
            }
        });
        paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FileManager.copyFolder(copySourcePath, copyTargetPath);
                Toast.makeText(getActivity(), "粘贴成功", Toast.LENGTH_SHORT).show();
                newFolder.setVisibility(View.INVISIBLE);
                paste.setVisibility(View.INVISIBLE);
                mMutilFileAdapter.setNewData(getFiles(FileManager.extractDirPath(copyTargetPath)));
                mMutilFileAdapter.setShowCheckBox(false);
                checkList.clear();
                checkListPath.clear();
                title.setText("（共" + FileManager.getFileCount(path) + ")" + "已选中" + checkList.size() + "个文件");
            }
        });
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileManager.moveFolder(moveSourcePath, moveTargetPath);
                Toast.makeText(getActivity(), "移动成功", Toast.LENGTH_SHORT).show();
                newFolder.setVisibility(View.INVISIBLE);
                move.setVisibility(View.INVISIBLE);
                mMutilFileAdapter.setNewData(getFiles(FileManager.extractDirPath(moveTargetPath)));
                mMutilFileAdapter.setShowCheckBox(false);
                checkList.clear();
                checkListPath.clear();
                title.setText("（共" + FileManager.getFileCount(path) + ")" + "已选中" + checkList.size() + "个文件");
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
            case R.id.order_name:
                FileManager.orderByName(path);
                break;
            case R.id.order_time:
                FileManager.orderByDate(path);
                break;
            case R.id.order_size:
                FileManager.orderBySize(path);
                break;
            case R.id.download:
                break;
            case R.id.imformation:
                showListDialog(path2,name2);
                break;
            case R.id.delete:

                for (String deletePath : checkListPath) {
                    if (deletePath != null) {
                        FileManager.deleteFolder(deletePath);
                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        //刷新
                        mMutilFileAdapter.setNewData(getFiles(FileManager.extractDirPath(deletePath)));
                        mMutilFileAdapter.setShowCheckBox(false);
                        checkList.clear();
                        checkListPath.clear();
                        title.setText("（共" + FileManager.getFileCount(path) + ")" + "已选中" + checkList.size() + "个文件");
//                        notifyAll();
//                        String afterDeletePath = Environment.getExternalStorageDirectory().getAbsolutePath();
//                        mMutilFileAdapter.setNewData(getFiles(afterDeletePath));
                    }else {
                        Toast.makeText(getActivity(), "请选择至少一个文件", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.copy:
                newFolder.setVisibility(View.VISIBLE);
                paste.setVisibility(View.VISIBLE);
                for (String copyPath : checkListPath) {
                    if (copyPath != null) {
                        copySourcePath = copyPath;
                        copyTargetPath = FileManager.extractDirPath(path2) + "/复件" + FileManager.extractFileName(copyPath);
                        Toast.makeText(getActivity(), "复制成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "请选择至少一个文件", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.move:
                newFolder.setVisibility(View.VISIBLE);
                move.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "点击了移动", Toast.LENGTH_SHORT).show();
                for (String movePath : checkListPath) {
                    if (movePath != null) {
                        moveSourcePath = movePath;
                        moveTargetPath = FileManager.extractDirPath(path2) + "/移件" + FileManager.extractFileName(movePath);

                    }else {
                        Toast.makeText(getActivity(), "请选择至少一个文件", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.like:
                Toast.makeText(getActivity(), "点击了收藏", Toast.LENGTH_SHORT).show();
                for (String path : checkListPath) {
                    if (path != null) {
//                        FileManager.copyFile(path,);
                    }else {
                        Toast.makeText(getActivity(), "请选择至少一个文件", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.rename:
                Toast.makeText(getActivity(), "点击了重命名", Toast.LENGTH_SHORT).show();
                for (String renamePath : checkListPath) {
                    if (renamePath != null) {
                        renameOldPath=renamePath;
                        showRenameDialog();
                    }else {
                        Toast.makeText(getActivity(), "请选择至少一个文件", Toast.LENGTH_SHORT).show();
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
    private void showListDialog(String path2,String name2) {
        final String[] items = { "文件名称:"+name2,"路径:"+path2,"大小:"+FileManager.getFileSize(path2),"创建时间:"+FileManager.fileModifyTime(path2) };
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(getActivity());
        listDialog.setTitle("详情");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which 下标从0开始
                // ...To-do
                Toast.makeText(getActivity(),
                        "你点击了" + items[which],
                        Toast.LENGTH_SHORT).show();
            }
        });
        listDialog.show();
    }

    private void showRenameDialog() {
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(getActivity());
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(getActivity());
        inputDialog.setTitle("重命名").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),
                                editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        String newName = editText.getText().toString();
                        FileManager.reNamePath(renameOldPath, newName);

                        mMutilFileAdapter.setNewData(getFiles(FileManager.extractDirPath(path2)));
                        mMutilFileAdapter.setShowCheckBox(false);
                        checkList.clear();
                        checkListPath.clear();
                        title.setText("（共" + FileManager.getFileCount(path) + ")" + "已选中" + checkList.size() + "个文件");
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void showPasteDialog() {
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(getActivity());
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(getActivity());
        inputDialog.setTitle("新建文件夹").setView(editText);
        inputDialog.setPositiveButton("新建文件夹",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showNewFolderDialog();

                    }
                }).setNegativeButton("粘贴", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void showNewFolderDialog() {
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(getActivity());
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(getActivity());
        inputDialog.setTitle("新建文件夹").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),
                                editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        String input = editText.getText().toString();
                        String folderPath = path + "/" + input;
                        FileManager.makeFolder(folderPath);
                        mMutilFileAdapter.setNewData(getFiles(FileManager.extractDirPath(folderPath)));
                        mMutilFileAdapter.setShowCheckBox(false);
                        checkList.clear();
                        checkListPath.clear();
                        title.setText("（共" + FileManager.getFileCount(path) + ")" + "已选中" + checkList.size() + "个文件");
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
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


}