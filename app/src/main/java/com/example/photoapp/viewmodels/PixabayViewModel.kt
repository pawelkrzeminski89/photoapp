package com.example.photoapp.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.photoapp.model.AppImage
import com.example.photoapp.remote.NetworkResult
import com.example.photoapp.remote.PixabayApiResponse
import com.example.photoapp.model.PixabaySearchArguments
import com.example.photoapp.usecases.GetPixabayImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UIActionPixabayViewModel< out T>(
    val data: T? = null,
    ):UIAction {

    class DecisionDialog< out T>(data: T): UIActionPixabayViewModel<T>(data)
    data class NoAction(val nothing: Nothing? = null): UIActionPixabayViewModel<Nothing>()

}

sealed class UIEffectPixabayViewModel:UIEffect {

    data class ShowToast(val message: String): UIEffectPixabayViewModel()

}

@HiltViewModel
class PixabayViewModel @Inject constructor(
    private val getPixabayImage: GetPixabayImage,
    application: Application
):BaseViewModel<UIActionPixabayViewModel<AppImage>, UIEffectPixabayViewModel>(application) {

    val pixabaySearchArguments = PixabaySearchArguments()

    private val pixabayMutableNetworkResultState = MutableStateFlow<NetworkResult<PixabayApiResponse>>(NetworkResult.EmptyData())
    val pixabayNetworkResultState = pixabayMutableNetworkResultState.asStateFlow()

    init {
        fetchPixabaySearchApi()
    }

    fun fetchPixabaySearchApi(){
        viewModelScope.launch(Dispatchers.IO) {
            getPixabayImage(pixabaySearchArguments).collect {
                if (it is NetworkResult.Error){
                    uiEffectChannel.send(UIEffectPixabayViewModel.ShowToast(it.message!!))
                }
                pixabayMutableNetworkResultState.emit(it)
            }
        }
    }

    fun questionAboutShowDetail(pixabayImage: AppImage){
        viewModelScope.launch {
            actionMutableState.emit(UIActionPixabayViewModel.DecisionDialog(pixabayImage))

        }
    }

    fun clearActionState() {
        viewModelScope.launch {
            actionMutableState.emit(UIActionPixabayViewModel.NoAction())
        }
    }

    override fun initialUIAction(): UIActionPixabayViewModel<AppImage> {
        return UIActionPixabayViewModel.NoAction()
    }


}