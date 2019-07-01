package seekbar.ggh.com.file.classify;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import seekbar.ggh.com.file.R;
import seekbar.ggh.com.file.bean.FileBean;
import seekbar.ggh.com.file.classify.adapter.DocAdapter;
import seekbar.ggh.com.file.manager.ImgFolderBean;
import seekbar.ggh.com.file.manager.SearchFragemnt;
import utils.FileUtils;

public class DocFragment extends Fragment {
    private RecyclerView rv;
    private DocAdapter adapter;
    private RelativeLayout search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manager, null);
        rv = view.findViewById(R.id.rv);
        search = view.findViewById(R.id.search);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tencent/MicroMsg/Download/";
        adapter = new DocAdapter(getAllFiles(path, "ppt"));
        rv.setAdapter(adapter);
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
        return view;
    }


    /**
     * 获取指定目录内所有文件路径
     *
     * @param dirPath 需要查询的文件目录
     * @param _type   查询类型，比如mp3什么的
     */
    public static List<ImgFolderBean> getAllFiles(String dirPath, String _type) {
        File f = new File(dirPath);
        if (!f.exists()) {//判断路径是否存在
            return null;
        }

        File[] files = f.listFiles();

        if (files == null) {//判断权限
            return null;
        }
        List<ImgFolderBean> list = new ArrayList<>();
        for (File _file : files) {//遍历目录
            if (_file.isFile() && _file.getName().endsWith(_type)) {
                String _name = _file.getName();
                String filePath = _file.getAbsolutePath();//获取文件路径
                String fileName = _file.getName().substring(0, _name.length() - 4);//获取文件名
                try {
                    ImgFolderBean bean = new ImgFolderBean();
                    boolean isFile = FileUtils.isFile(filePath);
                    bean.setFile(isFile);
                    bean.setName(FileUtils.getFileName(filePath));
                    bean.setCount(FileUtils.getFileLines(filePath));
                    bean.setDir(filePath);
                    list.add(bean);
                } catch (Exception e) {
                }
            } else if (_file.isDirectory()) {//查询子目录
                getAllFiles(_file.getAbsolutePath(), _type);
            } else {
            }
        }
        return list;
    }


}


