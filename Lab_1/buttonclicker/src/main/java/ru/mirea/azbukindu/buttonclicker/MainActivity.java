package ru.mirea.azbukindu.buttonclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textOut;
    private Button btnWhoAmI;
    private Button btnItsNotMe;
    private CheckBox ckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textOut = findViewById(R.id.tvOut);
        btnWhoAmI = findViewById(R.id.btnWhoAmI);
        btnItsNotMe = findViewById(R.id.btnItsNotMe);
        ckOut = findViewById(R.id.ckOut);

        View.OnClickListener oclBtnWhoAmI = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textOut.setText("Мой номер по списку №1");
                changeCheckBox();
            }
        };


        btnWhoAmI.setOnClickListener(oclBtnWhoAmI);
    }
    public void oclBtnItsNotMe(View v){
        textOut.setText("Это не я нажал на кнопку");
        changeCheckBox();
    }
    public void changeCheckBox(){
        ckOut.setChecked(!ckOut.isChecked());
    }
}