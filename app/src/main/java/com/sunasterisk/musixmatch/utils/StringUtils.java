package com.sunasterisk.musixmatch.utils;

import android.content.Context;
import android.widget.EditText;

import com.sunasterisk.musixmatch.R;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class StringUtils {
    public static String convertMilliSeconds(long milliSeconds) {
        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds))
        );
        return time;
    }

    public static String convertSize(long size) {
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        String stringSize = new DecimalFormat("#,##0.#")
                .format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
        return stringSize;
    }

    public static boolean isEmpty(Context context, EditText... editTexts) {
        boolean isEmpty = false;
        for (EditText editText : editTexts) {
            if (editText.getText().toString().isEmpty()) {
                isEmpty = true;
                editText.setError(context.getString(R.string.text_error_empty_value));
            }
        }
        return isEmpty;
    }
}
