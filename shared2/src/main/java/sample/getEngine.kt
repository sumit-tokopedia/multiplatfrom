package sample

import androidx.lifecycle.ViewModel
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.isActive
import kotlin.coroutines.CoroutineContext

actual fun getEngine(): HttpClientEngine  = Android.create()

actual open class BaseViewModel actual constructor() : CoroutineScope, ViewModel(){
    actual val clientScope: CoroutineScope = this
    protected val masterJob = SupervisorJob()
    private val baseDispatcher = Dispatchers.Main

    override val coroutineContext: CoroutineContext
        get() = baseDispatcher + masterJob
    actual override fun onCleared() {
        super.onCleared()
        clear()
    }

    private fun clear(){
        if (isActive && !masterJob.isCancelled){
            masterJob.children.map { it.cancel() }
        }
    }
}