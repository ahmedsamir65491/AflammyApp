package com.example.aflammy.domain.usecases.login

import com.example.aflammy.utilities.FormFieldState
import javax.inject.Inject

class ValidatePasswordFiledUseCase @Inject constructor(){
    operator fun invoke(passwordText: String) : FormFieldState {
        if(passwordText.length < 4) {
            return FormFieldState.InValid("Minimum 4 Character Password")
        }
        return FormFieldState.Valid
    }
}