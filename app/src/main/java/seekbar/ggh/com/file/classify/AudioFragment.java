package seekbar.ggh.com.file.classify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import seekbar.ggh.com.file.R;
import seekbar.ggh.com.file.bean.Music;
import seekbar.ggh.com.file.bean.Song;
import seekbar.ggh.com.file.bean.Video;
import seekbar.ggh.com.file.classify.adapter.AudioAdapter;
import seekbar.ggh.com.file.classify.search.AudioSearchFragment;
import seekbar.ggh.com.file.manager.ImgFolderBean;
import utils.AudioUtils;

public class AudioFragment extends Fragment {
    private RecyclerView rv;
    private AudioAdapter adapter;
    private RelativeLayout search;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manager, null);
        rv=view.findViewById(R.id.rv);
        search=view.findViewById(R.id.search);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AudioAdapter(AudioUtils.getAllSongs(getActivity()));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
                startActivity(intent);
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

    /**
     * 播放指定名称的歌曲
     * @param audioPath 指定默认播放的音乐
     */
    public static void playAudio(String audioPath){
        Intent mIntent = new Intent();
        mIntent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file:///sdcard/a.mp3");替换成audiopath
        mIntent.setDataAndType(uri , "audio/mp3");

    }

}

