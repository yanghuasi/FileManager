package seekbar.ggh.com.file.manager;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import seekbar.ggh.com.file.R;
import utils.FileManager;
import utils.FileUtils;

public class SearchFragemnt extends Fragment {
    private String[] mStrs = {"kk", "kk", "wskx", "wksx"};
    private SearchView mSearchView;
    private ListView listView;
    private ListView rv;



    public static SearchFragemnt newInstance(Context context) {
//        context.startActivity(new Intent().putExtra("path",filepath));
        SearchFragemnt fg = new SearchFragemnt();
        return fg;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search, null);
        mSearchView = view.findViewById(R.id.search);
        listView = view.findViewById(R.id.listview);
        rv = view.findViewById(R.id.rv);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mStrs));
        listView.setTextFilterEnabled(true);
        // 获取所有资源图片
//        mPicturePaths = FileUtil.getAssetPicPath(getActivity());
//        editText = view.findViewById(R.id.edit_text);
//        clear = view.findViewById(R.id.clear);
//        cancel = view.findViewById(R.id.cancel);
//        result = view.findViewById(R.id.result);

        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                quertFile(query);
                return true;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    listView.setFilterText(newText);
                } else {
                    listView.clearTextFilter();
                }
                return false;
            }
        });

        return view;
    }
    /**
     * 模糊查找音乐
     * @param key
     */
    private void quertFile(String key) {
        String[] musics = new String[]{};
        if (!TextUtils.isEmpty(key)){
            musics = FileManager.quertFile(getActivity(), key);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, musics);
        rv.setAdapter(adapter);
    }
}
