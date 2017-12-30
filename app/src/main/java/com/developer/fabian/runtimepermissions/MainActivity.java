package com.developer.fabian.runtimepermissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button check_permission = findViewById(R.id.check_permission);
        Button request_permission = findViewById(R.id.request_permission);

        check_permission.setOnClickListener(this);
        request_permission.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Snackbar.make(view, R.string.access_message, Snackbar.LENGTH_LONG).show();
                else
                    Snackbar.make(view, R.string.denied_message, Snackbar.LENGTH_LONG).show();

                break;
        }
    }

    @Override
    public void onClick(View v) {
        view = v;
        int id = v.getId();

        switch (id) {
            case R.id.check_permission:
                if (checkPermission())
                    Snackbar.make(view, R.string.granted_permission_message, Snackbar.LENGTH_LONG).show();
                else
                    Snackbar.make(view, R.string.request_permission_message, Snackbar.LENGTH_LONG).show();

                break;
            case R.id.request_permission:
                if (!checkPermission())
                    requestPermission();
                else
                    Snackbar.make(view, R.string.granted_permission_message, Snackbar.LENGTH_LONG).show();

                break;
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            Toast.makeText(getApplicationContext(), R.string.permissions_message, Toast.LENGTH_LONG).show();
        else
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }
}
