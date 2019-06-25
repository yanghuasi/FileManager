package seekbar.ggh.com.file.classify;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import seekbar.ggh.com.file.classify.adapter.AudioAdapter;
import seekbar.ggh.com.file.classify.adapter.RarAdapter;
import seekbar.ggh.com.file.manager.ImgFolderBean;
import seekbar.ggh.com.myapplication.R;
import utils.FileUtils;

public class RarFragment extends Fragment {
    private RecyclerView rv;
    private RarAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manager, null);
        rv=view.findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/tencent/MicroMsg/Download/";
        adapter = new RarAdapter(getAllFiles(path,"rar"));
        rv.setAdapter(adapter);
        return view;
    }

    /**
     * 获取指定目录内所有文件路径
     * @param dirPath 需要查询的文件目录
     * @param _type 查询类型，比如mp3什么的
     */
    public static List<ImgFolderBean> getAllFiles(String dirPath, String _type) {
        File f = new File(dirPath);
        if (!f.exists()) {//判断路径是否存在
            return null;
        }

        File[] files = f.listFiles();

        if(files==null){//判断权限
            return null;
        }
        List<ImgFolderBean> list = new ArrayList<>();
        for (File _file : files) {//遍历目录
            if(_file.isFile() && _file.getName().endsWith(_type)){
                String _name=_file.getName();
                String filePath = _file.getAbsolutePath();//获取文件路径
                String fileName = _file.getName().substring(0,_name.length()-4);//获取文件名
                try {
                    ImgFolderBean bean = new ImgFolderBean();
                    boolean isFile = FileUtils.isFile(filePath);
                    bean.setFile(isFile);
                    bean.setName(FileUtils.getFileName(filePath));
                    bean.setCount(FileUtils.getFileLines(filePath));
                    bean.setDir(filePath);
                    list.add(bean);
                }catch (Exception e){
                }
            } else if(_file.isDirectory()){//查询子目录
                getAllFiles(_file.getAbsolutePath(), _type);
            } else{
            }
        }
        return list;
    }

}
