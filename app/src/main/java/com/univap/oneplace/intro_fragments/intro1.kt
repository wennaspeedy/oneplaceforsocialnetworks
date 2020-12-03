package com.univap.oneplace.intro_fragments
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.univap.oneplace.R


class intro1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_intro1, container, false)
        // Inflate the layout for this fragment
        val backgroundOne = v.findViewById(R.id.background_one) as ImageView
        val backgroundTwo = v.findViewById(com.univap.oneplace.R.id.background_two) as ImageView
        val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.setRepeatCount(ValueAnimator.INFINITE)
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



        return v
    }


}
