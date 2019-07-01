package seekbar.ggh.com.file.manager.add;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import seekbar.ggh.com.file.R;
import seekbar.ggh.com.file.classify.adapter.PhotoAdapter;
import seekbar.ggh.com.file.classify.search.PhotoSearchFragment;
import seekbar.ggh.com.file.manager.ImgFolderBean;
import seekbar.ggh.com.file.manager.MutilFileFragment;

import utils.FileManager;
import utils.FileUtils;
import utils.PhotoUtils;

public class UploadPhotoFragment extends Fragment {
    private RecyclerView rv;
    private UploadPhotoAdapter adapter;
    private RelativeLayout search;
    private String folderPath;
    Context context;
    Activity activity;
    private String name2;
    private String path2;
    // 线程变量
    MyTask mTask;
    ProgressBar progressBar; // 进度条
    TextView title;
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
        rv = view.findViewById(R.id.rv);
        search = view.findViewById(R.id.search);
        progressBar= view.findViewById(R.id.progress_bar);
        title= view.findViewById(R.id.title);
        /**
         * 步骤2：创建AsyncTask子类的实例对象（即 任务实例）
         * 注：AsyncTask子类的实例必须在UI线程中创建
         */
        mTask = new MyTask();
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        adapter = new UploadPhotoAdapter(FileManager.getImageFolders(context));
//        adapter = new UploadPhotoAdapter(FileManager.getImagePathFromFolder(context));

//        adapter = new UploadPhotoAdapter(getFiles(path));
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("ADAPTER", "0");
                Toast.makeText(getActivity(), "点击了第" + (position + 1) + "条条目", Toast.LENGTH_SHORT).show();
                ImgFolderBean current = (ImgFolderBean) adapter.getItem(position);
                path2 = current.getDir();
                name2 = current.getName();
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.replace(R.id.container,MutilFileFragment.newInstance(path2,name2));
                ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft2.addToBackStack(null);
                ft2.commit();
                /**
                 * 步骤3：手动调用execute(Params... params) 从而执行异步线程任务
                 * 注：
                 *    a. 必须在UI线程中调用
                 *    b. 同一个AsyncTask实例对象只能执行1次，若执行第2次将会抛出异常
                 *    c. 执行任务中，系统会自动调用AsyncTask的一系列方法：onPreExecute() 、doInBackground()、onProgressUpdate() 、onPostExecute()
                 *    d. 不能手动调用上述方法
                 */
                mTask.execute();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
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

    /**
     * 步骤1：创建AsyncTask子类
     * 注：
     *   a. 继承AsyncTask类
     *   b. 为3个泛型参数指定类型；若不使用，可用java.lang.Void类型代替
     *   c. 根据需求，在AsyncTask子类内实现核心方法
     */

    /**
     * 步骤1：创建AsyncTask子类
     * 注：
     *   a. 继承AsyncTask类
     *   b. 为3个泛型参数指定类型；若不使用，可用java.lang.Void类型代替
     *      此处指定为：输入参数 = String类型、执行进度 = Integer类型、执行结果 = String类型
     *   c. 根据需求，在AsyncTask子类内实现核心方法
     */
    private class MyTask extends AsyncTask<String, Integer, String> {

        // 方法1：onPreExecute（）
        // 作用：执行 线程任务前的操作
        @Override
        protected void onPreExecute() {

            title.setText("加载中");
            // 执行前显示提示
        }


        // 方法2：doInBackground（）
        // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
        // 此处通过计算从而模拟“加载进度”的情况
        @Override
        protected String doInBackground(String... params) {

            try {
                int count = 0;
                int length = 1;
                while (count<99) {

                    count += length;
                    // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                    publishProgress(count);
                    // 模拟耗时任务
                    Thread.sleep(50);
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        // 方法3：onProgressUpdate（）
        // 作用：在主线程 显示线程任务执行的进度
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            progressBar.setProgress(progresses[0]);
            title.setText("loading..." + progresses[0] + "%");

        }

        // 方法4：onPostExecute（）
        // 作用：接收线程任务执行结果、将执行结果显示到UI组件
        @Override
        protected void onPostExecute(String result) {
            // 执行完毕后，则更新UI
            title.setText("加载完毕");
        }

        // 方法5：onCancelled()
        // 作用：将异步任务设置为：取消状态
        @Override
        protected void onCancelled() {

            title.setText("已取消");
            progressBar.setProgress(0);

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mTask.cancel(true);
    }
}
