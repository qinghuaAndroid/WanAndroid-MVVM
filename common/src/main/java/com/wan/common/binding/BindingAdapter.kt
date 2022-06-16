package com.wan.common.binding

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.wan.baselib.ext.listener.textWatcher


@BindingAdapter(value = ["afterTextChanged"])
fun EditText.afterTextChanged(action: (String) -> Unit) {
    textWatcher {
        onTextChanged { s, start, before, count ->
            action(s.toString())
        }
    }
}

