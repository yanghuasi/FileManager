package seekbar.ggh.com.file.system;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Contacts;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import seekbar.ggh.com.file.MainActivity;
import seekbar.ggh.com.file.R;

import utils.FileManager;

public class SystemIntentFragment extends Fragment {
    private TextView chosePic;
    private TextView choseVideo;
    private TextView recording;
    private TextView takeVideo;
    private TextView takePhoto;
    private ImageView imageView;
    private TextView openPhoneList;
    private TextView addPhoneList;
    private TextView installApk;
    private TextView uninstallApk;
    private TextView play;
    private TextView email;
    private TextView letter;
    private TextView letterMuilt;
    private TextView dail;
    private TextView map;
    private TextView gallery;
    private TextView webside;
    private TextView google;
    private TextView road;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_system_intent, null);
        //view.findViewById();
        chosePic = view.findViewById(R.id.chosePic);
        imageView = view.findViewById(R.id.image);
        choseVideo = view.findViewById(R.id.choseVideo);
        recording = view.findViewById(R.id.recording);
        takeVideo = view.findViewById(R.id.takeVideo);
        takePhoto = view.findViewById(R.id.takePhoto);
        openPhoneList = view.findViewById(R.id.openPhoneList);
        addPhoneList= view.findViewById(R.id.addPhoneList);
        installApk = view.findViewById(R.id.installApk);
        uninstallApk = view.findViewById(R.id.uninstallApk);
        play = view.findViewById(R.id.play);
        email= view.findViewById(R.id.email);
        letter = view.findViewById(R.id.letter);
        letterMuilt = view.findViewById(R.id.letterMuilt);
        dail = view.findViewById(R.id.dail);
        map= view.findViewById(R.id.map);
        google = view.findViewById(R.id.google);
        webside = view.findViewById(R.id.webside);
        gallery = view.findViewById(R.id.gallery);
        road= view.findViewById(R.id.road);
        road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri =Uri.parse("http://maps.google.com/maps?f=dsaddr=startLat%20startLng&daddr=endLat%20endLng&hl=en");
                Intent it = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(it);
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri imageUri = Uri.parse(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/sina/weibo/weibo/img-781355347939d43dbbd99c952e1e7bbd.jpg");
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(imageUri, "image/*");
                // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
                intent.putExtra("crop", "true");
                //该参数可以不设定用来规定裁剪区的宽高比
                intent.putExtra("aspectX", 2);
                intent.putExtra("aspectY", 1);
                //该参数设定为你的imageView的大小
                intent.putExtra("outputX", 600);
                intent.putExtra("outputY", 300);
                intent.putExtra("scale", true);
                //是否返回bitmap对象
                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出图片的格式
                intent.putExtra("noFaceDetection", true); // 头像识别
                startActivityForResult(intent, 7);


            }
        });
        webside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri =Uri.parse("http://www.google.com");
                Intent it = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(it);
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY,"搜索内容");
                startActivity(intent);

            }
        });
        letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //方法1：
                Intent it = new Intent(Intent.ACTION_VIEW);
                it.putExtra("sms_body", "TheSMS text");
                it.setType("vnd.android-dir/mms-sms");
                startActivity(it);

//                //方法2：
//                Uri uri =Uri.parse("smsto:0800000123");
//                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
//                it.putExtra("sms_body", "TheSMS text");
//                startActivity(it);
//
//                //方法三：
//                String body="this is sms demo";
//                Intent mmsintent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("smsto", number, null));
//                mmsintent.putExtra(Messaging.KEY_ACTION_SENDTO_MESSAGE_BODY,body);
//                mmsintent.putExtra(Messaging.KEY_ACTION_SENDTO_COMPOSE_MODE,true);
//                mmsintent.putExtra(Messaging.KEY_ACTION_SENDTO_EXIT_ON_SENT,true);
//                startActivity(mmsintent);
            }
        });
        letterMuilt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String url="https://www.cnblogs.com/rayray/archive/2013/03/18/Android_MMS.html";
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));// uri为你的附件的uri
                intent.putExtra("subject", "it's subject"); //彩信的主题
                intent.putExtra("address", "10086"); //彩信发送目的号码
                intent.putExtra("sms_body", "it's content"); //彩信中文字内容
                intent.putExtra(Intent.EXTRA_TEXT, "it's EXTRA_TEXT");
                intent.setType("image/*");// 彩信附件类型
                intent.setClassName("com.android.mms","com.android.mms.ui.ComposeMessageActivity");
                startActivity(intent);
            }
        });
        dail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri =Uri.parse("tel:xxxxxx");
                Intent it = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(it);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("geo:38.899533,-77.036476");
                Intent it = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(it);

            }
        });
        installApk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apkPath = getActivity().getPackageResourcePath();

//                String apkPath=Environment.getDataDirectory().getAbsolutePath()+"/data/app/com.anjuke.android.app-DHIqvyNt4UeYrd4DDcFqxA==/base.apk";
                try {
                    /**
                     * provider
                     * 处理android 7.0 及以上系统安装异常问题
                     */

                    File file = new File(apkPath);
                    Intent install = new Intent();
                    install.setAction(Intent.ACTION_VIEW);
                    install.addCategory(Intent.CATEGORY_DEFAULT);
                    install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri apkUri = FileProvider.getUriForFile(context, "com.chao.app.fileprovider", file);//在AndroidManifest中的android:authorities值
                        Log.d("======", "apkUri=" + apkUri); install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                        install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    } else {
                        install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    }
                    startActivity(install);
                } catch (Exception e) {
                    Log.d("======", e.getMessage());
                    Toast.makeText(getActivity(), "文件解析失败", Toast.LENGTH_SHORT).show();
                    FileManager.deleteFile(apkPath);
                }
            }
        });
        uninstallApk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("seekbar.ggh.com.file"));//"package:包名"
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri =Uri.parse("mailto:xxx@abc.com");
                Intent it1 = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(it1);

                Intent it2 = new Intent(Intent.ACTION_SEND);
                it2.putExtra(Intent.EXTRA_EMAIL,"me@abc.com");
                it2.putExtra(Intent.EXTRA_TEXT, "Theemail body text");
                it2.setType("text/plain");
                startActivity(Intent.createChooser(it2,"Choose Email Client"));

                Intent it3=new Intent(Intent.ACTION_SEND);
                String[] tos={"me@abc.com"};
                String[]ccs={"you@abc.com"};
                it3.putExtra(Intent.EXTRA_EMAIL, tos);
                it3.putExtra(Intent.EXTRA_CC, ccs);
                it3.putExtra(Intent.EXTRA_TEXT, "Theemail body text");
                it3.putExtra(Intent.EXTRA_SUBJECT, "Theemail subject text");
                it3.setType("message/rfc822");
                startActivity(Intent.createChooser(it3,"Choose Email Client"));

                Intent it = new Intent(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_SUBJECT, "Theemail subject text");
                it.putExtra(Intent.EXTRA_STREAM,"file:///sdcard/mysong.mp3");
                it.setType("audio/mp3");
                startActivity(Intent.createChooser(it,"Choose Email Client"));
            }
        });
        choseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //视频
                Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                innerIntent.setType("video/*"); //String VIDEO_UNSPECIFIED = "video/*";
                Intent wrapperIntent = Intent.createChooser(innerIntent, null);
                startActivityForResult(wrapperIntent, 2);
            }
        });
        chosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //照片
                Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); //"android.intent.action.GET_CONTENT"
                innerIntent.setType("image/*"); //查看类型 String IMAGE_UNSPECIFIED = "image/*";
                Intent wrapperIntent = Intent.createChooser(innerIntent, null);
                startActivityForResult(wrapperIntent, 1);
            }
        });
        recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //录音
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/amr"); //String AUDIO_AMR = "audio/amr";
                intent.setClassName("com.android.soundrecorder", "com.android.soundrecorder.SoundRecorder");
                startActivityForResult(intent, 3);
            }
        });
        addPhoneList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                intent.setType(Contacts.People.CONTENT_ITEM_TYPE);
                intent.putExtra(Contacts.Intents.Insert.NAME, "My Name");
                intent.putExtra(Contacts.Intents.Insert.PHONE, "+1234567890");
                intent.putExtra(Contacts.Intents.Insert.PHONE_TYPE,Contacts.PhonesColumns.TYPE_MOBILE);
                intent.putExtra(Contacts.Intents.Insert.EMAIL, "com@com.com");
                intent.putExtra(Contacts.Intents.Insert.EMAIL_TYPE, Contacts.ContactMethodsColumns.TYPE_WORK);
                startActivity(intent);
            }
        });
        openPhoneList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1
//                Intent i = new Intent();
//                i.setAction(Intent.ACTION_GET_CONTENT);
//                i.setType("vnd.android.cursor.item/phone");
//                startActivityForResult(i, 6);
                //2
                Uri uri = Uri.parse("content://contacts/people");
                Intent it = new Intent(Intent.ACTION_PICK, uri);
                startActivityForResult(it, 6);
            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 0);
                }
            }
        });
        takeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍摄视频
                int durationLimit = 60;
                //SystemProperties.getInt("ro.media.enc.lprof.duration", 60);
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 4);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, durationLimit);
                startActivityForResult(intent, 5);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                File file=new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/sina/weibo/weibo/img-781355347939d43dbbd99c952e1e7bbd.jpg");
                File file = new File("img-781355347939d43dbbd99c952e1e7bbd.jpg");
                Intent it = new Intent(Intent.ACTION_VIEW);

                Uri mUri = Uri.parse(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/sina/weibo/weibo/img-781355347939d43dbbd99c952e1e7bbd.jpg");

                it.setDataAndType(mUri, "image/*");

                startActivity(it);

            }
        });
        return view;
    }
}
