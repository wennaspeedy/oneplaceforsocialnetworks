package com.univap.oneplace.fragments


import android.annotation.SuppressLint
import android.app.*
import android.content.*
import android.content.ContentValues.TAG
import android.net.Uri
import android.os.*
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.*
import android.view.ViewGroup
import android.view.LayoutInflater
import android.webkit.*
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.univap.oneplace.util.*
import kotlinx.android.synthetic.main.maincontent.*
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.webkit.WebView
import android.webkit.WebChromeClient
import android.widget.FrameLayout
import com.univap.oneplace.R
import java.io.*
import java.util.regex.Pattern


class FacebFragment : Fragment() {

    private var currentUrl: String = "https://m.facebook.com"
    private var myWebView: WebView? = null
    val REQUEST_CODE_LOLIPOP = 1
    private var mFilePathCallback: ValueCallback<Array<Uri>>? = null
    private var mCameraPhotoPath: String? = null
    //var pbar: ProgressBar? = null
    var navimenu: BottomNavigationView? = null
    var img: ImageView? = null
    var sharedPref: SharedPreferences? = null
    private val DEFAULT_ERROR_PAGE_PATH = "file:///android_asset/default_error_page.html"
    private val DEFAULT_RELOAD_PAGE_PATH = "file:///android_asset/default_reload_page.html"
    var src: String = "fb"
    var mainFrameLayout: FrameLayout? = null
    var tempstring: String = "";


    ///////////////////////BEGIN ONCREATE
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreate(savedInstanceState)
        setRetainInstance(true)
        val v = inflater.inflate(R.layout.fragment_faceb, container, false)
        //val fragmentManager = getFragmentManager()
        val mySwipeRefreshLayout = v.findViewById(R.id.swipeContainer) as SwipeRefreshLayout
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPref!!.edit().putString("src", src).commit()
        img = v.findViewById(R.id.imgv) as ImageView
        // pbar = v.findViewById(R.id.pBar) as ProgressBar
        navimenu = (this!!.context!! as AppCompatActivity).bottomNav
        mainFrameLayout = v.findViewById(R.id.frame) as FrameLayout
        mainFrameLayout!!.setTransparent()
        // frKillOtherFragments(src, fragmentManager!!)
      //  frSaveFragmentPosition(sharedPref!!, src)
       // frSetThisMenuItemChecked((this!!.context!! as AppCompatActivity), R.id.facebookFragment, sharedPref!!)
        viewHideActionBar((activity as AppCompatActivity))
        //AdBlocker.init(this!!.context!!, Schedulers.io());
        viewStartImgRotate(img!!, this!!.context!!)
        SetFbTheme(context!!,/* pbar!!,*/ navimenu!!)  //Dynamically setting of facebook theme

       // context!!.theme.applyStyle(R.style.AppTheme2,false)


        myWebView = v.findViewById(R.id.webview) as WebView
        if (!viewIsOnline(context!!)) {
            myWebView!!.loadUrl(DEFAULT_ERROR_PAGE_PATH);
        } else {
            myWebView!!.getSettings().setJavaScriptEnabled(false);
            myWebView!!.stopLoading();
            myWebView!!.getSettings().setJavaScriptEnabled(true);
            myWebView!!.initWebview(src)
            myWebView!!.loadUrl(currentUrl)
            myWebView!!.setDownloadListener(activity as AppCompatActivity)
            myWebView!!.setOnKeyListener(myWebView!!, activity!!)


        }
        myWebView!!.webChromeClient = MyWebChromeClient()
        myWebView!!.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url: String = request?.url.toString()
              //  println ("FBURL:"+url)


                if ((Uri.parse(url).getHost().equals("m.facebook.com"))) {
                    return false;
                } else if ((Uri.parse(url).getHost().contains("runmain")))//my static html pages->link
                {
                    viewStartMainActivity(context!!)
                } else

                if (request?.url!!.scheme.equals("intent"))
                {
                    val uri = Uri.parse(url)
                    val appPackage = getAppPackageFromUri(uri)

                    if (appPackage != null)
                    {
                        val manager = getContext()!!.getPackageManager()
                        val appIntent = manager.getLaunchIntentForPackage(appPackage)
                        if (appIntent != null)
                        {
                            getActivity()!!.startActivity(appIntent)
                        }
                        else
                        {
                            openExternalWebsite("https://play.google.com/store/apps/details?id=" + appPackage)
                        }
                    }
                }

                 else {
                    viewBuildExtLink(context!!, url)
                }
                return true
            }

            override fun onPageFinished(view: WebView?, url: String) {
                super.onPageFinished(view, url);
                val sponsored = sharedPref!!.getBoolean("sponsored", false)
                if (sponsored){viewInjectJS(view!!, context!!,"fb_spon.js")}//hide sponsored on the first load
                viewOnPageFinished(null, img!!, view!!, mainFrameLayout!!)
                currentUrl = url
                //pridej podminku na type=1 ze sharedpref
               // fbInjector(sharedPref!!, myWebView!!, context!!);
                var tabmode = sharedPref!!.getString("tabmode", "2")
                if (tabmode == "1") {
                            fbInjector(sharedPref!!, myWebView!!, context!!);
                }

            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                viewOnReceivedError(myWebView!!, request!!, error!!, DEFAULT_ERROR_PAGE_PATH, context!!)

            }

            override fun onLoadResource(view: WebView?, url: String?) {
                //if (url!!.contains("m.facebook.com")){println ("URL:"+url)}
                fbOnLoadResource(view!!, url!!,context!!,sharedPref!!)



        }

            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                var overRequest = viewshouldInterceptRequest(view,request,src)

                if (overRequest){
                    return WebResourceResponse("text/javascript", "UTF-8", ByteArrayInputStream("".toByteArray()))
                }
                else {
                    return super.shouldInterceptRequest(view, request)
                }
            }
        }
        mySwipeRefreshLayout.setRefresh(myWebView!!, currentUrl, sharedPref!!)
        return v

    }
    ///////////////////////END ONCREATE

    override fun onStop() {
        // println ("URLSTOP: "+myWebView!!.getUrl())

        val someString: String? = myWebView!!.url
        tempstring = currentUrl
        someString.notNull {
            tempstring = myWebView!!.url
        }

       // println ("URLSTOP: "+tempstring)
        frOnStop(null, img!!, myWebView!!, sharedPref!!)
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()
        myWebView.notNull {
            frOndestroy(null, img!!, myWebView!!)

        }

    }

    override fun onResume() {
        super.onResume()
        frOnResume(myWebView!!, sharedPref!!, currentUrl, tempstring,img!!,mainFrameLayout!!,context!!)
        navimenu!!.menu.findItem(R.id.facebookFragment).setChecked(true)//musi byt v pripade, ze se program nacte zpatky z pameti!!
        val sponsored = sharedPref!!.getBoolean("sponsored", false)
        if (sponsored){viewInjectJS(myWebView!!, context!!,"fb_spon.js")}

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_LOLIPOP -> {
                var results: Array<Uri>? = null
                // Check that the response is a good one
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        // If there is not data, then we may have taken a photo
                        if (mCameraPhotoPath != null) {
                            results = arrayOf<Uri>(Uri.parse(mCameraPhotoPath))
                        }
                        else{
                          return
                        }
                    } else {
                        val dataString = data.getDataString()
                        if (dataString != null) {
                            results = arrayOf<Uri>(Uri.parse(dataString))
                        }
                    }
                }
                mFilePathCallback!!.onReceiveValue(results)
                mFilePathCallback = null
            }
        }

    }
    private fun getAppPackageFromUri(intentUri:Uri): String? {
        val pattern = Pattern.compile("package=(.*?);")
        val matcher = pattern.matcher(intentUri.getFragment())
        if (matcher.find())
            return matcher.group(1)
        return null
    }
    private fun openExternalWebsite(url:String) {
        val webeIntent = Intent(Intent.ACTION_VIEW)
        webeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        webeIntent.setData(Uri.parse(url))
        getActivity()!!.startActivity(webeIntent)
    }
    inner class MyWebChromeClient : WebChromeClient() {

        override fun onShowFileChooser(
            webView: WebView, filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            if (mFilePathCallback != null) {
                mFilePathCallback!!.onReceiveValue(null)
            }
            mFilePathCallback = filePathCallback
            var takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(activity!!.getPackageManager()) != null) {
                // Create the File where the photo should go
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath)
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.e(TAG, "Unable to create Image File", ex)
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath()
                    takePictureIntent.putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile)
                    )
                }
            }
            val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
            contentSelectionIntent.setType("image/*")
            var intentArray: Array<Intent>? = null
            if (takePictureIntent != null) {
                intentArray = arrayOf<Intent>(takePictureIntent)
            }
            val chooserIntent = Intent(Intent.ACTION_CHOOSER)
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
            chooserIntent.putExtra(Intent.EXTRA_TITLE, R.string.filechoose)
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
            startActivityForResult(chooserIntent, REQUEST_CODE_LOLIPOP)
            return true
        }
    }




}




