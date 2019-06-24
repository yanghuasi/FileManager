package seekbar.ggh.com.file.manager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import seekbar.ggh.com.myapplication.R;

public class AddDialogFragment extends Fragment {
    private RelativeLayout scan;
    private RelativeLayout newFolder;
    private RelativeLayout uploadPhoto;
    private RelativeLayout uploadVideo;
    private RelativeLayout uploadDoc;
    private RelativeLayout uploadMusic;
    private RelativeLayout uploadOther;
    private RelativeLayout newNotes;
    private ImageView cancel;


    public static AddDialogFragment newInstance(Context context) {
//        context.startActivity(new Intent().putExtra("path",filepath));
        AddDialogFragment fg = new AddDialogFragment();
        return fg;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_dialog_add, null);
        // 获取所有资源图片
//        mPicturePaths = FileUtil.getAssetPicPath(getActivity());
        scan = view.findViewById(R.id.scan);
        newFolder = view.findViewById(R.id.newFolder);
        uploadPhoto = view.findViewById(R.id.upload_photo);
        uploadVideo = view.findViewById(R.id.upload_video);
        uploadDoc = view.findViewById(R.id.upload_doc);
        uploadMusic = view.findViewById(R.id.upload_music);
        uploadOther = view.findViewById(R.id.upload_other);
        newNotes = view.findViewById(R.id.newNotes);
        cancel = view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();//suport.v4包
            }
        });

        return view;
    }
}

