package com.wan.common.binding

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter


@BindingAdapter(value = ["afterTextChanged"])
fun EditText.afterTextChanged(action: (String) -> Unit) {
    doOnTextChanged { text, start, before, count ->
        action(text.toString())
    }
}

