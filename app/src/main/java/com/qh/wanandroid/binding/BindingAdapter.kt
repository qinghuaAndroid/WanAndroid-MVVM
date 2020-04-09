package com.qh.wanandroid.binding

import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.devlibrary.ext.listener.textWatcher
import com.example.devlibrary.utils.ImageLoader
import com.qh.wanandroid.R

/**
 * Created by luyao
 * on 2019/12/18 16:16
 */
@BindingAdapter(value = ["afterTextChanged"])
fun EditText.afterTextChanged(action: (String) -> Unit) {
    textWatcher {
        onTextChanged { s, start, before, count ->
            action(s.toString())
        }
    }
}

@BindingAdapter(value = ["girlImgUrl"])
fun ImageView.loadGirlImg(url: String) {
    ImageLoader.load(this, url, R.mipmap.ic_girl_default)
}