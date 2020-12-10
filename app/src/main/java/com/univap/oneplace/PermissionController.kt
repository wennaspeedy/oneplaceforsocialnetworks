package com.univap.oneplace

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class PermissionController {
    @RequiresApi(Build.VERSION_CODES.M)
    fun havePermissions(activity: AppCompatActivity):Boolean {

            if ((activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED) && (activity.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) === PackageManager.PERMISSION_GRANTED))
            {
                return true
            }
            else
            {
                if(!(activity.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED) && (activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED)){
                    ActivityCompat.requestPermissions(activity, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                }
                else if(!(activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED)&& (activity.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED))
                {                ActivityCompat.requestPermissions(activity, arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                }
                else if(!(activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED)&& !(activity.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED))
                {                ActivityCompat.requestPermissions(activity, arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION), 1)
                }

                return false
            }



    }
    fun haveLocationPermission(activity: AppCompatActivity):Boolean {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if ((activity.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) === PackageManager.PERMISSION_GRANTED))
            {
                return true
            }
            else
            {
                ActivityCompat.requestPermissions(activity, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                return false
            }
        }
        else
        { //you dont need to worry about these stuff below api level 23
            return true
        }
    }

}