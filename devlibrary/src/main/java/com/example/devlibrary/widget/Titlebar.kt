package com.example.devlibrary.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.devlibrary.R
import com.example.devlibrary.utils.ResourcesUtils.getString
import kotlinx.android.synthetic.main.titlebar.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * @author FQH
 * Create at 2020/4/13.
 */
class Titlebar(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.titlebar, this)
        getAttr(attrs)
    }

    private fun getAttr(attrs: AttributeSet?) {

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        tvLeft.onClick { if (context is Activity) (context as Activity).finish() }
    }

    fun setTitle(charSequence: CharSequence) {
        tvTitle.text = charSequence
    }

    fun setTitle(@StringRes resId: Int) {
        tvTitle.text = getString(resId)
    }

    fun getTitle(): CharSequence {
        return tvTitle.text
    }
}