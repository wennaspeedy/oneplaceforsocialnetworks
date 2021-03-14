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

class FbSettingFragment : PreferenceFragmentCompat() {



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_fbsetting, rootKey)




        }




    }





