package seekbar.ggh.com.file.manager.add;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import seekbar.ggh.com.file.classify.adapter.PhotoAdapter;
import seekbar.ggh.com.file.classify.search.PhotoSearchFragment;
import seekbar.ggh.com.file.manager.ImgFolderBean;
import seekbar.ggh.com.myapplication.R;
import utils.FileManager;
import utils.FileUtils;
import utils.PhotoUtils;

public class UploadPhotoFragment extends Fragment {
    private RecyclerView rv;
    private UploadPhotoAdapter adapter;
    private RelativeLayout search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manager, null);
        rv = view.findViewById(R.id.rv);
        search = view.findViewById(R.id.search);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 5));
//        adapter = new UploadPhotoAdapter(FileManager.getImageFolders());
        String path= Environment.getExternalStorageDirectory().getAbsolutePath();
        adapter = new UploadPhotoAdapter(FileManager.getImageFolders());
        rv.setAdapter(adapter);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.replace(R.id.container, new PhotoSearchFragment());
                ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft2.addToBackStack(null);
                ft2.commit();
            }
        });
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
                                }else if (!isFile){
                                    getFiles(filePath);
                                }
//                            } else {
//                                list.add(bean);
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
