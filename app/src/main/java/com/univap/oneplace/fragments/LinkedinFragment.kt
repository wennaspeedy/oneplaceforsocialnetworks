package com.univap.oneplace.fragments


import android.app.*
import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import android.view.*
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.os.Build
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Log
import android.view.animation.RotateAnimation
import android.webkit.*
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.univap.oneplace.R
import com.univap.oneplace.util.*
import kotlinx.android.synthetic.main.maincontent.*
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException

class LinkedinFragment : Fragment() {

    private var currentUrl: String = "https://www.linkedin.com/login/"
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
    var src: String = "li"
    var navimenu: BottomNavigationView? = null
    var mainFrameLayout: FrameLayout? = null
    var tempstring:String = "";

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_linkedin, container, false)
        val fragmentManager = getFragmentManager()
        val mySwipeRefreshLayout = v.findViewById(R.id.swipeContainer) as SwipeRefreshLayout

        sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPref!!.edit().putString("src", src).commit()

        img = v.findViewById(R.id.imgv) as ImageView
       // pbar = v.findViewById(R.id.pBar) as ProgressBar

        viewStartImgRotate(img!!, this!!.context!!)

        //pbar!!.setPbarColor(this!!.context!!, R.color.colorLIblue)
        navimenu = (this!!.context!! as AppCompatActivity).bottomNav
        mainFrameLayout = v.findViewById(R.id.frame) as FrameLayout
        mainFrameLayout!!.setTransparent()
       // frSaveFragmentPosition(sharedPref!!,src)
        //frSetThisMenuItemChecked((this!!.context!! as AppCompatActivity), R.id.linkedinFragment,sharedPref!!)
        //frKillOtherFragments(src, fragmentManager!!)
        viewHideActionBar((activity as AppCompatActivity))
        ChangeNaviColor(this!!.context!!, R.color.colorLIblue)
        setTheme(context!!, null, navimenu!!)

        myWebView = v.findViewById(R.id.webview) as WebView
        if (!viewIsOnline(context!!)){  myWebView!!.loadUrl(DEFAULT_ERROR_PAGE_PATH); }
        else{
            myWebView!!.initWebview(src)
            myWebView!!.loadUrl(currentUrl)
            myWebView!!.setOnKeyListener(myWebView!!, activity!!)
            myWebView!!.setDownloadListener(activity as AppCompatActivity)


        }


        myWebView!!.setWebChromeClient(MyWebChromeClient())

        myWebView!!.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

                val url: String = request?.url.toString()

                currentUrl = url

                if ((Uri.parse(url).getHost().contains("www.linkedin.com")) ||  (Uri.parse(url).getHost().contains("www.linkedin.com")) )
                {
                    return false ;
                }  else if ((Uri.parse(url).getHost().contains("runmain")))
                {
                    viewStartMainActivity(context!!)
                }else {
                    viewBuildExtLink(context!!,url)
                }

                return true
            }

            override fun onLoadResource(view: WebView?, url: String?) {
           //  println ("URLLI:"+url)
                if (url!!.contains("linkedin") && !(url!!.contains("track") || url!!.contains("mwlite") || url!!.contains("fizzy"))) {
                    viewInjectCSS(view!!,"li_login.css", context);
                   // println("URLLI-----------------INJECT")
                }





            }

            override fun onPageFinished(view: WebView?, url: String) {
                viewInjectCSS(view!!,"li_login.css", context);
                //viewInjectJS(view!!, context!!,"li_propagated.js")
                viewOnPageFinished(null, img!!, myWebView!!,mainFrameLayout!!)
                currentUrl = url

            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                viewOnReceivedError(myWebView!!, request!!, error!!, DEFAULT_ERROR_PAGE_PATH,context!!)
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
       frOnStop(null,img!!,myWebView!!,sharedPref!!)
    }
    override fun onResume() {
        super.onResume()
        frOnResume(myWebView!!, sharedPref!!, currentUrl, tempstring,img!!,mainFrameLayout!!,context!!)
        navimenu!!.menu.findItem(R.id.linkedinFragment).setChecked(true)//musi byt v pripade, ze se program nacte zpatky z pameti!!

    }
    override fun onPause() {
        super.onPause()
        //tempstring = myWebView!!.getUrl()
        //myWebView!!.loadUrl(DEFAULT_RELOAD_PAGE_PATH)
    }

    override fun onDestroy() {
        super.onDestroy()
        frOndestroy(null,img!!,myWebView!!)
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



