package ru.mirea.azbukindu.mireaproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import ru.mirea.azbukindu.mireaproject.databinding.ActivityMainBinding;

public class MapActivity extends AppCompatActivity {
    private MapView mapView = null;
    private ActivityMainBinding binding;
    private MyLocationNewOverlay locationNewOverlay;
    private Boolean isWork =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        int locationPermissionStatus = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (locationPermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        mapView = findViewById(R.id.mapView);

        mapView.setZoomRounding(true);
        mapView.setMultiTouchControls(true);

        float lattitude = getIntent().getExtras().getFloat("latitude");
        float longitude = getIntent().getExtras().getFloat("longitude");

        IMapController mapController = mapView.getController();
        mapController.setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(lattitude, longitude);
        mapController.setCenter(startPoint);

        locationNewOverlay = new MyLocationNewOverlay(new
                GpsMyLocationProvider(getApplicationContext()), mapView);
        locationNewOverlay.enableMyLocation();
        mapView.getOverlays().add(this.locationNewOverlay);

        CompassOverlay compassOverlay = new CompassOverlay(getApplicationContext(), new
                InternalCompassOrientationProvider(getApplicationContext()), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        final Context context = this.getApplicationContext();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mapView.getOverlays().add(scaleBarOverlay);

        FloatingActionButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            finish();
        });

        Log.d("MYERROR", "BEFOR");

        String title = getIntent().getExtras().getString("title");
        String description = getIntent().getExtras().getString("description");
        String address = getIntent().getExtras().getString("address");

//        --------------------------------------------------------------------------
        Marker marker = new Marker(mapView);
        marker.setPosition(new GeoPoint(lattitude, longitude));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                createPopup(title, address,description);
                return true;
            }
        });
        mapView.getOverlays().add(marker);
        marker.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        marker.setTitle(title);
//   ----------------------------------------------------------------------------
        Log.d("MYERROR", "AFTER");

        if (isWork) {
            startDrawing(lattitude, longitude);
        }
    }
    private void createPopup(String placeTitle, String placeAddress, String placeDescription) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_map, null);

        int width = 900;
        int height = 750;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        TextView placeTitleField = popupView.findViewById(R.id.placeTitle);
        TextView placeAddressField = popupView.findViewById(R.id.placeAddress);
        TextView placeDescriptionField = popupView.findViewById(R.id.placeDescription);

        placeTitleField.setText(placeTitle);
        placeAddressField.setText(placeAddress);
        placeDescriptionField.setText(placeDescription);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }
    @Override
    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapView != null) {
            mapView.onResume();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        Configuration.getInstance().save(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapView != null) {
            mapView.onPause();
        }
    }

    private void startDrawing(float latitude, float longitude) {
        FusedLocationProviderClient fusedLocation = LocationServices.getFusedLocationProviderClient(this);
        fusedLocation.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location == null) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                } else {
                    drawPath(new GeoPoint(latitude, longitude), new GeoPoint(location.getLatitude(), location.getLongitude()));
                }
            }
        });
    }

    private void drawPath(GeoPoint placePoint, GeoPoint userPoint) {
        Polyline polyline = new Polyline(mapView);

        polyline.addPoint(placePoint);
        polyline.addPoint(userPoint);

        polyline.setColor(Color.BLUE);

        mapView.getOverlays().add(polyline);
    }
}