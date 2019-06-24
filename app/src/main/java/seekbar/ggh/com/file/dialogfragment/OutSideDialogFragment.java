package seekbar.ggh.com.file.dialogfragment;

import android.app.Activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import seekbar.ggh.com.file.MainActivity;
import seekbar.ggh.com.file.manager.MutilFileFragment;
import seekbar.ggh.com.myapplication.R;


public class OutSideDialogFragment extends Fragment {
    private Button album;
    private Button background;
    private MultiBackgoundFragment mMultiBackgoundFragment;
    private ImageFilesFragment mPhotoSeletordialog;
    /**
     * Activity跳转到Fragment第1步
     */
    MainActivity.FragmentChanger fragmentChanger;
    MutilFileFragment fg;
    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();//初始化上下文
        /**
         * Activity跳转到Fragment第2步
         */
        fragmentChanger = MainActivity.fragmentChanger;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_outside_dialogfragment, null);
        //view.findViewById();
        album = (Button) view.findViewById(R.id.album);
        background = (Button) view.findViewById(R.id.background);
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path=Environment.getExternalStorageDirectory().getAbsolutePath();
                String name=Environment.getExternalStorageDirectory().getName();
                mPhotoSeletordialog = ImageFilesFragment.newInstance(path,name);
                mPhotoSeletordialog.show(getFragmentManager(), "");


            }
        });
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMultiBackgoundFragment = new MultiBackgoundFragment();
                mMultiBackgoundFragment.show(getFragmentManager(), "");


            }
        });
        return view;
    }


}


