package com.team.wastewise.view.customtextfield

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class EmailField @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //DO NOTHING
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                if (!p0.toString().matches(emailPattern.toRegex())){
                    setError("Email tidak valid", null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                //DO NOTHING
            }

        })
    }
}