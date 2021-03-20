package com.univap.oneplace.settings

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.univap.oneplace.*
import com.univap.oneplace.util.SetThemeType
import petrov.kristiyan.colorpicker.ColorPicker
import java.util.prefs.PreferenceChangeEvent
import java.util.prefs.PreferenceChangeListener

class   FbSettingActivity : AppCompatActivity() {



    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        val myIntent = Intent(this@FbSettingActivity, MainActivity::class.java)
        this@FbSettingActivity.startActivity(myIntent)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed();
        finish()
        val myIntent = Intent(this@FbSettingActivity, MainActivity::class.java)
        this@FbSettingActivity.startActivity(myIntent)


        /*  finish();
          this.finishAffinity();*/

    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.preferenceFbSettFragment, FbSettingFragment())
            .commit()
        setContentView(R.layout.pref_fbsett_activity);
        setupActionBar()







        //addPreferencesFromResource(R.xml.pref_fbsetting)
        //setHasOptionsMenu(true)


/*
        val fbtheme: ListPreference = findPreference("fbthemes") as ListPreference

        val fbheader: CheckBoxPreference = findPreference("fbheader") as CheckBoxPreference
        val fbsponsored: CheckBoxPreference = findPreference("sponsored") as CheckBoxPreference

        //if(BuildConfig.VERSION_NAME.contains("free")){fbsponsored.setEnabled(true)}//zmenit

        fbtheme.onPreferenceChangeListener = object : PreferenceChangeListener, Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                changed = true
                return true
            }

            override fun preferenceChange(evt: PreferenceChangeEvent?) {
            }


        }
        fbheader.onPreferenceChangeListener = object : PreferenceChangeListener, Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                if (fbheader.isChecked()) {
                    fbheader.setChecked(false)
                    changed = true
                } else {
                    fbheader.setChecked(true)
                    changed = true
                }
                return false
            }

            override fun preferenceChange(evt: PreferenceChangeEvent?) {
            }


        }*/

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
