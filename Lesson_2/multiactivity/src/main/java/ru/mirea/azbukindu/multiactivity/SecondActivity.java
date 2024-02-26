package ru.mirea.azbukindu.multiactivity;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ru.mirea.azbukindu.multiactivity.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        String text = (String) getIntent().getSerializableExtra("value");

        TextView textView = findViewById(R.id.textView);

        textView.setText(text);

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