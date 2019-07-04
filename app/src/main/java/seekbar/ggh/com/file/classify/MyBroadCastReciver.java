package seekbar.ggh.com.file.classify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import seekbar.ggh.com.file.bean.WpsModel;

public class MyBroadCastReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {
            case WpsModel.Reciver.ACTION_BACK://返回键广播
                System.out.println(WpsModel.Reciver.ACTION_BACK);
                break;
            case WpsModel.Reciver.ACTION_CLOSE://关闭文件时候的广播
                System.out.println(WpsModel.Reciver.ACTION_CLOSE);

                break;
            case WpsModel.Reciver.ACTION_HOME://home键广播
                System.out.println(WpsModel.Reciver.ACTION_HOME);

                break;
            case WpsModel.Reciver.ACTION_SAVE://保存广播
                System.out.println(WpsModel.Reciver.ACTION_SAVE);

                break;
            default:
                break;
        }

    }

}

