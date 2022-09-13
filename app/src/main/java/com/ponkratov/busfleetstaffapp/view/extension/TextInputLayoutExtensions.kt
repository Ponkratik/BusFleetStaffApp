package com.ponkratov.busfleetstaffapp.view.extension

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.validateEmptyString(currentText: String, errorMessage: String): Boolean {
    if (currentText.isBlank()) {
        this.error = errorMessage
        return false
    }

    return true
}