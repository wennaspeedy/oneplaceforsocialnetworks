package com.univap.oneplace

import android.content.Context
import com.github.paolorotolo.appintro.AppIntro
import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Window
import android.view.WindowManager
import com.univap.oneplace.intro_fragments.*
import com.univap.oneplace.util.PreferenceHelper
import java.util.*


class IntroActivity2 : AppIntro() {

    private var preferenceHelper: PreferenceHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main);

        addSlide(intro1())  //extend AppIntro and comment setContentView
        addSlide(intro2())
        addSlide(intro3())
        addSlide(intro4())
        addSlide(intro5())

    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)

       // preferenceHelper!!.putIntro("no")

        val intent = Intent(this@IntroActivity2, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        this.finish()
         finishAffinity();

        startActivity(intent)
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)

//        preferenceHelper!!.putIntro("no")

        val intent = Intent(this@IntroActivity2, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        finishAffinity();
        this.finish()
        startActivity(intent)

    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
        // Do something when the slide changes.
    }

    override fun attachBaseContext(newBase: Context) {

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(newBase)
        val lang = sharedPref!!.getString("lang", "none")
        val deflang = Locale.getDefault().getLanguage();
        if(lang == "none"){
            super.attachBaseContext(MyContextWrapper.wrap(newBase, deflang))
        } else {
            super.attachBaseContext(MyContextWrapper.wrap(newBase, lang))

        }
    }

}