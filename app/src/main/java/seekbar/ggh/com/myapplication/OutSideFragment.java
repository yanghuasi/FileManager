package seekbar.ggh.com.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class OutSideFragment extends Fragment {
    private Button manager;
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
            final View view = inflater.inflate( R.layout.fragment_outside, null );
            //view.findViewById();
            manager = (Button) view.findViewById (R.id.manager);
            final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            final String name =Environment.getExternalStorageDirectory().getName();

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
                    if (fg == null) {
                        fg = new MutilFileFragment();
                        fragmentChanger.changeFragment(fg);

                    }

                }
            });
            return view;
        }


    }


