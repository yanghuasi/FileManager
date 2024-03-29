package seekbar.ggh.com.file.classify.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import seekbar.ggh.com.file.R;
import utils.FileManager;

public class PhotoSearchFragment extends Fragment {
    private String[] mStrs = {"kk", "kk", "wskx", "wksx"};
    private SearchView mSearchView;
    private ListView listView;
    private ListView rv;



    public static seekbar.ggh.com.file.manager.SearchFragemnt newInstance(Context context) {
//        context.startActivity(new Intent().putExtra("path",filepath));
        seekbar.ggh.com.file.manager.SearchFragemnt fg = new seekbar.ggh.com.file.manager.SearchFragemnt();
        return fg;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search, null);
        mSearchView = (SearchView) view.findViewById(R.id.search);
        listView = (ListView) view.findViewById(R.id.listview);
        rv = (ListView) view.findViewById(R.id.rv);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mStrs));
        listView.setTextFilterEnabled(true);

        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                quertPhoto(query);
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
    private void quertPhoto(String key) {
        String[] photo = new String[]{};
        if (!TextUtils.isEmpty(key)){
            photo = FileManager.queryPhoto(getActivity(), key);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, photo);
        rv.setAdapter(adapter);
    }
}
