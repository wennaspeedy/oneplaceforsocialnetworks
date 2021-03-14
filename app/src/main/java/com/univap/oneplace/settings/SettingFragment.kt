package com.univap.oneplace.settings

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.webkit.CookieManager
import android.webkit.WebView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.univap.oneplace.DonateActivity
import com.univap.oneplace.MainActivity
import com.univap.oneplace.MyContextWrapper
import com.univap.oneplace.R
import java.util.prefs.PreferenceChangeEvent
import java.util.prefs.PreferenceChangeListener

class SettingFragment : PreferenceFragmentCompat() {

    var changed = false

    fun onBackPress() {

        if (changed) {
            try {

                val builder = AlertDialog.Builder(requireContext())

                builder.setMessage(R.string.changesocials)
                    .setTitle(R.string.changehead)
                    .setCancelable(false)
                    .setNegativeButton(R.string.notnow, DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(
                            requireContext(), R.string.changenoopt,
                            Toast.LENGTH_LONG
                        ).show();
                    })
                    .setPositiveButton(R.string.reload, DialogInterface.OnClickListener { dialog, id ->
                        val myIntent = Intent(requireContext(), MainActivity::class.java)
                        // myIntent.putExtra("key", value) //Optional parameters

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
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_setting, rootKey)

        changed = false
        val cleard: Preference? = findPreference("cleard")
        val clearco: Preference? = findPreference("clearco")
        val doubleb: CheckBoxPreference? = findPreference("doubleb")
        val hidenavi: CheckBoxPreference? = findPreference("hidenavi")
        val donate: Preference? = findPreference("donate")
        val lang: ListPreference? = findPreference("lang")

        donate!!.onPreferenceClickListener = object : Preference.OnPreferenceClickListener {


            override fun onPreferenceClick(preference: Preference?): Boolean {

                    val myIntent = Intent(context, DonateActivity::class.java)
                    context!!.startActivity(myIntent)


                return false
            }


        }
        /*
        donate!!.onPreferenceChangeListener = object : PreferenceChangeListener, Preference.OnPreferenceChangeListener {
            override fun on(preference: Preference?, newValue: Any?): Boolean {
                if (donate.isChecked()) {
                    donate.setChecked(true)
                } else {
                    donate.setChecked(false)
                    val myIntent = Intent(context, DonateActivity::class.java)
                    context!!.startActivity(myIntent)
                }
                return false
            }

            override fun preferenceChange(evt: PreferenceChangeEvent?) {
            }


        }*/



        doubleb!!.onPreferenceChangeListener = object : PreferenceChangeListener, Preference.OnPreferenceChangeListener {
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
        hidenavi!!.onPreferenceChangeListener = object : PreferenceChangeListener, Preference.OnPreferenceChangeListener {
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
        cleard!!.onPreferenceClickListener = object : Preference.OnPreferenceClickListener {
            override fun onPreferenceClick(preference: Preference?): Boolean {
                var packageName = context!!.getPackageName()
                var intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))

                startActivity(intent);
                return false
            }


        }
        clearco!!.onPreferenceClickListener = object : Preference.OnPreferenceClickListener {
            override fun onPreferenceClick(preference: Preference?): Boolean {

                try {
                    val builder = AlertDialog.Builder(context!!)

                    builder.setMessage(R.string.performaction)
                        .setTitle(R.string.clearco)
                        .setCancelable(false)
                        .setNegativeButton(R.string.no, DialogInterface.OnClickListener { dialog, id ->

                        })
                        .setPositiveButton(R.string.yes, DialogInterface.OnClickListener { dialog, id ->
                            CookieManager.getInstance().removeAllCookies(null)
                            CookieManager.getInstance().flush()
                            WebView(context).clearCache(true);
                            changed = true
                            Toast.makeText(context, R.string.clearcotoast, Toast.LENGTH_SHORT).show();
                        })
                    val alert = builder.create()
                    if (alert.isShowing()) {
                        alert.dismiss();
                    } else {
                        alert.show()
                    }
                } catch (e: Exception) {
                }


                return false
            }


        }





        lang!!.onPreferenceChangeListener = object : PreferenceChangeListener, Preference.OnPreferenceChangeListener {
            override fun preferenceChange(evt: PreferenceChangeEvent?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {

                changed = true
                return true
            }

        }



    }





}