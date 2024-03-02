package ru.mirea.azbukindu.notebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

public class MainActivity extends AppCompatActivity {
    private EditText fileNameField;
    private EditText quoteField;
    private static final int  REQUEST_CODE_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileNameField = findViewById(R.id.fileNameField);
        quoteField = findViewById(R.id.quoteField);

        if ((isExternalStorageReadable() || isExternalStorageWritable()) == false) {
            ActivityCompat.requestPermissions(this,  new  String[]
                            {android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public	void	writeFileToExternalStorage(View view)	{
        if (!isExternalStorageReadable()){
            return;
        }

        File	path	=	Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File	file	=	new File(path,	fileNameField.getText().toString());
        try	{
            FileOutputStream	fileOutputStream	=	new FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter output	=	new	OutputStreamWriter(fileOutputStream);
            //	Запись строки в файл
            output.write(quoteField.getText().toString());
            //	Закрытие потока записи
            output.close();

        }	catch	(IOException e)	{
            Log.w("ExternalStorage",	"Error	writing	"	+	file,	e);
        }
    }
    public	void	readFileFromExternalStorage(View view)	{
        if (!isExternalStorageWritable()){
            return;
        }

        File	path	=	Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
        File	file	=	new	File(path, fileNameField.getText().toString());
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
            quoteField.setText(stringLines);

            Log.w("ExternalStorage",	String.format("Read	from	file	%s	successful",	lines.toString()));
        }	catch	(Exception	e)	{
            Log.w("ExternalStorage",	String.format("Read	from	file	%s	failed",	e.getMessage()));
        }
    }
}