package com.univap.oneplace.settings


import android.content.Context
import android.view.Window
import com.univap.oneplace.R
import com.univap.oneplace.util.GetThemeType

fun GetFBColor(context: Context): Int {

    val themeType = GetThemeType(context)

    if (themeType == "2") {
        return R.color.colorFBBlack
    } else if (themeType == "3") {
        return R.color.colorFBGrey
    } else if (themeType == "4") {
        return R.color.colorFBRed
    } else if (themeType == "5") {
        return R.color.colorFBBlack2
    } else if (themeType == "6") {
        return R.color.colorFBOrange
    } else if (themeType == "7") {
        return R.color.colorFBGreen
    } else {
        return R.color.colorFBblue
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





fun Window.changeApperance(){

}