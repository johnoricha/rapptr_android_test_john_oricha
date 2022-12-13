package com.rapptrlabs.androidtest.util

import com.google.android.material.textfield.TextInputLayout

object Extensions {
    fun TextInputLayout.checkValidation(inputValue: Boolean, errorMsg: String) {
        if (inputValue) {
            this.error = ""
            this.isErrorEnabled = false

        } else {
            this.error = errorMsg
            this.isErrorEnabled = true
        }
    }
}