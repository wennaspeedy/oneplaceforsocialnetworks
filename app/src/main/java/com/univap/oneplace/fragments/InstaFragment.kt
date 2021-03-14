package com.univap.oneplace.fragments


import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import android.view.*
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.animation.RotateAnimation
import android.webkit.*
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.univap.oneplace.R
import com.univap.oneplace.util.*
import kotlinx.android.synthetic.main.maincontent.*
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException

class InstaFragment : Fragment() {

    private var currentUrl: String = "https://www.instagram.com"
    private var myWebView: WebView? = null
    val REQUEST_CODE_LOLIPOP = 1
    private var mFilePathCallback: ValueCallback<Array<Uri>>? = null
    private var mCameraPhotoPath: String? = null
    var rotateAnim: RotateAnimation? = null
    private val DEFAULT_ERROR_PAGE_PATH = "file:///android_asset/default_error_page.html"
    private val DEFAULT_RELOAD_PAGE_PATH ="file:///android_asset/default_reload_page.html"
    var sharedPref: SharedPreferences? = null
   // var pbar: ProgressBar? = null
    var img:ImageView? = null
    var src:String = "ig"
    var navimenu: BottomNavigationView? = null
    var mainFrameLayout: FrameLayout? = null
    var tempstring:String = "";

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // val fragmentManager = getFragmentManager()
        val v = inflater.inflate(R.layout.fragment_insta, container, false)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPref!!.edit().putString("src", src).commit()

        img = v.findViewById(R.id.imgv) as ImageView
        viewStartImgRotate(img!!, this!!.requireContext())
        //pbar = v.findViewById(R.id.pBar) as ProgressBar
        //pbar!!.setPbarColor(this!!.context!!, R.color.colorIGpink)
        navimenu = (this!!.requireContext() as AppCompatActivity).bottomNav
        mainFrameLayout = v.findViewById(R.id.frame) as FrameLayout
        mainFrameLayout!!.setTransparent()
        //frSaveFragmentPosition(sharedPref!!,src)
        //frSetThisMenuItemChecked((this!!.context!! as AppCompatActivity), R.id.instaFragment,sharedPref!!)
        //frKillOtherFragments(src, fragmentManager!!)
        viewHideActionBar((activity as AppCompatActivity))
        ChangeNaviColor(this!!.requireContext(), R.color.colorIGpink)
        setTheme(requireContext(), null, navimenu!!)
        changeMaintThemescolors(activity as AppCompatActivity,src)

        myWebView = v.findViewById(R.id.webview) as WebView
        //CookieManager.getInstance().setAcceptCookie(true);

        if (!viewIsOnline(requireContext())){  myWebView!!.loadUrl(DEFAULT_ERROR_PAGE_PATH); }
else {

            myWebView!!.stopLoading();

            myWebView!!.initWebview(src)
            myWebView!!.loadUrl(currentUrl)
            myWebView!!.setOnKeyListener(myWebView!!, requireActivity())
            myWebView!!.setDownloadListener(activity as AppCompatActivity)

        }

        myWebView!!.setWebChromeClient(MyWebChromeClient())

        myWebView!!.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url: String = request?.url.toString()
                currentUrl = url
                if ((Uri.parse(url).getHost().equals("www.instagram.com")) || (Uri.parse(url).toString().contains("facebook.com/dialog/oauth"))
                    || (Uri.parse(url).toString().contains("dialog/oauth")) || (Uri.parse(url).toString().contains("facebook.com/login.php")) || (Uri.parse(url).getHost()!!.contains("graph.instagram.com"))
                ) {
                    return false;
                } else if ((Uri.parse(url).getHost()!!.contains("runmain"))) {
                    viewStartMainActivity(context!!)
                } else {
                    viewBuildExtLink(context!!, url)
                }

                return true

            }

            override fun onPageFinished(view: WebView?, url: String) {
                viewInjectCSS(view!!, "ig_login.css", context);
                viewOnPageFinished(null, img!!, myWebView!!,mainFrameLayout!!)
                currentUrl = url

            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)

                viewOnReceivedError(view!!, request!!, error!!, DEFAULT_ERROR_PAGE_PATH,context!!)

            }
            override fun shouldInterceptRequest(view:WebView, request:WebResourceRequest ):WebResourceResponse? {
                var overRequest = viewshouldInterceptRequest(view,request,src)

                if (overRequest){
                    return WebResourceResponse("text/javascript", "UTF-8", ByteArrayInputStream("".toByteArray()))
                }
                else {
                    return super.shouldInterceptRequest(view, request)
                }
            }

        }
        val mySwipeRefreshLayout = v.findViewById(R.id.swipeContainer) as SwipeRefreshLayout
        mySwipeRefreshLayout.setRefresh(myWebView!!, currentUrl,sharedPref!!)

        return v

    }
    override fun onStop() {
        super.onStop()
        val someString: String? = myWebView!!.url
        tempstring = currentUrl
        someString.notNull {
            tempstring = myWebView!!.url
        }
        frOnStop(null, img!!, myWebView!!, sharedPref!!)
    }

    override fun onDestroy() {
        super.onDestroy()

         frOndestroy(null, img!!, myWebView!!)


    }

    override fun onResume() {
        super.onResume()
        frOnResume(myWebView!!, sharedPref!!, currentUrl, tempstring,img!!,mainFrameLayout!!,requireContext())
        navimenu!!.menu.findItem(R.id.instaFragment).setChecked(true)//musi byt v pripade, ze se program nacte zpatky z pameti!!

    }
    override fun onPause() {
        super.onPause()

       // myWebView!!.loadUrl(DEFAULT_RELOAD_PAGE_PATH)
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

    inner class MyWebChromeClient : WebChromeClient() {
        override fun onGeolocationPermissionsShowPrompt(origin:String, callback:GeolocationPermissions.Callback) {
            callback.invoke(origin, true, false)
        }
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
                    Log.e(ContentValues.TAG, "Unable to create Image File", ex)
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



