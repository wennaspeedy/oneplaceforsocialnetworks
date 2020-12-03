package com.univap.oneplace.util

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.webkit.WebView
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.univap.oneplace.R

@SuppressLint("ResourceType")
fun SetFbTheme(context: Context, /*pbar:ProgressBar,*/ navimenu: BottomNavigationView) {

    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

    val themeType = sharedPref.getString("fbthemes", "1")
    val themeColor = GetThemeMainColor(context);
    if (themeType == "2") {
        // pbar!!.setPbarColor(context!!, R.color.colorFBBlack)
        navimenu!!.background = ContextCompat.getDrawable(context!!, themeColor)
        navimenu!!.setItemTextColor(AppCompatResources.getColorStateList(context!!, R.drawable.item_selector))
        navimenu!!.setItemIconTintList(AppCompatResources.getColorStateList(context!!, R.drawable.item_selector));
    } else if (themeType == "3") {
        //pbar!!.setPbarColor(context!!, R.color.colorFBGrey)
        navimenu!!.background = ContextCompat.getDrawable(context!!, themeColor)

        navimenu!!.setItemTextColor(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_black_white))
        navimenu!!.setItemIconTintList(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_black_white));

    } else if (themeType == "4") {
        // pbar!!.setPbarColor(context!!, R.color.colorFBRed)
        navimenu!!.background = ContextCompat.getDrawable(context!!, themeColor)
        navimenu!!.setItemTextColor(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_white_black))
        navimenu!!.setItemIconTintList(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_white_black))

    } else if (themeType == "5") {
        // pbar!!.setPbarColor(context!!, R.color.colorFBRed)
        navimenu!!.background = ContextCompat.getDrawable(context!!, themeColor)
        navimenu!!.setItemTextColor(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_white_black))
        navimenu!!.setItemIconTintList(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_white_black))

    } else if (themeType == "6") {
        // pbar!!.setPbarColor(context!!, R.color.colorFBRed)
        navimenu!!.background = ContextCompat.getDrawable(context!!, themeColor)
        navimenu!!.setItemTextColor(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_white_black))
        navimenu!!.setItemIconTintList(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_white_black))

    } else if (themeType == "7") {
        // pbar!!.setPbarColor(context!!, R.color.colorFBRed)
        navimenu!!.background = ContextCompat.getDrawable(context!!, themeColor)
        navimenu!!.setItemTextColor(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_white_black))
        navimenu!!.setItemIconTintList(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_white_black))

    }else {
        //pbar!!.setPbarColor(context!!, R.color.colorFBblue)
        navimenu!!.background = ContextCompat.getDrawable(context!!, themeColor)
        navimenu!!.setItemTextColor(AppCompatResources.getColorStateList(context!!, R.drawable.item_selector))
        navimenu!!.setItemIconTintList(AppCompatResources.getColorStateList(context!!, R.drawable.item_selector));
    }
    navimenu!!.menu.findItem(R.id.facebookFragment).setChecked(true)

}

fun GetThemeMainColor(context: Context): Int {

    val themeType = GetThemeType(context)

    if (themeType == "2") {
        return R.color.colorFBBlack
    } else if (themeType == "3") {
        return R.color.colorFBGrey
    } else if (themeType == "4") {
        return R.color.colorFBRed
    } else if (themeType == "5") {
        return R.color.colorFBBlack2
    } else if (themeType == "6") {
        return R.color.colorFBOrange
    } else if (themeType == "7") {
        return R.color.colorFBGreen
    } else {
        return R.color.colorFBblue
    }
}


fun StartFbImgRotate(img: ImageView, context: Context) {
    //var rotateAnim: RotateAnimation? = null
    img.setBgColor(context, GetThemeMainColor(context!!))

    if (GetThemeType(context) == "3") {
        img.setColorFilter(
            ContextCompat.getColor(context, R.color.colorFBBlack),
            android.graphics.PorterDuff.Mode.MULTIPLY
        );
    }

    ObjectAnimator.ofFloat(img, "rotation",0f, 360f).apply {
        duration = 1500
        repeatCount = ValueAnimator.INFINITE

        start()
    }
}

fun GetThemeType(context: Context): String {
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

    val themeType = sharedPref.getString("fbthemes", "1")
    return themeType
}

fun fbOnLoadResource(myWebView: WebView, url: String, context: Context, sharedPref: SharedPreferences) {
    val fbheader = sharedPref!!.getBoolean("fbheader", false)
    //if(url.contains("m.facebook.com")){println ("URL: "+url)}
    if (url!!.isNotEmpty()) {
        if (url.contains("%2Fpages")) {
            myWebView!!.loadUrl("https://m.facebook.com/pages");
        } else if (url.contains("messages")) {
            viewInjectCSS(myWebView!!, "fb_hides.css", context);
        } else if (url.contains("home")) {
            val sponsored = sharedPref!!.getBoolean("sponsored", false)
            if (sponsored) {
                viewInjectJS(myWebView!!, context!!, "fb_spon.js")
            }//hide sponsored on the FB feed page
        }

        if (url.contains("m.facebook.com") && !(url.contains("/a/bz") || url.contains("presence_icon")|| url.contains("common") || url.contains("pymk") || url.contains("badge") || url.contains("platform") || url.contains("metrics"))) {
           // println("URL--->INJECT")
            fbInjector(sharedPref!!, myWebView!!, context!!);
        }//inject css everytime is page loaded


        if (fbheader) {
            viewInjectCSS(myWebView!!, "fb_header.css", context);

            if (myWebView!!.getUrl().contains("soft=bookmarks") || myWebView!!.getUrl().contains("composer") ) {
                viewInjectCSS(myWebView!!, "fb_header_unfix.css", context)
            } else {
                viewInjectCSS(myWebView!!, "fb_header_fix.css", context)
            }
        } else {
            if (myWebView!!.getUrl().contains("composer") ) {
                viewInjectCSS(myWebView!!, "fb_header_unfix.css", context)
            }
        }

    }
}

fun fbInjector(sharedPref: SharedPreferences, view: WebView, context: Context) {


    val themeType = sharedPref!!.getString("fbthemes", "1")
    viewInjectCSS(view!!, "fb_login.css", context);
    if (themeType == "2") {
        viewInjectCSS(view!!, "fb_dark.css", context);


    } else if (themeType == "3") {
        viewInjectCSS(view!!, "fb_light.css", context);

    } else if (themeType == "4") {
        viewInjectCSS(view!!, "fb_blackred.css", context);


    } else if (themeType == "5") {
        viewInjectCSS(view!!, "fb_black.css", context);

    }else if (themeType == "6") {
        viewInjectCSS(view!!, "fb_blackorange.css", context);

    }
else if (themeType == "7") {
    viewInjectCSS(view!!, "fb_blackgreen.css", context);

}

}