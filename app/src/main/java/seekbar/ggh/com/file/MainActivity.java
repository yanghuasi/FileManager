package seekbar.ggh.com.file;

import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.wkp.runtimepermissions.callback.PermissionCallBack;
import com.wkp.runtimepermissions.util.RuntimePermissionUtil;

import seekbar.ggh.com.myapplication.R;


public class MainActivity extends AppCompatActivity {
    //    private Button classify;
//    private Button manager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        classify = (Button) findViewById(R.id.classify);
//        classify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        manager = (Button) findViewById(R.id.manager);
//        manager.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getFragmentManager()   //
//                        .beginTransaction()
//                        .add(R.id.container ,new OutSideFragment())   // 此处的R.id.fragment_container是要盛放fragment的父容器
//                        .commit();
////                String path =Environment.getExternalStorageDirectory().getAbsolutePath();
////                String name =Environment.getExternalStorageDirectory().getName();
////                ManagerFragment filesFragment = ManagerFragment.newInstance(path,name);
//////                filesFragment.setOnItemClickListener(this);
//////                if (mChoseImageCallback!=null){
//////                    filesFragment.setChoseImageCallback(mChoseImageCallback);
//////                }
////                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container, filesFragment).commit();
//            }
//        });
//    }
    private Button login;
    /**
     * Activity跳转到Fragment第1步
     */
    public static FragmentChanger fragmentChanger;//Fragment切换接口实现
    private Fragment oldFragment = null;
    private OutSideFragment fg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getFragmentManager()    //
//                .beginTransaction()
//                .add(R.id.container, new OutSideFragment())   // 此处的R.id.fragment_container是要盛放fragment的父容器
//                .commit();

        /**
         * Activity跳转到Fragment第2步
         */
        fragmentChanger = new FragmentChanger() {
            @Override
            public void changeFragment(Fragment fragment) {
                if (oldFragment != fragment) oldFragment = fragment;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction
                        .replace(R.id.container, fragment)
                        .commit();
            }
        };
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fg = new OutSideFragment();
        fragmentTransaction.add(R.id.container, fg, "outside").commit();
//        btn=(Button)findViewById(R.id.btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //显示选择相册的dialog
//                MultiImageDialogFragment editNameDialogFragment = MultiImageDialogFragment.newInstance("名字");
//                editNameDialogFragment.show(getSupportFragmentManager(), "edit");
//            }
//        });
            //当手机版本好大于6.0时，需要动态申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            applyPermission();
        }
    }
    /**
     * Activity跳转到Fragment第3步
     */
    public interface FragmentChanger {
        void changeFragment(Fragment fragment);
    }
    /**
     * 点击获取权限
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void applyPermission() {
        //权限检查，回调是权限申请结果
        RuntimePermissionUtil.checkPermissions(this, RuntimePermissionUtil.STORAGE, new PermissionCallBack() {
            @Override
            public void onCheckPermissionResult(boolean hasPermission) {
                if (hasPermission) {
                    //直接做具有权限后的操作
                    Toast.makeText(MainActivity.this, "权限申请成功", Toast.LENGTH_SHORT).show();

                }else {
                    //显示权限不具备的界面
                    Toast.makeText(MainActivity.this, "权限申请失败", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}

