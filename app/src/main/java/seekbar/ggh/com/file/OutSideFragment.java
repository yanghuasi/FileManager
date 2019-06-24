package seekbar.ggh.com.file;

import android.app.Activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import seekbar.ggh.com.myapplication.R;
import seekbar.ggh.com.file.classify.ClassifyFragment;
import seekbar.ggh.com.file.dialogfragment.OutSideDialogFragment;
import seekbar.ggh.com.file.manager.MutilFileFragment;


public class OutSideFragment extends Fragment {
    private Button manager;
    private Button dialog;
    private Button classify;
    /**
     * Activity跳转到Fragment第1步
     */
    MainActivity.FragmentChanger fragmentChanger;
    MutilFileFragment fg;
    OutSideDialogFragment outSideFragment;
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
            final View view = inflater.inflate( R.layout.fragment_outside, null );
            //view.findViewById();
            manager = (Button) view.findViewById (R.id.manager);
            dialog= (Button) view.findViewById (R.id.dialog);
            classify= (Button) view.findViewById (R.id.classify);
            final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            final String name =Environment.getExternalStorageDirectory().getName();
            classify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                    ft2.replace(R.id.container, new ClassifyFragment());
                    ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft2.addToBackStack(null);
                    ft2.commit();
                }
            });
            manager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    //获取fragment的实例
//                    MutilFileFragment filesFragment = MutilFileFragment.newInstance(path,name);
//                    //获取Fragment的管理器
//                    FragmentManager fragmentManager=getFragmentManager();
//                    //开启fragment的事物,在这个对象里进行fragment的增删替换等操作。
//                    FragmentTransaction ft=fragmentManager.beginTransaction();
//                    //跳转到fragment，第一个参数为所要替换的位置id，第二个参数是替换后的fragment
//                    ft.replace(R.id.container,filesFragment);
//                    //提交事物
//                    ft.commit();
                    /**
                     * Activity跳转到Fragment第3步
                     */
                    FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                    ft2.replace(R.id.container, new MutilFileFragment());
                    ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft2.addToBackStack(null);
                    ft2.commit();
//
//                    if (fg == null) {
//                        fg = new MutilFileFragment();
//                        fragmentChanger.changeFragment(fg);
//
//
//                    }

                }
            });
            dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (outSideFragment == null) {
                        outSideFragment = new OutSideDialogFragment();
                        fragmentChanger.changeFragment(outSideFragment);

                    }
                }
            });
            return view;
        }


    }


