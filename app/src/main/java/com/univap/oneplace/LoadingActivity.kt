package com.univap.oneplace

import android.animation.*
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity;
import com.univap.oneplace.util.*
import android.widget.RelativeLayout
import android.os.AsyncTask
import android.view.animation.LinearInterpolator




class LoadingActivity : AppCompatActivity() {
    var img:ImageView? = null
    var runningTask: AsyncTask<*, *, *>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewHideActionBar(this@LoadingActivity)

            setContentView(R.layout.activity_loading)
            val v: View = findViewById(android.R.id.content)
        var context:Context = this@LoadingActivity
            img = v.findViewById(R.id.nav_header_imageViewMain) as ImageView

/*
        val scaleXAnimation = ObjectAnimator.ofFloat(img, "scaleX", 0.2f, 1.2f)
        scaleXAnimation.setInterpolator(AccelerateDecelerateInterpolator())
        scaleXAnimation.setDuration(4000)
        val scaleYAnimation = ObjectAnimator.ofFloat(img, "scaleY", 0.2f, 1.2f)
        scaleYAnimation.setInterpolator(AccelerateDecelerateInterpolator())
        scaleYAnimation.setDuration(4000)
        val alphaAnimation = ObjectAnimator.ofFloat(img, "alpha", 0.5f, 1f)
        alphaAnimation.setInterpolator(AccelerateDecelerateInterpolator())
        alphaAnimation.setDuration(2000)
        val animatorSet = AnimatorSet()
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation)
        animatorSet.setStartDelay(0)
        animatorSet.start()


            ObjectAnimator.ofFloat(img, "rotation",0f, 360f).apply {
                duration = 2500
                // repeatCount = 1
                start()
            }

        val display = windowManager.defaultDisplay
        val size: Point = Point()
        display.getSize(size)
        val displayWidth:Float = size.x.toFloat()
        ObjectAnimator.ofFloat(img, "translationY", -displayWidth,displayWidth/3).apply {
            duration = 3500
            start()
        }*/




        val layout1:RelativeLayout = v.findViewById(R.id.relativeloading)
        /*
        ObjectAnimator.ofFloat(layout1, "alpha",0f, 1f).apply {
            duration = 1500
            start()
        }*/

       /* val animDrawable = layout1.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(3000)
        animDrawable.start()*/

        val backgroundOne = v.findViewById(R.id.background_one) as ImageView
        val backgroundTwo = v.findViewById(R.id.background_two) as ImageView
        val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.setRepeatCount(1)
        animator.setInterpolator(LinearInterpolator())
        animator.setDuration(10000L)
        animator.addUpdateListener(object: ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation:ValueAnimator) {
                val progress = animation.getAnimatedValue() as Float
                val width = backgroundOne.getWidth()
                val translationX = width * progress
                backgroundOne.setTranslationX(translationX)
                backgroundTwo.setTranslationX(translationX - width)
            }
        })
        animator.start()


       val welcomeThread = object:Thread() {
            override fun run() {
                try
                {
                    super.run()

                    Thread.sleep(2000)
                }
                catch (e:Exception) {
                }
                finally
                {
                    //val myIntent = Intent(this@LoadingActivity, IntroActivity::class.java)
                    //this@LoadingActivity.startActivity(myIntent)
                    finishAffinity()
                    finish()
                    viewStartMainActivity(this@LoadingActivity)

                }
            }
        }
        welcomeThread.start()
    }


}





