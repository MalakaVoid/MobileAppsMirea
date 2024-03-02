package ru.mirea.azbukindu.mireaproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class FileProcessing extends Fragment {
    private EditText fileTextField;
    private EditText fileNameField;
    private EditText fileNameFieldToEncrypt;
    private Button decodeBtn;
    private FloatingActionButton submitBtn;
    private SecretKeySpec secret;

    public FileProcessing() {
    }

    public static FileProcessing newInstance(String param1, String param2) {
        FileProcessing fragment = new FileProcessing();
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
        return inflater.inflate(R.layout.fragment_file_processing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fileTextField = view.findViewById(R.id.fileTextField);
        fileNameField = view.findViewById(R.id.fileNameField);
        submitBtn = view.findViewById(R.id.submitBtn);
        decodeBtn = view.findViewById(R.id.decodeBtn);
        fileNameFieldToEncrypt = view.findViewById(R.id.fileNameFieldToEncrypt);

        submitBtn.setOnClickListener(v -> onSubmitBtn(v));
        decodeBtn.setOnClickListener(v -> onDecryptBtn(v));

        secret = new SecretKeySpec("passwordPassword".getBytes(), "AES");
    }

    public void onDecryptBtn(View view){
        String fileName = fileNameFieldToEncrypt.getText().toString();
        String  encrypted_text = readFileByFileName(fileName);
        String decrypted_text = getDecrypted(encrypted_text);
        Log.d("ERR", decrypted_text);
        createPopupDecrypt(decrypted_text);
    }
    public void onSubmitBtn(View view){
        String fileName = fileNameField.getText().toString();
        String fileText = fileTextField.getText().toString();
        writeToFileText(fileName, fileText);
        byte[] encryptedText = getEncrypted(fileText);
        writeToFileBytes(fileName, encryptedText);
        fileNameField.setText("");
        fileTextField.setText("");
        createPopupSave(fileName, fileText, new String(encryptedText, StandardCharsets.UTF_8));
    }

    public	void	writeToFileText(String fileName, String fileText)	{
        File path	=	Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File	file	=	new File(path,	fileName);
        try	{
            FileOutputStream	fileOutputStream	=	new FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter output	=	new	OutputStreamWriter(fileOutputStream);
            output.write(fileText);
            output.close();

        }	catch	(IOException e)	{
            Log.w("ExternalStorage",	"Error	writing	"	+	file,	e);
        }
    }
    public	void	writeToFileBytes(String fileName, byte[] fileText)	{
        File path	=	Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File	file	=	new File(path,	"encrypted_"+fileName);
        try	{
            FileOutputStream	fileOutputStream	=	new FileOutputStream(file.getAbsoluteFile());
            fileOutputStream.write(new String(fileText, StandardCharsets.UTF_8).getBytes());

        }	catch	(IOException e)	{
            Log.w("ExternalStorage",	"Error	writing	"	+	file,	e);
        }
    }

    public	String	readFileByFileName(String fileName)	{
        File	path	=	Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
        File	file	=	new	File(path, fileName);
        try	{
            FileInputStream fileInputStream	=	new	FileInputStream(file.getAbsoluteFile());
            InputStreamReader inputStreamReader	=	new	InputStreamReader(fileInputStream,	StandardCharsets.UTF_8);
            List<String> lines	=	new ArrayList<String>();
            BufferedReader reader	=	new	BufferedReader(inputStreamReader);
            String	line	=	reader.readLine();
            while	(line	!=	null)	{
                lines.add(line);
                line	=	reader.readLine();
            }
            String stringLines = String.join("\n", lines);
            return stringLines;

        }	catch	(Exception	e)	{
            Log.w("ExternalStorage",	String.format("Read	from	file	%s	failed",	e.getMessage()));
        }
        return "";
    }

    private byte[] getEncrypted(String text) {
        try {
            byte[] cipherText = Base64.encode(text.getBytes(), Base64.DEFAULT);

            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "".getBytes();
    }
    private String getDecrypted(String content) {
        try {
            String cipherText = new String(Base64.decode(content, Base64.DEFAULT));

            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    private void createPopupSave(String fileName, String fileText, String encryptedText) {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window, null);

        int width = 900;
        int height = 900;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        TextView textMain = popupView.findViewById(R.id.mainText);

        textMain.setText(String.format("Файл сохранен по пути Documents/%s, а также зашифрованный файл Documents/encrypted_%s.\n" +
                "Зашифрованный файл выглядит: %s", fileName, fileName, encryptedText));

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }
    private void createPopupDecrypt(String fileText) {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_window, null);

        int width = 900;
        int height = 900;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        TextView textMain = popupView.findViewById(R.id.mainText);

        textMain.setText(String.format("Расшифрованный файл:\n %s", fileText));

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }
}