package ru.mirea.azbukindu.controllesson2;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

public class MyTimeDialogFragment extends TimePickerDialog {

    public MyTimeDialogFragment(Context context, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context, listener, hourOfDay, minute, is24HourView);
    }

    public MyTimeDialogFragment(Context context, int themeResId, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context, themeResId, listener, hourOfDay, minute, is24HourView);
    }
}
