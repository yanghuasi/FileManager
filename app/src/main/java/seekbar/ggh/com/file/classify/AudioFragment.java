package seekbar.ggh.com.file.classify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import seekbar.ggh.com.file.classify.adapter.AudioAdapter;
import seekbar.ggh.com.file.classify.search.AudioSearchFragment;
import seekbar.ggh.com.myapplication.R;
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


}

