package ru.mirea.azbukindu.controllesson2;

import android.app.DatePickerDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDateDialogFragment extends DatePickerDialog {

    public MyDateDialogFragment(@NonNull Context context) {
        super(context);
    }

    public MyDateDialogFragment(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public MyDateDialogFragment(@NonNull Context context, @Nullable OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }

    public MyDateDialogFragment(@NonNull Context context, int themeResId, @Nullable OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
    }
}
