package seekbar.ggh.com.file.classify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import seekbar.ggh.com.file.classify.adapter.AudioAdapter;
import seekbar.ggh.com.file.classify.adapter.VideoAdapter;
import seekbar.ggh.com.myapplication.R;
import utils.AudioUtils;
import utils.FileManager;

public class VideoFragemnt extends Fragment {
    private RecyclerView rv;
    private VideoAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manager, null);
        rv=view.findViewById(R.id.rv);

        rv.setLayoutManager(new GridLayoutManager(getActivity(),5));
        adapter = new VideoAdapter(FileManager.getVideos());
        rv.setAdapter(adapter);
        return view;
    }


}
