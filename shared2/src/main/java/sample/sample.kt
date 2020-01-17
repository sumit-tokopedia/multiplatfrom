package sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.CoroutineScope


actual fun getEngine(): HttpClientEngine  = Android.create()

actual open class BaseViewModel actual constructor() :  ViewModel(){
    actual val clientScope: CoroutineScope = viewModelScope
    actual override fun onCleared() {
        super.onCleared()
    }
}