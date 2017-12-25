package com.jaja.home.xmpp.util;

import android.graphics.BitmapFactory;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.EditText;

import java.io.IOException;

/**
 * Created by ${Terry} on 2017/12/22.
 */
public class BiaoQinUtils {


    public static void addBiaoQin(EditText e, String pName) {
        //获得输入光标的位置
        int selectionEnd = e.getSelectionEnd();
        //图文混排
        SpannableStringBuilder sp = new SpannableStringBuilder();
        sp.append("[" + pName + "]");
        try {
            sp.setSpan(new ImageSpan(e.getContext(), BitmapFactory.decodeStream(e.getContext().getAssets().open("p/" + pName))), 0, sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        e.getText().insert(selectionEnd, pName);
    }
}
