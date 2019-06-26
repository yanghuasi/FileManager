package seekbar.ggh.com.file.manager.add;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import seekbar.ggh.com.file.classify.search.PhotoSearchFragment;
import seekbar.ggh.com.myapplication.R;
import utils.FileManager;
import utils.ScreenUtils;

public class AddDialogFragment extends Fragment {
    private RelativeLayout scan;
    private RelativeLayout newFolder;
    private RelativeLayout uploadPhoto;
    private RelativeLayout uploadVideo;
    private RelativeLayout uploadDoc;
    private RelativeLayout uploadMusic;
    private RelativeLayout uploadOther;
    private RelativeLayout newNotes;
    private ImageView cancel;
    private String path;
    private String name;

    public static AddDialogFragment newInstance(String path, String name) {
//        context.startActivity(new Intent().putExtra("path",filepath));
        AddDialogFragment fg = new AddDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        bundle.putString("name", name);
//        Bitmap bm = BitmapFactory.decodeFile(imgpath);
        fg.setArguments(bundle);
        return fg;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getArguments().getString("path");
        name = getArguments().getString("name");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_dialog_add, null);
        // 获取所有资源图片
//        mPicturePaths = FileUtil.getAssetPicPath(getActivity());
        scan = view.findViewById(R.id.scan);
        newFolder = view.findViewById(R.id.newFolder);
        uploadPhoto = view.findViewById(R.id.upload_photo);
        uploadVideo = view.findViewById(R.id.upload_video);
        uploadDoc = view.findViewById(R.id.upload_doc);
        uploadMusic = view.findViewById(R.id.upload_music);
        uploadOther = view.findViewById(R.id.upload_other);
        newNotes = view.findViewById(R.id.newNotes);
        cancel = view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();//suport.v4包
            }
        });
        newFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.replace(R.id.container, new UploadPhotoFragment());
                ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft2.addToBackStack(null);
                ft2.commit();
            }
        });
        return view;
    }

    private void showInputDialog() {
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
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    EditText editText;
    Activity activity;
    AlertDialog saveDialog;

    private void initCreateDialog() {
        editText = new EditText(activity);
        editText.setHint("新文件名");//设置view为空时,view显示的内容
        editText.setGravity(Gravity.CENTER);//居中
        editText.setSingleLine();//setSingleLine(false); 你的文本框里的数据不在一行显示。setSingleLine(true); 你的文本框里的数据在一行显示。。。就是不换行。
        editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);//设置输入文本类型
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);//文本完成
        editText.setSelectAllOnFocus(true);//获得焦点时全选文本
        //setOnEditorActionListener编辑完之后点击软键盘上的回车键才会触发
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ScreenUtils.hideInput(saveDialog.getCurrentFocus());//隐藏键盘（获取当前activity中获得焦点的view）
                    saveDialog.dismiss();//弹窗消失
//                    String input = editText.getText().toString();//获取输入的数据
//                    String folderPath=path+"/"+input;
//                    FileManager.makeFolder(folderPath);
                }
                return true;
            }
        });
        saveDialog = new AlertDialog.Builder(getActivity())
                .setTitle("请输入保存文件名")
                .setMessage("")
                .setView(editText)//设置编辑文本框
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ScreenUtils.hideInput(saveDialog.getCurrentFocus());
                        String input = editText.getText().toString();
                        String folderPath = path + "/" + input;
                        FileManager.makeFolder(folderPath);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ScreenUtils.hideInput(saveDialog.getCurrentFocus());
                    }
                })
                .setCancelable(false)//对话框弹出后点击或按返回键不消失
                .create();
    }

}

