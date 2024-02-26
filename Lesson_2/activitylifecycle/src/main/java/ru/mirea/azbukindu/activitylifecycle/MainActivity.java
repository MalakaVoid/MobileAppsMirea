package ru.mirea.azbukindu.activitylifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    /*
        Ответы на вопросы
        1 НЕТ
        2 НЕТ
        3 НЕТ
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("TAG", "onCreate()");
    }
    @Override
    protected void onStart(){
        super.onStart();
    };
    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);
    };
    @Override
    protected void onRestart(){
        super.onRestart();
    };
    @Override
    protected void onResume(){
        super.onResume();
    };
    @Override
    protected void onPause(){
        super.onPause();
    };
    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState){
        super.onSaveInstanceState(saveInstanceState);
    };
    @Override
    protected void onStop(){
        super.onStop();
    };
    @Override
    protected void onDestroy(){
        super.onDestroy();
    };
}