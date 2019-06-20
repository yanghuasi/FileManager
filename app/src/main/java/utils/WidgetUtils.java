package utils;

import android.text.method.DigitsKeyListener;
import android.widget.EditText;

public class WidgetUtils {
    //限制email输入字符串
    public static final String KEY_EMAIN = "0123456789abcdefghijklmnopqrstuvwxyz\n" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ`¬!\"£$%^*()~=#{}[];':,./?/*-_+&#060;&#062;&#064;&#038;";
    public static void setEditTextInputString(EditText mEditText, String key){
        mEditText.setKeyListener(DigitsKeyListener.getInstance(key));

    }
}
