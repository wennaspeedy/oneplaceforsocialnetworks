package com.univap.oneplace.settings

import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.preference.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.univap.oneplace.AppCompatPreferenceActivity
import com.univap.oneplace.MainActivity
import com.univap.oneplace.MyContextWrapper
import com.univap.oneplace.R
import com.univap.oneplace.util.frSaveFragmentPosition
import androidx.preference.PreferenceManager

class CustomActivity : AppCompatPreferenceActivity() {


var changed = false
var counter = 0
    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

        fun setNavigationOnClickListener (){

    }
    override fun onBackPressed() {

        if(changed){


        try {
            val builder = AlertDialog.Builder(this)

            builder.setMessage(R.string.changesocials)
                .setTitle(R.string.changehead)
                .setCancelable(false)
                .setNegativeButton(R.string.notnow, DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(
                        this, R.string.changenoopt,
                        Toast.LENGTH_LONG
                    ).show();
                    super.onBackPressed();
                    finish();
                })
                .setPositiveButton(R.string.reload, DialogInterface.OnClickListener { dialog, id ->
                    val myIntent = Intent(this, MainActivity::class.java)
                    // myIntent.putExtra("key", value) //Optional parameters

                    //finish();
                    this.finish()
                    this.finishAffinity();

                    this.startActivity(myIntent)
                })
            val alert = builder.create()
            if (alert.isShowing()) {
                alert.dismiss();
            }else {
                alert.show()}
        } catch (e: Exception) {
        }
        }
        else {
            super.onBackPressed();
            finish();
        }
    }









        @RequiresApi(Build.VERSION_CODES.M)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            setupActionBar()
            addPreferencesFromResource(R.xml.pref_custom)
            //setHasOptionsMenu(true)

            changed = false
            val fbSwitch = findPreference("switchfacebook") as SwitchPreference
            val twSwitch = findPreference("switchtwitter") as SwitchPreference
            val igSwitch = findPreference("switchinstagram") as SwitchPreference
            val liSwitch = findPreference("switchlinkedin") as SwitchPreference
            val rdSwitch = findPreference("switchreddit") as SwitchPreference
            val gnSwitch = findPreference("switchnews") as SwitchPreference

            counter = if (fbSwitch.isChecked()) 1 else 0
                counter += if (twSwitch.isChecked()) 1 else 0
                counter +=  if (igSwitch.isChecked()) 1 else 0
                counter += if (liSwitch.isChecked()) 1 else 0
            counter += if (rdSwitch.isChecked()) 1 else 0
            counter += if (gnSwitch.isChecked()) 1 else 0

            fbSwitch.onPreferenceChangeListener = object: Preference.OnPreferenceChangeListener {
                override fun onPreferenceChange(preference:Preference, o:Any):Boolean {
                 checkStatus(fbSwitch)
if(!fbSwitch.isChecked){
    frSaveFragmentPosition(PreferenceManager.getDefaultSharedPreferences(applicationContext),"null")
}

                    return false
                }
            }
            twSwitch.onPreferenceChangeListener = object: Preference.OnPreferenceChangeListener {
                override fun onPreferenceChange(preference:Preference, o:Any):Boolean {
                    checkStatus(twSwitch)
                    if(!twSwitch.isChecked){
                        frSaveFragmentPosition(PreferenceManager.getDefaultSharedPreferences(applicationContext),"null")
                    }

                    return false
                }
            }
            igSwitch.onPreferenceChangeListener = object: Preference.OnPreferenceChangeListener {
                override fun onPreferenceChange(preference:Preference, o:Any):Boolean {
                checkStatus(igSwitch)
                    if(!igSwitch.isChecked){
                        frSaveFragmentPosition(PreferenceManager.getDefaultSharedPreferences(applicationContext),"null")
                    }

                    return false
                }
            }
            liSwitch.onPreferenceChangeListener = object: Preference.OnPreferenceChangeListener {
                override fun onPreferenceChange(preference:Preference, o:Any):Boolean {
                    checkStatus(liSwitch)
                    if(!liSwitch.isChecked){
                        frSaveFragmentPosition(PreferenceManager.getDefaultSharedPreferences(applicationContext),"null")
                    }



                    return false
                }
            }
            rdSwitch.onPreferenceChangeListener = object: Preference.OnPreferenceChangeListener {
                override fun onPreferenceChange(preference:Preference, o:Any):Boolean {
                    checkStatus(rdSwitch)
                    if(!rdSwitch.isChecked){
                        frSaveFragmentPosition(PreferenceManager.getDefaultSharedPreferences(applicationContext),"null")
                    }



                    return false
                }
            }
            gnSwitch.onPreferenceChangeListener = object: Preference.OnPreferenceChangeListener {
                override fun onPreferenceChange(preference:Preference, o:Any):Boolean {
                    checkStatus(gnSwitch)
                    if(!gnSwitch.isChecked){
                        frSaveFragmentPosition(PreferenceManager.getDefaultSharedPreferences(applicationContext),"null")
                    }
                    return false
                }
            }




        }

fun checkStatus(switch:SwitchPreference){
    if (switch.isChecked()) {

        if(counter==1){Toast.makeText(this@CustomActivity, R.string.selectleast, Toast.LENGTH_LONG).show();}
        else{
            switch.setChecked(false)
            changed = true
            counter = counter-1}
    } else {

        if(counter==5){Toast.makeText(this@CustomActivity, R.string.selectmax, Toast.LENGTH_LONG).show();}
        else{
            switch.setChecked(true)
            changed = true
            counter = counter+1}
    }
}
    override fun attachBaseContext(newBase: Context) {

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(newBase)
        val lang = sharedPref!!.getString("lang", "none")
        val deflang = Resources.getSystem().getConfiguration().locale.getLanguage();
        if(lang == "none"){
            super.attachBaseContext(MyContextWrapper.wrap(newBase, deflang))
        } else {
            super.attachBaseContext(MyContextWrapper.wrap(newBase, lang!!))

        }
    }


    }




