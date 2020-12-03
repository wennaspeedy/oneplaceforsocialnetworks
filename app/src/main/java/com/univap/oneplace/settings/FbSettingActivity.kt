package com.univap.oneplace.settings

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.univap.oneplace.*
import java.util.prefs.PreferenceChangeEvent
import java.util.prefs.PreferenceChangeListener

class FbSettingActivity : AppCompatPreferenceActivity() {

    var changed = false

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onBackPressed() {

        if (changed) {


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
                        this.finish()
                        this.finishAffinity();

                        this.startActivity(myIntent)
                    })
                val alert = builder.create()
                if (alert.isShowing()) {
                    alert.dismiss();
                } else {
                    alert.show()
                }
            } catch (e: Exception) {
            }
        } else {
            super.onBackPressed();
            finish();
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changed = false
        setupActionBar()
        addPreferencesFromResource(R.xml.pref_fbsetting)
        //setHasOptionsMenu(true)



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


        }

}
    override fun attachBaseContext(newBase: Context) {

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(newBase)
        val lang = sharedPref!!.getString("lang", "none")
        val deflang = Resources.getSystem().getConfiguration().locale.getLanguage();
        if(lang == "none"){
            super.attachBaseContext(MyContextWrapper.wrap(newBase, deflang))
        } else {
            super.attachBaseContext(MyContextWrapper.wrap(newBase, lang))

        }
    }

}
