package ru.mirea.azbukindu.multiactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNewActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        EditText textOut = findViewById(R.id.textOut);
        intent.putExtra("value", textOut.getText().toString());
        startActivity(intent);

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