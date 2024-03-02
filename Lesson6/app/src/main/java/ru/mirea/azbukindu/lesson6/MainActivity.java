package ru.mirea.azbukindu.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText groupNumField;
    private EditText numInGroupField;
    private EditText favouriteFilm;
    private String  GROUP_NUM_ID = "GROUP_NUM";
    private String  NUM_IN_GROUP = "NUM_IN_GROUP";
    private String  FAVOURITE_FILM = "FAVOURITE_FILM";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        groupNumField = findViewById(R.id.numGroup);
        numInGroupField = findViewById(R.id.numInGroup);
        favouriteFilm = findViewById(R.id.favouriteFilm);

        SharedPreferences sharedPref = getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);
        groupNumField.setText(sharedPref.getString(GROUP_NUM_ID, ""));
        numInGroupField.setText(sharedPref.getString(NUM_IN_GROUP, ""));
        favouriteFilm.setText(sharedPref.getString(FAVOURITE_FILM, ""));

    }

    public void saveDataButton(View view){
        SharedPreferences sharedPref = getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(GROUP_NUM_ID, groupNumField.getText().toString());
        editor.putString(NUM_IN_GROUP, numInGroupField.getText().toString());
        editor.putString(FAVOURITE_FILM, favouriteFilm.getText().toString());

        editor.apply();
    }
}