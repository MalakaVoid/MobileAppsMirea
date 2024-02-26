package ru.mirea.azbukindu.toastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showMessage(View view){
        EditText textOut = findViewById(R.id.textOut);
        Toast toast = Toast.makeText(getApplicationContext(),
                String.format(Locale.getDefault(),
                        "СТУДЕНТ № %d ГРУППА %s Количество символов - %d",
                        1, "БСБО-10-21", textOut.getText().length()),
                Toast.LENGTH_SHORT);
        toast.show();
    }
}