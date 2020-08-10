package com.example.mfundofalteni.myweatherapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goCheck();
            }
        }, 1000);

    }

    public void goCheck() {
        WeatherConnect weatherConnect = new WeatherConnect();
        if (!weatherConnect.checkConnection(getBaseContext())) {
            FragmentManager fm = getSupportFragmentManager();
            MyAlertDiagFrag myAlertDiagFrag = MyAlertDiagFrag.newInstance();
            myAlertDiagFrag.show(fm, "");
        } else {
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getLocation() {

        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        LocationServices.getFusedLocationProviderClient(SplashActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback()
                {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(SplashActivity.this).removeLocationUpdates(this);
                        if(locationResult != null && locationResult.getLocations().size() > 0){
                            int lastLocation = locationResult.getLocations().size() - 1;
                            double lat = locationResult.getLocations().get(lastLocation).getLatitude();
                            double lng = locationResult.getLocations().get(lastLocation).getLongitude();
                            showLat(lat, lng);
                        }
                    }
                }, Looper.getMainLooper());
    }
    private void showLat(double d, double dd){
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("lat",d);
        intent.putExtra("lng",dd);
        startActivity(intent);
        finish();
    }
}
