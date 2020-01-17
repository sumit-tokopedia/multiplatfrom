package sample

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.ios.Ios
import kotlinx.coroutines.*

actual fun getEngine(): HttpClientEngine = Ios.create()

actual open class BaseViewModel actual constructor() {
    private val viewModelJob = SupervisorJob()
    val viewModelScope: CoroutineScope = CoroutineScope(IosMainDispatcher + viewModelJob)

    actual val clientScope: CoroutineScope = viewModelScope

    protected actual open fun onCleared() {
        viewModelJob.cancelChildren()
    }

    object IosMainDispatcher : CoroutineDispatcher() {
        override fun dispatch(context: kotlin.coroutines.CoroutineContext, block: Runnable) {

        }


    }
}

