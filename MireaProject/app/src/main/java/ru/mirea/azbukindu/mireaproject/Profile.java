package ru.mirea.azbukindu.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Profile extends Fragment {

    private EditText emailField;
    private EditText nameField;
    private EditText surnameField;
    private EditText phoneField;
    private String  USER_EMAIL = "USER_EMAIL";
    private String  USER_NAME = "USER_NAME";
    private String  USER_SURNAME = "USER_SURNAME";
    private String  USER_PHONE = "USER_PHONE";
    public Profile() {
    }

    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonSave = view.findViewById(R.id.saveBtn);
        buttonSave.setOnClickListener(v -> saveDataButton(v));

        emailField = view.findViewById(R.id.emailField);
        nameField = view.findViewById(R.id.nameField);
        surnameField = view.findViewById(R.id.surnameField);
        phoneField = view.findViewById(R.id.phoneField);

        SharedPreferences sharedPref = getContext().getSharedPreferences("profile_data", Context.MODE_PRIVATE);
        emailField.setText(sharedPref.getString(USER_EMAIL, ""));
        nameField.setText(sharedPref.getString(USER_NAME, ""));
        surnameField.setText(sharedPref.getString(USER_SURNAME, ""));
        phoneField.setText(sharedPref.getString(USER_PHONE, ""));


    }


    public void saveDataButton(View view){
        SharedPreferences sharedPref = getContext().getSharedPreferences("profile_data", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(USER_EMAIL, emailField.getText().toString());
        editor.putString(USER_NAME, nameField.getText().toString());
        editor.putString(USER_SURNAME, surnameField.getText().toString());
        editor.putString(USER_PHONE, phoneField.getText().toString());

        editor.apply();
    }
}