package seekbar.ggh.com.file.classify;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;

import seekbar.ggh.com.file.R;
import seekbar.ggh.com.file.classify.adapter.PhotoAdapter;
import seekbar.ggh.com.file.classify.search.PhotoSearchFragment;
import seekbar.ggh.com.file.manager.SearchFragemnt;

import utils.FileManager;
import utils.PhotoUtils;

import static android.content.ContentValues.TAG;

public class PhotoFragment extends Fragment {
    private static final int CROP_IMAGE = 200;
    private RecyclerView rv;
    private PhotoAdapter adapter;
    private RelativeLayout search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manager, null);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        search = (RelativeLayout) view.findViewById(R.id.search);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        adapter = new PhotoAdapter(PhotoUtils.getSystemPhotoList(getActivity()));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                String filePath = (String) adapter.getItem(position);
                Uri mUri = Uri.parse(filePath);


//                Uri imageUri = Uri.parse(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/sina/weibo/weibo/img-781355347939d43dbbd99c952e1e7bbd.jpg");
                if(mUri != null) {
                    setImageCrop(mUri);
                }

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.replace(R.id.container, new PhotoSearchFragment());
                ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft2.addToBackStack(null);
                ft2.commit();
            }
        });
        return view;
    }
private void setImageCrop(Uri uri){
    Intent intent = new Intent( "com.android.camera.action.CROP" );
    Log.i( TAG, "startImageCrop: " + "执行到压缩图片了" + "uri is " + uri );
    intent.setDataAndType( uri, "image/*" );//设置Uri及类型

    //添加下面两个语句
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

    intent.putExtra( "crop", "true" );//
    intent.putExtra( "aspectX", 1 );//X方向上的比例
    intent.putExtra( "aspectY", 1 );//Y方向上的比例
    intent.putExtra( "outputX", 150 );//裁剪区的X方向宽
    intent.putExtra( "outputY", 150 );//裁剪区的Y方向宽
    intent.putExtra( "scale", true );//是否保留比例
    intent.putExtra( "outputFormat", Bitmap.CompressFormat.PNG.toString() );
    intent.putExtra( "return-data", true );//是否将数据保留在Bitmap中返回dataParcelable相应的Bitmap数据
    Log.i( TAG, "startImageCrop: " + "即将跳到剪切图片" );
    startActivityForResult( intent, CROP_IMAGE );
}


    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            Uri uri = data.getData();
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getColumnCount(); i++)
            {// 取得图片uri的列名和此列的详细信息
                System.out.println(i + "-" + cursor.getColumnName(i) + "-" + cursor.getString(i));
            }
        }
    }

}
