package com.rapptrlabs.androidtest.util

import com.google.android.material.textfield.TextInputLayout

object Extensions {
    fun TextInputLayout.checkValidation(inputValue: Boolean, errorMsg: String) {
        if (!inputValue) {
//            Timber.d(errorMsg)
            this.error = errorMsg
            this.isErrorEnabled = true
        } else {
//            Timber.d("free")
            this.error = ""
            this.isErrorEnabled = false
        }
    }
}