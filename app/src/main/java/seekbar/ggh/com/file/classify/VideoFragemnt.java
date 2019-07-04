package seekbar.ggh.com.file.classify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import seekbar.ggh.com.file.R;
import seekbar.ggh.com.file.bean.Song;
import seekbar.ggh.com.file.bean.Video;
import seekbar.ggh.com.file.classify.adapter.AudioAdapter;
import seekbar.ggh.com.file.classify.adapter.VideoAdapter;
import seekbar.ggh.com.file.classify.search.VideoSearchFragment;
import utils.VideoUtils;

public class VideoFragemnt extends Fragment {
    private RecyclerView rv;
    private VideoAdapter adapter;
    private RelativeLayout search;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manager, null);
        rv= (RecyclerView) view.findViewById(R.id.rv);
        search = (RelativeLayout) view.findViewById(R.id.search);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),4));
        adapter = new VideoAdapter(VideoUtils.getAllVideo(getActivity()));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Video current = (Video) adapter.getItem(position);
                Uri uri = Uri.parse(current.getPath());
                intent.setDataAndType(uri, "video/mp4");
//                intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory() + "/3.mp4"), "video/mp4");
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.replace(R.id.container, new VideoSearchFragment());
                ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft2.addToBackStack(null);
                ft2.commit();
            }
        });
        return view;
    }


}
