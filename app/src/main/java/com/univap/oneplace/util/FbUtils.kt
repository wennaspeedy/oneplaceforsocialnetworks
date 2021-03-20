package com.univap.oneplace.util

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Build
import android.view.MotionEvent
import androidx.preference.PreferenceManager
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.univap.oneplace.R
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("ResourceType")
fun SetFbTheme(context: Context, /*pbar:ProgressBar,*/ navimenu: BottomNavigationView) {

    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

    val themeType = sharedPref.getString("fbthemes", "1")
    val themeColor = GetThemeMainColor(context);
    navimenu!!.background = ContextCompat.getDrawable(context!!, themeColor)
    if (themeType == "3") {


        navimenu!!.setItemTextColor(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_grey_black))// ikony navimenu bottom
        navimenu!!.setItemIconTintList(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_grey_black)) // navimenu bottom pismo

    } else if (themeType == "2"||themeType == "5"/*||themeType == "4"||themeType == "6"||themeType == "7"*/){

        navimenu!!.setItemTextColor(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_white_grey))
        navimenu!!.setItemIconTintList(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_white_grey));

    } else {

        navimenu!!.setItemTextColor(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_white_black))
        navimenu!!.setItemIconTintList(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_white_black));

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
    } else if (themeType == "8") {
        return R.color.colorGold
    } else if (themeType == "9") {
        return R.color.colorPink
    } else if (themeType == "10") {
        return R.color.colorCoral
    } else if (themeType == "11") {
        return R.color.colorSeagreen
    } else if (themeType == "12") {
        return R.color.colorViolet
    }  else if (themeType == "13") {
        return R.color.colorSkyblue
    } else if (themeType == "14") {
        return R.color.colorYellowgreen
    }
    else {
        return R.color.colorFBblue
    }

}
fun changeFbThemescolors (context: Context, button: Button, messbutton: Button){
    val color = GetThemeMainColor(context)
    val activity = (context as AppCompatActivity)
    val window: Window = activity.getWindow()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = context.resources.getColor(color)
        window.navigationBarColor = context.resources.getColor(color)

    }
    button.setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.background.setColorFilter(ContextCompat.getColor(context!!, color), PorterDuff.Mode.SRC_ATOP)
                v.invalidate()
            }
            MotionEvent.ACTION_UP -> {
                v.background.clearColorFilter()
                v.invalidate()
            }
        }
        false
    }
    messbutton.setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                v.background.setColorFilter(ContextCompat.getColor(context!!, color), PorterDuff.Mode.SRC_ATOP)
                v.invalidate()
            }
            MotionEvent.ACTION_UP -> {
                v.background.clearColorFilter()
                v.invalidate()
            }
        }
        false
    }
        //pozadi leveho menu
   // activity.nav_view.setBackgroundResource(color);

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
    return themeType!!
}
fun SetThemeType(context: Context,position: Int){
    // println("MYLOGfbut: "+position)
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    with (sharedPref.edit()) {
        putString("fbthemes",position.toString() )
        apply()
    }
    /*val themeType = sharedPref.set("fbthemes", "1")
    return themeType!!*/
}

fun fbOnLoadResource(myWebView: WebView, url: String, context: Context, sharedPref: SharedPreferences) {
   // val fbheader = sharedPref!!.getBoolean("fbheader", false)
    //if(url.contains("m.facebook.com")){println ("URL: "+url)}
    if (url!!.isNotEmpty()) {
       /* if (url.contains("%2Fpages")) {
            myWebView!!.loadUrl("https://m.facebook.com/pages");
        } else if (url.contains("messages")) {
            viewInjectCSS(myWebView!!, "fb_hides.css", context);
        } else*/ if (url.contains("home")) {
            val sponsored = sharedPref!!.getBoolean("sponsored", false)
            if (sponsored) {
                viewInjectJS(myWebView!!, context!!, "fb_spon.js")
            }//hide sponsored on the FB feed page
        }

        if ((url.contains("m.facebook.com") || url.contains("www.facebook.com"))&& !(url.contains("/a/bz") || url.contains("presence_icon")|| url.contains("common") || url.contains("pymk") || url.contains("badge") || url.contains("platform") || url.contains("metrics"))) {
           // println("URL--->INJECT")
            fbInjector(sharedPref!!, myWebView!!, context!!);
        }//inject css everytime is page loaded
        if (url.contains("www.facebook.com/marketplace/")) {
            println("MYLOG: "+url)
            // view!!.stopLoading()

            //myWebView!!.loadUrl("m.facebook.com/marketplace/?_rdr");


        }

      /*  if (fbheader) {
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
        }*/

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
    else if (themeType == "8") {
        viewInjectCSS(view!!, "fb_gold.css", context);

    }
    else if (themeType == "9") {
        viewInjectCSS(view!!, "fb_pink.css", context);

    }
    else if (themeType == "10") {
        viewInjectCSS(view!!, "fb_coral.css", context);

    }  else if (themeType == "11") {
        viewInjectCSS(view!!, "fb_seagreen.css", context);

    }  else if (themeType == "12") {
        viewInjectCSS(view!!, "fb_violet.css", context);

    }else if (themeType == "13") {
        viewInjectCSS(view!!, "fb_skyblue.css", context);

    }  else if (themeType == "14") {
        viewInjectCSS(view!!, "fb_yellowgreen.css", context);

    }

}