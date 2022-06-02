package com.example.photoapp.model

import androidx.lifecycle.MutableLiveData

class PixabaySearchArguments {

    var searchArgument: MutableLiveData<String> = MutableLiveData<String>("fruits")

    private fun getArgumentValue():String{
        return searchArgument.value?:""
    }

    fun preparePixabayApiQueryArgument() :String {
        return getArgumentValue()
    }


}