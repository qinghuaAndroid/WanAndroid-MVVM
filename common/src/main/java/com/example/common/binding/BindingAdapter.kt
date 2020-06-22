package com.example.common.binding

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.example.devlibrary.ext.listener.textWatcher


@BindingAdapter(value = ["afterTextChanged"])
fun EditText.afterTextChanged(action: (String) -> Unit) {
    textWatcher {
        onTextChanged { s, start, before, count ->
            action(s.toString())
        }
    }
}

