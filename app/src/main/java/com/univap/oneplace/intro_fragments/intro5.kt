package com.univap.oneplace.intro_fragments
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import android.widget.ImageView
import com.univap.oneplace.R


class intro5 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setRetainInstance(true)
        val v = inflater.inflate(R.layout.fragment_intro5, container, false)



        val img = v.findViewById(R.id.paypal) as ImageView

        img.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_VIEW)
            myIntent.setData(Uri.parse("https://www.paypal.me/wnzk"))
            this.startActivity(myIntent)
        }
        val img2 = v.findViewById(R.id.gplay) as ImageView

        img2.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://play.google.com/store/apps/details?id=com.univap.fsocialnetworks.pro")
            startActivity(i)
        }



        return v
    }


}