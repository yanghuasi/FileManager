package seekbar.ggh.com.file.manager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import seekbar.ggh.com.myapplication.R;

public class SearchFragemnt extends Fragment {
    private EditText editText;
    private ImageView clear;
    private TextView cancel;
    private RecyclerView result;




    public static SearchFragemnt newInstance(Context context) {
//        context.startActivity(new Intent().putExtra("path",filepath));
        SearchFragemnt fg = new SearchFragemnt();
        return fg;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search, null);
        // 获取所有资源图片
//        mPicturePaths = FileUtil.getAssetPicPath(getActivity());
        editText = view.findViewById(R.id.edit_text);
        clear = view.findViewById(R.id.clear);
        cancel = view.findViewById(R.id.cancel);
        result = view.findViewById(R.id.result);

        return view;
    }
}
