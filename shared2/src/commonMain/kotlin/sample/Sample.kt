package sample

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.native.concurrent.ThreadLocal

expect fun getEngine() : HttpClientEngine


class ItemRepository {
    private val client = HttpClient(getEngine())

    suspend fun getData() : String {
        return client.get<String>("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22")
    }

}

expect open class BaseViewModel() {
    val clientScope: CoroutineScope
    protected open fun onCleared()
}


class ViewItemsViewModel(
    private val itemsRepository: ItemRepository
) : BaseViewModel() {


    fun getStringData(block : (String) -> Unit){
        clientScope.launch {
            block( itemsRepository.getData())
        }
    }

    @ThreadLocal
    companion object {
        fun create() = ViewItemsViewModel(ItemRepository())
    }
}

