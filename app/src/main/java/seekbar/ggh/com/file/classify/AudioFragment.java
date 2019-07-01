package seekbar.ggh.com.file.classify;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import seekbar.ggh.com.file.dialogfragment.ImageFilesFragment;
import seekbar.ggh.com.file.manager.ImgFolderBean;
import seekbar.ggh.com.file.manager.add.AddDialogFragment;
import utils.AudioUtils;

public class AudioFragment extends Fragment {
    private RecyclerView rv;
    private AudioAdapter audioAdapter;
    private RelativeLayout search;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manager, null);
        rv=view.findViewById(R.id.rv);
        search=view.findViewById(R.id.search);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        audioAdapter = new AudioAdapter(AudioUtils.getAllSongs(getActivity()));
        rv.setAdapter(audioAdapter);
        audioAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Song song = audioAdapter.getItem(position);
//                String audioPath = song.getFileUrl();
//                playAudio(audioPath);
                try {
                    PlaybackFragment mPlaybackFragment = PlaybackFragment.newInstance(audioAdapter.getItem(position));
                    mPlaybackFragment.show(getFragmentManager(), "");
//                    PlaybackFragment playbackFragment =
//                            (PlaybackFragment) new PlaybackFragment().newInstance(audioAdapter.getItem(position));
//                    FragmentTransaction ft2 = getFragmentManager().beginTransaction();
////                    ft2.replace(R.id.container, PlaybackFragment.newInstance(audioAdapter.getItem(position));
//                    ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                    ft2.addToBackStack(null);
//                    ft2.commit();
//
//                    playbackFragment.show(ft2, "dialog_playback");

                } catch (Exception e) {
                    Log.e("1111", "exception", e);
                }
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
    public void playAudio(String audioPath) {
//        Intent mIntent = new Intent();
//        mIntent.setAction(android.content.Intent.ACTION_VIEW);
//        Uri uri = Uri.parse(audioPath);//替换成audiopath
//        mIntent.setDataAndType(uri , "audio/mp3");
//       startActivity(mIntent);

        Intent intent_music = new Intent(Intent.ACTION_PICK);
			intent_music.setDataAndType(Uri.parse(audioPath),"vnd.android.cursor.dir/playlist");
			intent_music.putExtra("withtabs", true); // 显示tab选项卡
			intent_music.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			Intent j =Intent.createChooser(intent_music, "Choose an application to open with:");
			if (j == intent_music) {
				startActivity(j);
			} else {
				Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
				startActivity(intent);
			}

    }
}

