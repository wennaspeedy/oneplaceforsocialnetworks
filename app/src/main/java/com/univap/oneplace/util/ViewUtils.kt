package com.univap.oneplace.util

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.*
import androidx.preference.PreferenceManager
import android.util.Base64
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.univap.oneplace.MainActivity
import com.univap.oneplace.PermissionController
import com.univap.oneplace.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.maincontent.*
import kotlinx.android.synthetic.main.nav_header_main.*
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

private var mFilePathCallback: ValueCallback<Array<Uri>>? = null
private var mCameraPhotoPath:String? = null

fun ImageView.setBgColor(context: Context,color: Int){
    val background = getBackground()

    if (background is ShapeDrawable)
    {
        (background as ShapeDrawable).getPaint().setColor(ContextCompat.getColor(context, color))
    }
    else if (background is GradientDrawable)
    {
        (background as GradientDrawable).setColor(ContextCompat.getColor(context, color))
    }
    else if (background is ColorDrawable)
    {
        (background as ColorDrawable).setColor(ContextCompat.getColor(context, color))
    }
    setColorFilter(getContext().getResources().getColor(com.univap.oneplace.R.color.colorWhite));
}
/*
fun RotateAnimation.activateRotation(){
    setFillAfter(true)
    setDuration(1500);
    setRepeatCount(Animation.INFINITE);

}*/




fun ProgressBar.setPbarColor(context:Context,color:Int){
    getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.MULTIPLY);


}

fun WebView.initWebview(agent:String = "noagent"){
    //setVisibility(View.GONE);

    settings.allowFileAccessFromFileURLs
    settings.allowFileAccess;
    settings.allowContentAccess;
    settings.allowUniversalAccessFromFileURLs

    if(agent=="fb"){
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

        if(sharedPref!!.getBoolean("fbfast",false)){
            settings.setUserAgentString("Mozilla/5.0 (BB10; Touch) AppleWebKit/537.1+ (KHTML, like Gecko) Version/10.0.0.1337 Mobile Safari/537.1+");
        } else
        {
            settings.setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/89.0.4389.82 Mobile/15E148 Safari/604.1");

            // settings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");
        }}
    else if(agent=="li"){
        settings.setUserAgentString("Mozilla/5.0 (BB10; Touch) AppleWebKit/537.1+ (KHTML, like Gecko) Version/10.0.0.1337 Mobile Safari/537.1+")

    } else if(agent=="tw"){settings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36");
    } else if(agent=="ig"){
        settings.setUserAgentString("Mozilla/5.0 (BB10; Touch) AppleWebKit/537.1+ (KHTML, like Gecko) Version/10.0.0.1337 Mobile Safari/537.1+")
    }

    settings.setJavaScriptEnabled(true);
    settings.setAllowFileAccess(true);
    settings.setAppCacheEnabled(true);
    settings.setDatabaseEnabled(true);
    setVerticalScrollBarEnabled(false);
    //getSettings().setLoadWithOverviewMode(true);
    //getSettings().setUseWideViewPort(false);
    settings.setCacheMode(WebSettings.LOAD_DEFAULT);
    settings.setDomStorageEnabled(true); //nevypinat jinak nefunguje linkedin
    settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
    setLayerType(View.LAYER_TYPE_HARDWARE, null);
    settings.setGeolocationEnabled(true)
}


fun ChangeNaviColor(context: Context,color:Int){

    val navimenu = (context as AppCompatActivity).bottomNav
    navimenu.background = ContextCompat.getDrawable(context, color)


}

fun viewHideActionBar(context: Context){
    (context as AppCompatActivity).supportActionBar!!.hide()

}

    fun Context.toast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG ).show()
    }

    fun ProgressBar.show(){
        visibility = View.VISIBLE
    }

    fun ProgressBar.hide(){
        visibility = View.GONE
    }

fun viewOnPageFinished(pbar: ProgressBar?, img:ImageView, myWebView:WebView,mainFrameLayout:FrameLayout){
    //pbar.setVisibility(ProgressBar.GONE);
    img.setVisibility(ProgressBar.GONE);
    img.clearAnimation();
    mainFrameLayout!!.foreground.setAlpha(0);
    mainFrameLayout!!.setVisibility(FrameLayout.GONE)
    //myWebView!!.setVisibility(View.VISIBLE);
}




fun viewInjectCSS(myWebView2:WebView, name:String, context: Context?) {
    try
    {
        val inputStream = context!!.getAssets().open(name)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()
        val encoded = Base64.encodeToString(buffer, Base64.NO_WRAP)
        myWebView2.evaluateJavascript("javascript:(function() {" +
                "var parent = document.getElementsByTagName('head').item(0);" +
                "var style = document.createElement('style');" +
                "style.type = 'text/css';" +
                // Tell the browser to BASE64-decode the string into your script !!!
                "style.innerHTML = window.atob('" + encoded + "');" +
                "parent.appendChild(style)" +
                "})()",null)
    }
    catch (e:Exception) {
        e.printStackTrace()
    }
}



fun WebView.setDownloadListener(context: Context){
    setDownloadListener(object: DownloadListener {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onDownloadStart(url:String, userAgent:String,
                                     contentDisposition:String, mimetype:String,
                                     contentLength:Long) {


            var perm = PermissionController()
            if(perm.havePermissions(context as AppCompatActivity) && contentDisposition.isNotEmpty())
            {
                val request = DownloadManager.Request(Uri.parse(url))
                request.setMimeType(mimetype)
                //------------------------COOKIE!!------------------------
                val cookies = CookieManager.getInstance().getCookie(url)
                request.addRequestHeader("cookie", cookies)
                //------------------------COOKIE!!------------------------
                request.addRequestHeader("User-Agent", userAgent)
                request.setDescription("Downloading file...")
                var name = URLUtil.guessFileName(url, contentDisposition, mimetype);
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype))
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
                val dm = context!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                dm.enqueue(request)
                Toast.makeText(context, "Downloading file", Toast.LENGTH_SHORT).show()
            } else {Toast.makeText(context, "Try it again", Toast.LENGTH_LONG).show()}

        }
    })

}


 fun viewInjectJS(webView: WebView,context: Context,name:String) {

    try {


        val inputStream = context!!.getAssets().open(name)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()
        val encoded = Base64.encodeToString(buffer, Base64.NO_WRAP)
        webView.loadUrl("javascript:(function() {" +
                "var parent = document.getElementsByTagName('head').item(0);" +
                "var script = document.createElement('script');" +
                "script.type = 'text/javascript';" +
                // Tell the browser to BASE64-decode the string into your script !!!
                "script.innerHTML = window.atob('" + encoded + "');" +
                "parent.appendChild(script)" +
                "})()");




    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun readFileAsTextUsingInputStream(fileName: String)
        = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8);




//METODA PRO ASYNC AKCE
class viewDoAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        Looper.prepare();
        handler()

        return null
    }
}

fun viewIsOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    return netInfo != null

}

fun viewBuildExtLink(context: Context, url:String){

    val builder = AlertDialog.Builder(context)
    builder.setMessage(R.string.linkmsg)
        .setCancelable(true)
        .setPositiveButton(R.string.linkok, DialogInterface.OnClickListener { dialog, id ->

            val i = Intent(Intent.ACTION_VIEW)

            i.data = Uri.parse(url)
            context.startActivity(i)
        })
        .setNegativeButton(R.string.linkko, DialogInterface.OnClickListener { dialog, id ->
            null
        })

    val alert = builder.create()
    if (alert.isShowing()) {
        alert.dismiss();
    } else {
        alert.show()
    }
}

fun viewStartMainActivity(context: Context){

    val myIntent = Intent(context, MainActivity::class.java)
    context.startActivity(myIntent)
}

fun SwipeRefreshLayout.setRefresh(myWebView: WebView,currentUrl:String,sharedPref:SharedPreferences){

    if(sharedPref!!.getBoolean("swiperefresh",false)){
        setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            Handler().postDelayed(Runnable {
                if (myWebView!!.getUrl().contains("default_error_page"))
                {myWebView!!.loadUrl(currentUrl)}
                else{ myWebView!!.reload(); }
                setRefreshing(false) }, 1000)
        })
    }
        else{setRefreshing(false);
            setEnabled(false);}




}

@RequiresApi(Build.VERSION_CODES.M)
fun viewOnReceivedError(myWebView: WebView, request:WebResourceRequest, error:WebResourceError, DEFAULT_ERROR_PAGE_PATH:String,context: Context){

   // println("MYURI-ERROR:"+error!!.description)
    var uri:Uri = request!!.getUrl();
   // println("MYURI:"+uri)
if(!viewIsOnline(context)) {
    if (error!!.description.contains("INTERNET") || error!!.description.contains("UNREACHABLE") || error!!.description.contains(
            "TIMED"
        )
    ) {
        myWebView!!.loadUrl(DEFAULT_ERROR_PAGE_PATH);
    }
}
}
fun createDialogExit(activity: Activity){
    try {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(R.string.alclose)
            .setTitle(R.string.alquit)
            .setCancelable(true)
            .setOnCancelListener(object: DialogInterface.OnCancelListener {
            override fun onCancel(dialog:DialogInterface) {
               counter=0
            }
        })
            .setPositiveButton(
                R.string.yes,
                DialogInterface.OnClickListener { dialog, id ->
                    finishAffinity(activity);
                    System.exit(0);
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
var downtime: Long? = null
var counter: Int = 0
fun WebView.setOnKeyListener(myWebView: WebView,activity: Activity){
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    setOnKeyListener(object: View.OnKeyListener {
        override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
            //println("MYLOG--------")
           // println("MYLOG: keyevent: " + event.toString())

            if (event.getAction() == KeyEvent.ACTION_DOWN) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
                    activity.onBackPressed();
                    event.startTracking()
                    downtime = event.eventTime
                 //  println("MYLOG: onepress")
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount > 0){
                    //println("MYLOG: LONG")
                    if (event.eventTime - downtime!! > 400) {
                        if (counter==0){
                        if (sharedPref!!.getBoolean("doubleb", false)) {
                            createDialogExit(activity);
                            counter=1
                        }
                        }
                    }
                    return true;
                };
            };

            if (event.getAction() == KeyEvent.ACTION_UP) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //println("MYLOG: keydown2 - ")
                    if (event.eventTime - downtime!! > 400) {
                    } else if (activity.drawer_layout.isDrawerOpen(GravityCompat.START)) {
                        activity.drawer_layout.closeDrawer(GravityCompat.START)
                    }else if (myWebView!!.canGoBack()) {
                        myWebView!!.stopLoading();
                        myWebView!!.goBack();
                        return true;
                    }

                };
            }
            return false;
        }
    })}



@SuppressLint("ResourceType")
fun setTheme(context:Context, pbar: ProgressBar?, navimenu: BottomNavigationView) {
    navimenu!!.setItemTextColor(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_white_black))
    navimenu!!.setItemIconTintList(AppCompatResources.getColorStateList(context!!, R.drawable.is_fb_white_black));
}

fun FrameLayout.setTransparent(){
    foreground.setAlpha(200);
}

fun viewStartImgRotate(img: ImageView, context: Context){

    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    var themeType = sharedPref.getString("src", "")
    if (themeType=="fb"){
        StartFbImgRotate(img!!, context) //Setting imgiconloader
        return
    } else {

    img.setBgColor(context, viewGetTabColor(context!!,themeType!!))

        ObjectAnimator.ofFloat(img, "rotation",0f, 360f).apply {
            duration = 1500
            repeatCount = INFINITE
            start()
        }


    }
}

fun viewGetTabColor(context:Context,themeType:String): Int {

    if (themeType=="ig") {
        return R.color.colorIGpink
    }
    else if (themeType=="tw") {
        return R.color.colorTWblue
    }
    else if (themeType=="li") {
        return R.color.colorLIblue
    }
    else if (themeType=="rd") {
        return R.color.colorRDorange
    }
    else if (themeType=="gn") {
        return R.color.colorNews
    }
    else return R.color.colorFBblue
}


fun changeMaintThemescolors (context: Context,themeType:String){
    val color = viewGetTabColor(context,themeType);
    val activity = (context as AppCompatActivity)
    val window: Window = activity.getWindow()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = context.resources.getColor(color)
        window.navigationBarColor = context.resources.getColor(color)
    }

    activity.nav_view.setBackgroundResource(color);
    //activity.nav_header.setBackgroundColor(color)
   /* val rootview = window.getDecorView().rootView
    rootview.setBackgroundColor(color)*/

}



fun viewshouldInterceptRequest (view:WebView, request:WebResourceRequest,src:String): Boolean {

    var url = request.getUrl().toString()
   // var req:String? = request!!.requestHeaders.toString()
  //  println("MYLOG:"+src + "URL: " + url)



    var fbadArray = arrayListOf("analytics","googlead","cedexis","track", "ads", /*"logging_client_events",*/ "googletag","adsystem","platform-telemetry","checked_installed_related_apps")
    var liadArray = arrayListOf("analytics","googlead","cedexis","track", "ads", /*"logging_client_events",*/ "googletag","adsystem","platform-telemetry","facebook"/*,"XMLHttpRequest"*/)
    var igadArray = arrayListOf("analytics","googlead","cedexis","track", "logging_client_events", "googletag","adsystem","platform-telemetry"/*,"XMLHttpRequest"*/)

    var adArray = arrayListOf("")

    when(src) {
        "fb" -> adArray=fbadArray
        "ig" -> adArray=igadArray
        "li" -> adArray=liadArray
        else -> adArray=liadArray
    }

        for (it in adArray) {

            if (url.contains(it) )
               // || req!!.contains(it) )
            {
                if (src=="fb" && (it=="track" || it =="ads")){
                    if (url.contains("tracking_string") || url.contains("uploads")){
                      //  println("MYLOG: notkilled exception: "+src)
                        return false
                    }
                     else {return true}

                } else {
                  //  println("MYLOG: killed: "+src + "URL_KILLED "+url)
                    return true
                }

            }
        }

        return false
    }


