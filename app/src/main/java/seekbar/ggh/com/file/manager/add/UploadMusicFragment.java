package seekbar.ggh.com.file.manager.add;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.util.List;

import seekbar.ggh.com.file.R;
import seekbar.ggh.com.file.bean.Song;
import seekbar.ggh.com.file.classify.PlaybackFragment;
import seekbar.ggh.com.file.classify.adapter.AudioAdapter;
import seekbar.ggh.com.file.classify.search.AudioSearchFragment;
import seekbar.ggh.com.file.manager.ImgFolderBean;
import utils.AudioUtils;

public class UploadMusicFragment extends Fragment {
    private RecyclerView rv;
    private AudioAdapter audioAdapter;
    private RelativeLayout search;
    private Activity activity;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        context = getContext();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manager, null);
        rv= (RecyclerView) view.findViewById(R.id.rv);
        search= (RelativeLayout) view.findViewById(R.id.search);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        audioAdapter = new AudioAdapter(AudioUtils.getAllSongs(getActivity()));
        rv.setAdapter(audioAdapter);
        audioAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Song bean = audioAdapter.getItem(position);
                Intent intent = getMusicFileIntent(bean.getFileUrl());
                startActivity(intent);
//                PackageManager packageManager = getActivity().getPackageManager();
//                packageManager.getInstalledApplications(0);

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.replace(R.id.container, new AudioSearchFragment());
                ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft2.addToBackStack(null);
                ft2.commit();
            }
        });
        return view;
    }
    private boolean isInstall(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    private static String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        /* 取得扩展名 */
        String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
        /* 依扩展名的类型决定MimeType */
        if (end.equals("pdf")) {
            type = "application/pdf";
        } else if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") ||
                end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            type = "audio/*";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video/*";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
                end.equals("jpeg") || end.equals("bmp")) {
            type = "image/*";
        } else if (end.equals("apk")) {
            type = "application/vnd.android.package-archive";
        } else if (end.equals("pptx") || end.equals("ppt")) {
            type = "application/vnd.ms-powerpoint";
        } else if (end.equals("docx") || end.equals("doc")) {
            type = "application/vnd.ms-word";
        } else if (end.equals("xlsx") || end.equals("xls")) {
            type = "application/vnd.ms-excel";
        } else if (end.equals("txt")) {
            type = "text/plain";
        } else if (end.equals("html") || end.equals("htm")) {
            type = "text/html";
        } else {
            //如果无法直接打开，就跳出软件列表给用户选择
            type = "*/*";
        }
        return type;
    }

    private Intent getMusicFileIntent(String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider",
                file);
        String type = getMIMEType(file);
        if (type.contains("m4a")||type.contains("mp3") || type.contains("mid") || type.contains("xmf") || type.contains("ogg") || type.contains("wav") ) {
            if (isInstall(getActivity(), "com.netease.cloudmusic")) {
                intent.setClassName("com.netease.cloudmusic",
                        "com.netease.nis.wrapper.MyApplication");
                intent.setData(uri);
            } else {
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setDataAndType(uri, type);
            }
        } else {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setDataAndType(uri, type);
        }
        return intent;
    }
    /**
     * 播放指定名称的歌曲
     * @param audioPath 指定默认播放的音乐
     */
    public void playAudio(String audioPath) {
//        Intent mIntent = new Intent();
//        mIntent.setAction(android.content.Intent.ACTION_VIEW);
//        Uri uri = Uri.parse(audioPath);//替换成audiopath
//        mIntent.setDataAndType(uri , "audio/mp3");
//       startActivity(mIntent);

        Intent intent_music = new Intent(Intent.ACTION_PICK);
        intent_music.setDataAndType(Uri.parse(audioPath),"vnd.android.cursor.dir/playlist");
        intent_music.putExtra("withtabs", true); // 显示tab选项卡
        intent_music.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent j =Intent.createChooser(intent_music, "Choose an application to open with:");
        if (j == intent_music) {
            startActivity(j);
        } else {
            Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
            startActivity(intent);
        }

    }
}


