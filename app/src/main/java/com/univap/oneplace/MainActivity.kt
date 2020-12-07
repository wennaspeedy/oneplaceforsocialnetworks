package com.univap.oneplace

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.univap.oneplace.settings.CustomActivity
import com.univap.oneplace.settings.FbSettingActivity
import com.univap.oneplace.settings.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.maincontent.*
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var doubleBackToExitPressedOnce = true
    private lateinit var navController: NavController
    //lateinit var mAdView : AdView
    var sharedPref: SharedPreferences? =  null
    private var locale: Locale? = null
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*MobileAds.initialize(this,
            "ca-app-pub-7065309844332971~1938672281")
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)*/


       /* AdBlocker.init(this, Schedulers.io());*/
        //theme.applyStyle(R.style.AppTheme2,true)

        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.fragment)

       //navController.navigate(R.id.twitterFragment)

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getMyContext())
        val showfr:Boolean = sharedPref!!.getBoolean("showfr", false)
        val fbPref = sharedPref!!.getBoolean("switchfacebook",true)
        val twPref = sharedPref!!.getBoolean("switchtwitter", true)
        val igPref = sharedPref!!.getBoolean("switchinstagram", true)
        val liPref = sharedPref!!.getBoolean("switchlinkedin", false)
        val rdPref = sharedPref!!.getBoolean("switchreddit", true)
        val gnPref = sharedPref!!.getBoolean("switchnews", false)
        val menu = bottomNav.getMenu();
        menu.clear();

        var isFirst:Boolean = false
        if (fbPref) {
            menu.add(Menu.NONE, R.id.facebookFragment, Menu.NONE, "Facebook")
                .setIcon(R.drawable.ic_fb)
                .setOnMenuItemClickListener {
                    if (drawer_layout.isDrawerVisible(GravityCompat.START)) { true }
                    else { false}
                }

            bottomNav.setupWithNavController(navController);
            val view = findViewById<View>(R.id.facebookFragment) // BottomNavigationView menu item id

            view.setOnLongClickListener {
                drawer_layout.openDrawer(GravityCompat.START)
                false
            }
            if(!isFirst){
                bottomNav.setSelectedItemId(R.id.facebookFragment);
                isFirst = true
            }

        }
        if (twPref) {
            menu.add(Menu.NONE, R.id.twitterFragment, Menu.NONE, "Twitter")
                .setIcon(R.drawable.ic_tw)
                .setOnMenuItemClickListener {
                    if (drawer_layout.isDrawerVisible(GravityCompat.START)) { true }
                    else { false}
                }
            bottomNav.setupWithNavController(navController);
            val view = findViewById<View>(R.id.twitterFragment) // BottomNavigationView menu item id

            view.setOnLongClickListener {
                drawer_layout.openDrawer(GravityCompat.START)
                false
            }
            if(!isFirst){
                bottomNav.setSelectedItemId(R.id.twitterFragment);
                isFirst = true
            }
        }
        if (igPref) {
            menu.add(Menu.NONE, R.id.instaFragment, Menu.NONE, "Instagram")
                .setIcon(R.drawable.ic_ig)
                .setOnMenuItemClickListener {
                    if (drawer_layout.isDrawerVisible(GravityCompat.START)) { true }
                    else { false}
                }
            bottomNav.setupWithNavController(navController);
            val view = findViewById<View>(R.id.instaFragment) // BottomNavigationView menu item id

            view.setOnLongClickListener {
                drawer_layout.openDrawer(GravityCompat.START)
                false
            }
            if(!isFirst){
                bottomNav.setSelectedItemId(R.id.instaFragment);
                isFirst = true
            }
        }
        if (liPref) {
            menu.add(Menu.NONE, R.id.linkedinFragment, Menu.NONE, "LinkedIn")
                .setIcon(R.drawable.ic_lidark)
                .setOnMenuItemClickListener {
                    if (drawer_layout.isDrawerVisible(GravityCompat.START)) { true }
                    else { false}
                }
            bottomNav.setupWithNavController(navController);
            val view = findViewById<View>(R.id.linkedinFragment) // BottomNavigationView menu item id

            view.setOnLongClickListener {
                drawer_layout.openDrawer(GravityCompat.START)
                false
            }
            if(!isFirst){
                bottomNav.setSelectedItemId(R.id.linkedinFragment);
                isFirst = true
            }
        }
        if (rdPref) {
            menu.add(Menu.NONE, R.id.redditFragment, Menu.NONE, "Reddit")
                .setIcon(R.drawable.ic_reddit)
                .setOnMenuItemClickListener {
                    if (drawer_layout.isDrawerVisible(GravityCompat.START)) { true }
                    else { false}
                }

            bottomNav.setupWithNavController(navController);
            val view = findViewById<View>(R.id.redditFragment) // BottomNavigationView menu item id

            view.setOnLongClickListener {
                drawer_layout.openDrawer(GravityCompat.START)
                false
            }
            if(!isFirst){
                bottomNav.setSelectedItemId(R.id.redditFragment);
                isFirst = true
            }
        }
        if (gnPref) {
            menu.add(Menu.NONE, R.id.newsFragment, Menu.NONE, R.string.gnews)
                .setIcon(R.drawable.ic_news)
                .setOnMenuItemClickListener {
                    if (drawer_layout.isDrawerVisible(GravityCompat.START)) { true }
                    else { false}

                }

            bottomNav.setupWithNavController(navController);
            val view = findViewById<View>(R.id.newsFragment) // BottomNavigationView menu item id

            view.setOnLongClickListener {
                drawer_layout.openDrawer(GravityCompat.START)
                false
            }
            if(!isFirst){
                bottomNav.setSelectedItemId(R.id.newsFragment);
                isFirst = true
            }
        }


        val view = findViewById<View>(R.id.facebookFragment) // BottomNavigationView menu item id

        view.setOnLongClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
            false
        }

        val hidenavi = sharedPref!!.getBoolean("hidenavi", false)
        if(hidenavi){bottomNav.setVisibility(View.GONE);}

        //Setting up the action bar
        NavigationUI.setupActionBarWithNavController(this, navController)
        //Nastav listener na akce pro vysouvaci panel
        nav_view.setNavigationItemSelectedListener(this)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        //println("showfr: "+showfr)
            //show and load support window
            if (!showfr) {


                            try {
                                val builder = AlertDialog.Builder(this@MainActivity)

                                builder.setMessage(R.string.freeMessage)
                                    .setTitle(R.string.freeMessageTitle)
                                    .setCancelable(false)
                                    .setNegativeButton(
                                        R.string.negativeButton,
                                        DialogInterface.OnClickListener { dialog, id ->
                                            Toast.makeText(
                                                this@MainActivity, R.string.why,
                                                Toast.LENGTH_LONG
                                            ).show();

                                        })
                                    .setPositiveButton(
                                        R.string.positiveButton,
                                        DialogInterface.OnClickListener { dialog, id ->
                                            /*val i = Intent(Intent.ACTION_VIEW)

                                            i.data =
                                                Uri.parse("https://play.google.com/store/apps/details?id=com.univap.fsocialnetworks.pro")
                                            startActivity(i)*/
                                            val myIntent = Intent(Intent.ACTION_VIEW)
                                            myIntent.setData(Uri.parse("https://www.paypal.me/wnzk"))
                                            this@MainActivity.startActivity(myIntent)

                                        })
                                val alert = builder.create()
                                if (alert.isShowing()) {
                                    alert.dismiss();
                                } else {
                                    alert.show()
                                }
                            } catch (e: Exception) {
                            }



               // timer.schedule(doAsynchronousTask, 2000, 100000 * 60) //10000*60
            }




        //val fragmentManager: FragmentManager? = supportFragmentManager
       // frLoadPositionOnRestart(fragmentManager!!,sharedPref!!,this,fragment)

    }


    fun getMyContext(): Context {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val lang = sharedPref!!.getString("lang", "none")
        //println("VALs:"+lang)
        val deflang =  Locale.getDefault().getLanguage(); //Resources.getSystem().getConfiguration().locale.getLanguage(); //
        //println("LANG1: "+lang)
//println("LANG: "+deflang)
        if(lang == "none"){
            return applicationContext
        } else {
            return MyContextWrapper.wrap(applicationContext, lang)
        }
    }

    override fun attachBaseContext(newBase: Context) {

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(newBase)
        val lang = sharedPref!!.getString("lang", "none")

        val deflang = Locale.getDefault().getLanguage(); //Locale.getDefault().getDisplayLanguage();
        //println("VALs:"+lang)
        //println("VALs:"+deflang)
        if(lang == "none"){
            super.attachBaseContext(MyContextWrapper.wrap(newBase, deflang))
        } else {
            super.attachBaseContext(MyContextWrapper.wrap(newBase, lang))

        }
    }



/*
var first = 0

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage(R.string.alclose)
            .setTitle(R.string.alquit)
            .setCancelable(true)
            .setOnCancelListener(object: DialogInterface.OnCancelListener {
            override fun onCancel(dialog:DialogInterface) {
                // dialog dismiss without button press
                first = 0
                //println("MYLOG: "+first)
            }
        })
            .setPositiveButton(R.string.yes, DialogInterface.OnClickListener { dialog, id ->
                finishAffinity();
                System.exit(0);
            })

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val doubleb = sharedPref!!.getBoolean("doubleb",false)
            if (doubleb) {
                val alert = builder.create()
                if (alert.isShowing()) {
                    alert.dismiss();
                    return super.onKeyLongPress(keyCode, event)
                }else {
                    //println("MYLOG: firstkeycodeback "+first)
                    if (first == 0){
                        alert.show()
                        first = 1
                    }

                    return true}

        }}
        return super.onKeyLongPress(keyCode, event)
    }*/
/*
    override fun onBackPressed() {
        //sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        val doubleb = sharedPref!!.getBoolean("doubleb",false)
        if (doubleBackToExitPressedOnce) {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            else { //navController.navigateUp()
                doubleBackToExitPressedOnce = true
                 }

            if(doubleb){
                doubleBackToExitPressedOnce = false
                Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = true }, 300)

            }
            return
        }

            if(!doubleBackToExitPressedOnce) {
                try {
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setMessage(R.string.alclose)
                        .setTitle(R.string.alquit)
                        .setCancelable(true)
                        .setPositiveButton(R.string.yes, DialogInterface.OnClickListener { dialog, id ->
                            finishAffinity();
                            System.exit(0);
                        })
                    val alert = builder.create()
                    if (alert.isShowing()) {
                        alert.dismiss();
                    }else {
                        alert.show()}
                } catch (e: Exception) {
                }
            }
    }*/


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.nav_custom -> {
                val myIntent = Intent(this@MainActivity, CustomActivity::class.java)
                this@MainActivity.startActivity(myIntent)

            }
            R.id.nav_fbsetting -> {
                val myIntent = Intent(this@MainActivity, FbSettingActivity::class.java)
                this@MainActivity.startActivity(myIntent)

            }
            R.id.nav_setting -> {
                val myIntent = Intent(this@MainActivity, SettingActivity::class.java)
                this@MainActivity.startActivity(myIntent)

            }
            R.id.nav_tutorial -> {
                val myIntent = Intent(this@MainActivity, IntroActivity2::class.java)
                this@MainActivity.startActivity(myIntent)

            }


            R.id.nav_donate -> {
                val myIntent = Intent(Intent.ACTION_VIEW)
                myIntent.setData(Uri.parse("https://www.paypal.me/wnzk"))
                this@MainActivity.startActivity(myIntent)

            }
            R.id.nav_info -> {
                try {
                    val builder = AlertDialog.Builder(this@MainActivity)
                    //val app_name = PpackageInfo.applicationInfo.loadLabel(packageManager).toString()

                    builder.setMessage(this.getString(R.string.app_name) +"\n\n"+"Version: "+ BuildConfig.VERSION_NAME+"\n"
                    )
                        .setTitle("App info")
                        .setCancelable(true)

                        .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->


                        })
                    val alert = builder.create()
                    if (alert.isShowing()) {
                        alert.dismiss();
                    }else {
                        alert.show()}
                } catch (e: Exception) {
                }

            }
            R.id.nav_exit -> {

                finishAffinity();
                System.exit(0);

            }
            R.id.nav_bug -> {
                val myIntent = Intent(Intent.ACTION_VIEW)
                myIntent.setData(Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSc2s8mCLUH3a2DzX9rK-cTLCTAn2bCt7B37xwpNTLU3u9MXWg/viewform"))
                this@MainActivity.startActivity(myIntent)
            }


        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }




}




