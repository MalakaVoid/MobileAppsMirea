package ru.mirea.azbukindu.mireaproject;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class Microphone extends Fragment {
    private boolean isWork = false;
    private  static  final  int  REQUEST_CODE_PERMISSION  =  200;

    private Button toggleButton;
    private Button playButton;

    private MediaRecorder recorder  =  null;
    private MediaPlayer player  =  null;
    boolean  isStartRecording  =  true;
    boolean  isStartPlaying  =  true;

    String fileName;
    public Microphone() {

    }

    public static Microphone newInstance(String param1, String param2) {
        Microphone fragment = new Microphone();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_microphone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toggleButton = view.findViewById(R.id.recordButton);
        playButton = view.findViewById(R.id.playButton);

        playButton.setEnabled(false);

        fileName = (new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "/audiorecordtest.3gp")).getAbsolutePath();

        int  audioRecordPermissionStatus  =  ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.RECORD_AUDIO);
        int  storagePermissionStatus  =  ContextCompat.checkSelfPermission(getContext(),  android.Manifest.permission.
                WRITE_EXTERNAL_STORAGE);
        if  (audioRecordPermissionStatus  ==  PackageManager.PERMISSION_GRANTED  &&  storagePermissionStatus
                ==  PackageManager.PERMISSION_GRANTED)  {
            isWork  =  true;
        }  else  {
            //  Выполняется  запрос  к  пользователь  на  получение  необходимых  разрешений
            requestPermissions(new  String[]  {android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE},  REQUEST_CODE_PERMISSION);
        }

        toggleButton.setOnClickListener(view1 -> {
            if (isStartRecording) {
                toggleButton.setText("Stop  recording");
                playButton.setEnabled(false);
                startRecording();
            } else {
                toggleButton.setText("Start  recording");
                playButton.setEnabled(true);
                stopRecording();

            }

            isStartRecording = !isStartRecording;
        });

        playButton.setOnClickListener(view1 -> {
            if (isStartPlaying) {
                playButton.setText("Stop playing");
                toggleButton.setEnabled(false);
                startPlaying();
            } else {
                playButton.setText("Start playing");
                toggleButton.setEnabled(false);
                stopPlaying();
            }

            isStartPlaying = !isStartPlaying;
        });
    }

    @Override
    public  void  onRequestPermissionsResult(int  requestCode, @NonNull String[]  permissions, @NonNull  int[]
            grantResults)  {
        //  производится проверка  полученного  результата  от  пользователя  на  запрос  разрешения  Camera
        super.onRequestPermissionsResult(requestCode,  permissions,  grantResults);
        switch  (requestCode){
            case  REQUEST_CODE_PERMISSION:
                isWork = grantResults[0] ==  PackageManager.PERMISSION_GRANTED;
                break;
        }
        if  (!isWork) getActivity().finish();
    }
    private  void startRecording()  {
        recorder = new MediaRecorder();
        recorder.setAudioChannels(1);
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try  {
            recorder.prepare();
        }  catch  (IOException e)  {
            Log.e("ERRORS",  "prepare()  failed");
        }
        recorder.start();
    }

    private  void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private  void startPlaying()  {
        player  = new  MediaPlayer();
        try  {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException  e)  {
            Log.e("ERRORS",  "prepare()  failed");
        }
    }

    private  void stopPlaying()  {
        player.release();
        player = null;
    }
}