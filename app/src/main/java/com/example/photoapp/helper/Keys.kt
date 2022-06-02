package com.example.photoapp.helper

object Keys {
    init {
        System.loadLibrary("photoapp")
    }

    external fun getPixabayApiKey(): String
}