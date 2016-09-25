package com.app.hrms.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class Utils {

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String convertCredit(double value) {
        NumberFormat n = NumberFormat.getNumberInstance(Locale.CHINA);
        return n.format(value);
    }

    public static void showToast(Context context,String text){
        Toast toast = Toast.makeText(context, text,Toast.LENGTH_LONG);
        toast.show();
    }
}
