package com.univap.oneplace.settings

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.univap.oneplace.MainActivity
import com.univap.oneplace.MyContextWrapper
import com.univap.oneplace.R


class SettingActivity : AppCompatActivity() {


    var changed = false

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        val myIntent = Intent(this@SettingActivity, MainActivity::class.java)
        this@SettingActivity.startActivity(myIntent)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed();
        finish()
        val myIntent = Intent(this@SettingActivity, MainActivity::class.java)
        this@SettingActivity.startActivity(myIntent)


      /*  finish();
        this.finishAffinity();*/

    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.preferenceSettFragment, SettingFragment())
            .commit()
        setContentView(R.layout.pref_sett_activity);
        //addPreferencesFromResource(R.xml.pref_setting)
       // changed = false
        setupActionBar()


        //setHasOptionsMenu(true)

        //getWindow().getDecorView().background.setAlpha(50)
/*
        val cleard: Preference = findPreference("cleard") as Preference
        getPreferenceScreen().findPreference("PREF_SAVE_PATH");

        val clearco: Preference = findPreference("clearco") as Preference
        val doubleb: CheckBoxPreference = findPreference("doubleb") as CheckBoxPreference
       // val showfr: CheckBoxPreference = findPreference("showfr")!! as CheckBoxPreference
        val hidenavi: CheckBoxPreference = findPreference("hidenavi") as CheckBoxPreference
        val donate: CheckBoxPreference = findPreference("donate") as CheckBoxPreference
        /*if(BuildConfig.VERSION_NAME.contains("free")){
            showfr.setEnabled(true)

            showfr.setOnPreferenceClickListener {
                showfr.setChecked(false)
                try {
                    val builder = AlertDialog.Builder(this)

                    builder.setMessage(R.string.freeMessage)
                        .setTitle(R.string.freeMessageTitle)
                        .setCancelable(false)
                        .setNegativeButton(
                            R.string.negativeButton,
                            DialogInterface.OnClickListener { dialog, id ->
                                Toast.makeText(this, R.string.why,Toast.LENGTH_LONG).show();
                            })
                        .setPositiveButton(
                            R.string.positiveButton,
                            DialogInterface.OnClickListener { dialog, id ->
                                val i = Intent(Intent.ACTION_VIEW)
                                i.data = Uri.parse("https://play.google.com/store/apps/details?id=com.univap.fsocialnetworks.pro")
                                startActivity(i)
                            })
                    val alert = builder.create()
                    if (alert.isShowing()) {
                        alert.dismiss();
                    } else {
                        alert.show()
                    }
                } catch (e: java.lang.Exception) {
                }
                true
            }
        }*/
        donate.onPreferenceClickListener = object : Preference.OnPreferenceClickListener {
            override fun onPreferenceClick(preference: Preference?): Boolean {
                finish()
                val myIntent = Intent(this@SettingActivity, DonateActivity::class.java)
                this@SettingActivity.startActivity(myIntent)
                return false
            }


        }



        doubleb.onPreferenceChangeListener = object : PreferenceChangeListener, Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                if (doubleb.isChecked()) {
                    doubleb.setChecked(false)
                } else {
                    doubleb.setChecked(true)
                }
                return false
            }

            override fun preferenceChange(evt: PreferenceChangeEvent?) {
            }


        }
        /*
        showfr.onPreferenceChangeListener = object : PreferenceChangeListener, Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                if (showfr.isChecked()) {
                    showfr.setChecked(false)
                } else {
                    showfr.setChecked(true)
                }
                return false
            }

            override fun preferenceChange(evt: PreferenceChangeEvent?) {
            }


        }*/
        hidenavi.onPreferenceChangeListener = object : PreferenceChangeListener, Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                if (hidenavi.isChecked()) {
                    hidenavi.setChecked(false)
                } else {
                    hidenavi.setChecked(true)
                }
               changed = true
                return false
            }

            override fun preferenceChange(evt: PreferenceChangeEvent?) {
            }


        }
        cleard.onPreferenceClickListener = object : Preference.OnPreferenceClickListener {
            override fun onPreferenceClick(preference: Preference?): Boolean {
                var packageName = this@SettingActivity.getPackageName()
                var intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))

                startActivity(intent);
                return false
            }


        }
        clearco.onPreferenceClickListener = object : Preference.OnPreferenceClickListener {
            override fun onPreferenceClick(preference: Preference?): Boolean {

                try {
                    val builder = AlertDialog.Builder(this@SettingActivity)

                    builder.setMessage(R.string.performaction)
                        .setTitle(R.string.clearco)
                        .setCancelable(false)
                        .setNegativeButton(R.string.no, DialogInterface.OnClickListener { dialog, id ->

                        })
                        .setPositiveButton(R.string.yes, DialogInterface.OnClickListener { dialog, id ->
                            CookieManager.getInstance().removeAllCookies(null)
                            CookieManager.getInstance().flush()
                            WebView(getApplicationContext()).clearCache(true);
                            changed = true
                            Toast.makeText(applicationContext, R.string.clearcotoast, Toast.LENGTH_SHORT).show();
                        })
                    val alert = builder.create()
                    if (alert.isShowing()) {
                        alert.dismiss();
                    } else {
                        alert.show()
                    }
                } catch (e: Exception) {
                }

                /*
                CookieManager.getInstance().removeAllCookies(null)
                CookieManager.getInstance().flush()
                changed = true
                Toast.makeText(applicationContext, R.string.clearcotoast, Toast.LENGTH_SHORT).show();
                */
                return false
            }


        }





        lang.onPreferenceChangeListener = object : PreferenceChangeListener, Preference.OnPreferenceChangeListener {
            override fun preferenceChange(evt: PreferenceChangeEvent?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {

                changed = true
                return true
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



