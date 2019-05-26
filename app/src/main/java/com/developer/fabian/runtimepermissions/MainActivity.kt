package com.developer.fabian.runtimepermissions

import android.Manifest
import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }

    private var view: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val checkPermission = findViewById<Button>(R.id.check_permission)
        val requestPermission = findViewById<Button>(R.id.request_permission)

        checkPermission.setOnClickListener(this)
        requestPermission.setOnClickListener(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Snackbar.make(view!!, R.string.access_message, Snackbar.LENGTH_LONG).show()
            else
                Snackbar.make(view!!, R.string.denied_message, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onClick(v: View) {
        view = v

        when (v.id) {
            R.id.check_permission -> if (checkPermission())
                Snackbar.make(view!!, R.string.granted_permission_message, Snackbar.LENGTH_LONG).show()
            else
                Snackbar.make(view!!, R.string.request_permission_message, Snackbar.LENGTH_LONG).show()
            R.id.request_permission -> if (!checkPermission())
                requestPermission()
            else
                Snackbar.make(view!!, R.string.granted_permission_message, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)

        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            Toast.makeText(applicationContext, R.string.permissions_message, Toast.LENGTH_LONG).show()
        else
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
    }
}
