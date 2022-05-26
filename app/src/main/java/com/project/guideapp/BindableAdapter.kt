package com.project.guideapp

import android.view.View
import androidx.databinding.BindingAdapter


object BindableAdapter {
    @BindingAdapter("android:progressVisibility")
    @JvmStatic
    fun setVisibility(view: View?, value: String) {
        if (value == "LOADING") {
            view?.visibility = View.VISIBLE
        } else
            view?.visibility = View.GONE
    }

}