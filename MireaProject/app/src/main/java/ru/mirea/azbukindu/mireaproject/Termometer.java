package ru.mirea.azbukindu.mireaproject;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Termometer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Termometer extends Fragment implements SensorEventListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ProgressBar progressBar;
    private ImageView image;
    private TextView temperatureText;
    private TextView noticeText;

    private SensorManager sensorManager;
    private Sensor thermometer;
    private int currentImage;

    public Termometer() {
        // Required empty public constructor
    }

    public static Termometer newInstance(String param1, String param2) {
        Termometer fragment = new Termometer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_termometer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        image = view.findViewById(R.id.tempImage);
        temperatureText = view.findViewById(R.id.temperature);
        noticeText = view.findViewById(R.id.notice);

        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        thermometer = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        currentImage = 1;
    }


    @Override
    public final void onSensorChanged(SensorEvent event) {
        float temperature = event.values[0];
        temperatureText.setText(temperature + " ℃");

        if (temperature < 0){
            if (currentImage ==1){
                return;
            }
            image.setImageResource(R.drawable.cold);
            noticeText.setText("Оденьтесь потеплее!");
            currentImage =1;
            return;
        }
        if (temperature >0 && temperature < 25){
            if (currentImage ==2){
                return;
            }
            image.setImageResource(R.drawable.warm);
            noticeText.setText("На улице прохладно!");
            currentImage =2;
            return;
        }
        if (temperature > 25){
            if (currentImage ==3){
                return;
            }
            image.setImageResource(R.drawable.hot);
            noticeText.setText("Жара, не забудьте взять головные уборы!");
            currentImage =3;
            return;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, thermometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this, thermometer);
    }
}