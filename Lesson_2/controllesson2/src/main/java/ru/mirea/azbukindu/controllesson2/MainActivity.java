package ru.mirea.azbukindu.controllesson2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void timePicker(View view) {
        MyTimeDialogFragment fragment = new MyTimeDialogFragment(this, (timePicker, hours, minutes)->{
            Log.d("TIME TAG", String.format("Chosen: %d:%d", hours, minutes));
        }, 10, 20, true);
        fragment.show();
        snakeBarDo(view, "TIME PICKER");
    }

    public void datePicker(View view) {
        MyDateDialogFragment fragment = new MyDateDialogFragment(this, (datePicker, year, month, day)->{

        }, 2022, 12, 28);
        fragment.show();
        snakeBarDo(view, "DATE PICKER");
    }

    public void progressPicker(View view) {
        MyProgressDialogFragment fragment = new MyProgressDialogFragment(this);
        fragment.setTitle("TITLE");
        fragment.setMessage("Loading...");
        fragment.show();

        snakeBarDo(view, "PROGRESS PICKER");
    }

    private void snakeBarDo(View view,String text){
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }
}