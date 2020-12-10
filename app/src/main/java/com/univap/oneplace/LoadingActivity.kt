package com.univap.oneplace



import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.univap.oneplace.util.viewHideActionBar
import com.univap.oneplace.util.viewStartMainActivity


class LoadingActivity : AppCompatActivity() {
    var img:ImageView? = null
    var runningTask: AsyncTask<*, *, *>? = null


    fun animateIt(endcolor: Int, img: ImageView) {
        val from = R.color.bg2
        val to = endcolor

        val anim = ValueAnimator()
        anim.setIntValues(from, to)
        anim.setEvaluator(ArgbEvaluator())
        anim.addUpdateListener { valueAnimator -> img.setBackgroundResource(valueAnimator.animatedValue as Int) }
        anim.setStartDelay(1100);
        anim.duration = 1000
        anim.start()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewHideActionBar(this@LoadingActivity)

            setContentView(R.layout.activity_loading)
            val v: View = findViewById(android.R.id.content)
        var context:Context = this@LoadingActivity
            img = v.findViewById(R.id.nav_header_imageViewMain) as ImageView


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

        val logo = v.findViewById(R.id.nav_header_imageViewMain) as ImageView

        val loadIg = v.findViewById(R.id.loadIg) as ImageView
        val loadFb = v.findViewById(R.id.loadFb) as ImageView
        val loadRd = v.findViewById(R.id.loadRd) as ImageView
        val loadTw = v.findViewById(R.id.loadTw) as ImageView
        val loadGn = v.findViewById(R.id.loadGn) as ImageView
        val loadLi = v.findViewById(R.id.loadLi) as ImageView

        val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.setRepeatCount(1)
        animator.setInterpolator(LinearInterpolator())
        animator.setDuration(10000L)
        animator.setStartDelay(1100);
        animator.addUpdateListener(object: ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation:ValueAnimator) {
                val progress = animation.getAnimatedValue() as Float
                val width = backgroundOne.getWidth()
                val translationX = width * progress
                //loadTw.setTranslationX(translationX)
                loadTw.setTranslationY(-translationX*4)
                loadTw.setTranslationX(-translationX*4)

                loadFb.setTranslationY(translationX*4)
                loadFb.setTranslationX(translationX*4)

                loadIg.setTranslationX(translationX*4)
                loadIg.setTranslationY(-translationX*4)

                loadRd.setTranslationX(-translationX*4)
                loadRd.setTranslationY(translationX*4)

                loadGn.setTranslationX(-translationX*4)
                //loadGn.setTranslationX(-translationX*2)

                loadLi.setTranslationX(translationX*4)
                //backgroundTwo.setTranslationX(translationX - width)
                loadTw.setVisibility(View.VISIBLE);
                loadFb.setVisibility(View.VISIBLE);
                loadIg.setVisibility(View.VISIBLE);
                loadRd.setVisibility(View.VISIBLE);
                loadGn.setVisibility(View.VISIBLE);
                loadLi.setVisibility(View.VISIBLE);

            }
        })
        animator.start()

        //animate from your current color to red


        animateIt(R.color.colorFBblue,loadFb)
        animateIt(R.color.colorTWblue,loadTw)
        animateIt(R.color.colorIGpink,loadIg)
        animateIt(R.color.colorLIblue,loadLi)
        animateIt(R.color.colorRDorange,loadRd)
        animateIt(R.color.colorNews,loadGn)





        val rotate = RotateAnimation(
            180F, 360F, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        val rotate2 = RotateAnimation(
            360F, 0F, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 650
        rotate2.duration = 800

        backgroundOne.startAnimation(rotate2)
        logo.startAnimation(rotate)

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





