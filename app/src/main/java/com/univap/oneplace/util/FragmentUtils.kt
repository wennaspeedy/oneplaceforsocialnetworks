package com.univap.oneplace.util

import android.content.Context
import android.content.SharedPreferences
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.univap.oneplace.*
import com.univap.oneplace.fragments.*
import kotlinx.android.synthetic.main.maincontent.*


fun frKillOtherFragments(actualFragment: String, fragmentManager: FragmentManager) {


    val fragmentTransaction = fragmentManager!!.beginTransaction()


    if (actualFragment != "fb") {
        fragmentTransaction.remove(FacebFragment())
    }
    if (actualFragment != "tw") {
        fragmentTransaction.remove(TwitterFragment())
    }
    if (actualFragment != "ig") {
        fragmentTransaction.remove(InstaFragment())
    }
    if (actualFragment != "li") {
        fragmentTransaction.remove(LinkedinFragment())
    }
    if (actualFragment != "rd") {
        fragmentTransaction.remove(LinkedinFragment())
    }
    fragmentTransaction.commit()
}

fun frLoadPositionOnRestart(
    fragmentManager: FragmentManager,
    sharedPref: SharedPreferences,
    context: Context,
    fragment: Fragment
) {

    var fragLoaded = sharedPref!!.getString("frag_loaded", null)
    var rememposit = sharedPref!!.getBoolean("rememposit", false)
    val fbPref = sharedPref.getBoolean("switchfacebook", true)
    val twPref = sharedPref.getBoolean("switchtwitter", true)
    val igPref = sharedPref.getBoolean("switchinstagram", true)
    val liPref = sharedPref.getBoolean("switchlinkedin", true)
    val rdPref = sharedPref.getBoolean("switchreddit", true)
    val gnPref = sharedPref.getBoolean("switchreddit", true)
    if (rememposit) {
        if (fragLoaded == "tw" && twPref) {
            //fragmentManager!!.beginTransaction().remove(FacebFragment())
            // fragmentManager!!.beginTransaction().remove(fragment)
            fragmentManager!!.beginTransaction().replace(R.id.fragment, TwitterFragment()).commit()
        } else
            if (fragLoaded == "fb" && fbPref) {


                // fragmentManager!!.beginTransaction().remove(fragment)
                fragmentManager!!.beginTransaction().replace(R.id.fragment,
                    FacebFragment()
                ).commit()
            } else
                if (fragLoaded == "ig" && igPref) {


                    // fragmentManager!!.beginTransaction().remove(fragment)
                    fragmentManager!!.beginTransaction().replace(R.id.fragment,
                        InstaFragment()
                    ).commit()
                } else

                    if (fragLoaded == "li" && liPref) {

                        //  fragmentManager!!.beginTransaction().remove(fragment)
                        fragmentManager!!.beginTransaction().replace(R.id.fragment,
                            LinkedinFragment()
                        ).commit()
                    } else
                        if (fragLoaded == "rd" && rdPref) {


                            // fragmentManager!!.beginTransaction().remove(fragment)
                            fragmentManager!!.beginTransaction().replace(R.id.fragment,
                                RedditFragment()
                            ).commit()
                        }
                        else
                            if (fragLoaded == "gn" && gnPref) {


                                // fragmentManager!!.beginTransaction().remove(fragment)
                                fragmentManager!!.beginTransaction().replace(R.id.fragment,
                                    NewsFragment()
                                ).commit()
                            }

    }
}

fun frSaveFragmentPosition(sharedPref: SharedPreferences, src: String) {
    if (sharedPref!!.getBoolean("rememposit", false)) {

        sharedPref!!.edit().putString("frag_loaded", src).commit()
    }


}

fun frSetThisMenuItemChecked(activity: AppCompatActivity, fragId: Int, sharedPref: SharedPreferences) {

    activity.bottomNav.getMenu().findItem(fragId).setChecked(true)

}

fun frOndestroy(pbar: ProgressBar?, img: ImageView, myWebView: WebView) {

    //pbar?.let { pbar!!.setVisibility(ProgressBar.GONE); }
    img?.let {
        img!!.setVisibility(ProgressBar.GONE);
        img!!.clearAnimation();
    }
    myWebView?.let {
        //  myWebView!!.setVisibility(View.GONE);
        myWebView!!.stopLoading();
        myWebView!!.setWebChromeClient(null);
        myWebView!!.setWebViewClient(null);
        myWebView!!.clearHistory();
        // myWebView!!.clearCache(true);
        // myWebView!!.onPause();
        myWebView!!.removeAllViews()
        //myWebView!!.pauseTimers();
        myWebView!!.loadUrl("file:///android_asset/default_reload_page.html")
        myWebView!!.destroy()
    }
}

fun frOnStop(pbar: ProgressBar?, img: ImageView, myWebView: WebView, sharedPref: SharedPreferences) {

//pbar?.let { pbar!!.setVisibility(ProgressBar.GONE); }
    img?.let {
        img!!.setVisibility(ProgressBar.GONE);
        img!!.clearAnimation();
    }
    var tabmode = sharedPref!!.getString("tabmode", "2")

    myWebView?.let {
        myWebView!!.stopLoading();
    }
    if (tabmode == "1") {

        myWebView?.let {
            myWebView!!.stopLoading();
            myWebView!!.loadUrl("file:///android_asset/default_reload_page.html")
        }
    }
}

fun frOnResume(myWebView: WebView, sharedPref: SharedPreferences, url: String, tempstring: String,img: ImageView,mainFrameLayout:FrameLayout,context: Context) {

    var tabmode = sharedPref!!.getString("tabmode", "2")
    if (tabmode == "1") {
        img!!.setVisibility(ProgressBar.VISIBLE)
        viewStartImgRotate(img!!, context!!)
        mainFrameLayout!!.setVisibility(FrameLayout.VISIBLE)
        mainFrameLayout!!.setTransparent()
        myWebView?.let {
            if (!tempstring.equals("") && tempstring != null) {
                myWebView!!.loadUrl(tempstring);
            }
        }
    }


}


fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}
