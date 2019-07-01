package seekbar.ggh.com.file.manager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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


import org.apache.tools.ant.types.resources.selectors.None;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import seekbar.ggh.com.file.R;
import seekbar.ggh.com.file.classify.ClassifyFragment;
import seekbar.ggh.com.file.manager.add.AddDialogFragment;
import seekbar.ggh.com.file.system.SystemIntentFragment;
import utils.FileUtils;
import utils.FileManager;
import utils.GetFilesUtils;

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
    private TextView system;
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

    private List<Map<String, Object>> aList;
    private String baseFile;
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
    MyTask mTask;

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
        system= view.findViewById(R.id.system_intent);
        checkList = new ArrayList<>();
        checkListPath = new CopyOnWriteArrayList<>();
        mMutilFileAdapter = new MutilFileAdapter(getFiles(path));
        mRcv.setAdapter(mMutilFileAdapter);
        mRcv.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        mRcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.replace(R.id.container, new SystemIntentFragment());
                ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft2.addToBackStack(null);
                ft2.commit();
            }
        });
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
                getActivity().getSupportFragmentManager().popBackStack();//suport.v4包
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
                    checkList.clear();
                    checkListPath.clear();
                    //遍历所有item 获取其position——i
                    for (i = 0; i < mMutilFileAdapter.getItemCount(); i++) {
                        checkList.add(String.valueOf(i));//添加勾选的item——全部
//                        checkListPath.add(path);
//                        checkListPath.add();
                        //position就刷新第几个的checkbox的选中状态
                        mMutilFileAdapter.setCheckStatus(i);//勾选item——全部
                        title.setText("（共" + FileManager.getFileCount(path) + ")" + "已选中" + checkList.size() + "个文件");
                    }

                    mMutilFileAdapter.notifyDataSetChanged();//刷新
                    //全选键被取消时
                } else if (all.isChecked() == false) {
                    mMutilFileAdapter.setAllCheckBox(false);//非全选状态
                    checkList.clear();
                    checkListPath.clear();
                    for (i = 0; i < mMutilFileAdapter.getItemCount(); i++) {
                        checkList.remove(String.valueOf(i));//移除取消勾选的item——全部
//                        checkListPath.remove(path);
                        //觉得难看不想选择则移除，取消选中框选中状态
                        mMutilFileAdapter.removeCheckBoxStuaus(i);//取消勾选item——全部
                        title.setText("（共" + FileManager.getFileCount(path) + ")" + "已选中" + checkList.size() + "个文件");
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
                ImgFolderBean current = mMutilFileAdapter.getItem(position);
                path2 = current.getDir();
                name2 = current.getName();
                //如果隐藏勾选框时，点击item进入文件夹，刷新数据
                if (select = !select && mMutilFileAdapter.setShowCheckBox(false) && checkList.isEmpty()) {
//                    mMutilFileAdapter.setShowCheckBox(true);
                    mMutilFileAdapter.setNewData(getFiles(path2));
                    imgFolderBean.setName(name2);
                    imgFolderBean.setDir(path2);
                    if (checkIsImageFile(name2)) {
                        File file = new File(name2);
                        Intent it = new Intent(Intent.ACTION_VIEW);

                        Uri mUri = Uri.parse(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/sina/weibo/weibo/img-781355347939d43dbbd99c952e1e7bbd.jpg");

                        it.setDataAndType(mUri, "image/*");

                        startActivity(it);

                    }
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
        /**
         * 步骤2：创建AsyncTask子类的实例对象（即 任务实例）
         * 注：AsyncTask子类的实例必须在UI线程中创建
         */
        mTask = new MyTask();

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
                mMutilFileAdapter.setNewData(getFiles(path));
                break;
            case R.id.order_time:
                FileManager.orderByDate(path);
                mMutilFileAdapter.setNewData(getFiles(path));
                break;
            case R.id.order_size:
                FileManager.orderBySize(path);
                mMutilFileAdapter.setNewData(getFiles(path));
                break;
            case R.id.download:
                break;
            case R.id.imformation:
                showListDialog(path2, name2);
                break;
            case R.id.delete:
                for (final String deletePath : checkListPath) {
                    if (deletePath != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FileManager.deleteFolder(deletePath);
                            }
                        }).start();
                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        //刷新
                        mMutilFileAdapter.setNewData(getFiles(FileManager.extractDirPath(deletePath)));
                        mMutilFileAdapter.setShowCheckBox(false);
                        checkList.clear();
                        checkListPath.clear();
                        title.setText("（共" + FileManager.getFileCount(path) + ")" + "已选中" + checkList.size() + "个文件");
                    } else {
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
                    } else {
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
                    } else {
                        Toast.makeText(getActivity(), "请选择至少一个文件", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.like:
                Toast.makeText(getActivity(), "点击了收藏", Toast.LENGTH_SHORT).show();
                for (String likePath : checkListPath) {
                    if (likePath != null) {
                        mMutilFileAdapter.setShowLike(true);
                        mMutilFileAdapter.setShowCheckBox(false);
                        checkList.clear();
                        checkListPath.clear();
                        title.setText("（共" + FileManager.getFileCount(path) + ")" + "已选中" + checkList.size() + "个文件");
                    } else {
                        Toast.makeText(getActivity(), "请选择至少一个文件", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.rename:
                Toast.makeText(getActivity(), "点击了重命名", Toast.LENGTH_SHORT).show();
                for (String renamePath : checkListPath) {
                    if (renamePath != null) {
                        renameOldPath = renamePath;
                        showRenameDialog();
                    } else {
                        Toast.makeText(getActivity(), "请选择至少一个文件", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.share:
                Toast.makeText(getActivity(), "点击了分享", Toast.LENGTH_SHORT).show();
                ShareDialogFragment dialogFragment = ShareDialogFragment.newInstance("https://www.yuque.com/dashboard/docs");
                dialogFragment.show(getFragmentManager(), "share");
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showListDialog(String path2, String name2) {
        final String[] items = {"文件名称:" + name2, "路径:" + path2, "大小:" + FileManager.getFileSize(path2), "创建时间:" + FileManager.fileModifyTime(path2)};
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

    /**
     * 步骤1：创建AsyncTask子类
     * 注：
     * a. 继承AsyncTask类
     * b. 为3个泛型参数指定类型；若不使用，可用java.lang.Void类型代替
     * 此处指定为：输入参数 = String类型、执行进度 = Integer类型、执行结果 = String类型
     * c. 根据需求，在AsyncTask子类内实现核心方法
     */
    private class MyTask extends AsyncTask<None, None, None> {

        // 方法1：onPreExecute（）
        // 作用：执行 线程任务前的操作
        @Override
        protected void onPreExecute() {

            // 执行前显示提示
        }


        // 方法2：doInBackground（）
        // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
        // 此处通过计算从而模拟“加载进度”的情况
        @Override
        protected None doInBackground(None... nones) {


            return null;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTask.cancel(true);
    }

}