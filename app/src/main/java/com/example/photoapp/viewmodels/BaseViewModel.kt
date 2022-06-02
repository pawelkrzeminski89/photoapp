package com.example.photoapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

interface UIAction
interface UIEffect

abstract class BaseViewModel<Action:UIAction, Effect:UIEffect>(application: Application): AndroidViewModel(application) {

    private val initialActionState : Action by lazy {
        initialUIAction()
    }

    protected val actionMutableState = MutableStateFlow(initialActionState)
    val actionState = actionMutableState.asStateFlow()

    protected val uiEffectChannel = Channel<Effect>(1)
    val uiEffectReceived = uiEffectChannel.receiveAsFlow()

    abstract fun initialUIAction():Action

}