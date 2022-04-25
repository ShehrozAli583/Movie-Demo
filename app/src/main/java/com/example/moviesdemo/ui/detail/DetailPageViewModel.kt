package com.example.moviesdemo.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesdemo.models.DetailPageModel
import com.example.moviesdemo.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class DetailPageViewModel @Inject constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {

    val mDetailPageResponse = MutableLiveData<DetailPageModel>()
    var loadingError = MutableLiveData<String?>()
    var loading = MutableLiveData<Boolean>()
    var job: Job? = null
    var exceptionalHandling = CoroutineExceptionHandler { _, throwable ->
        onError("Exceptional Error: ${throwable.localizedMessage}")
    }

    private fun onError(message: String) {
        try {
            loading.postValue(true)
            if (message.isNotEmpty()) {
                loadingError.postValue(message)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun callDetailPageData(keyword: Int) {
        job = CoroutineScope(Dispatchers.IO + exceptionalHandling).launch {
            val response = mainRepository.getDetailPageData(keyword)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        mDetailPageResponse.value = response.body()
                        loadingError.value = null
                        loading.value = false
                    } else {
                        onError("UserLoadError : ${response.message()} ")
                        loading.value = true
                    }
                } catch (e: SocketTimeoutException) {
                    onError("UserLoadError : timeout ")
                    loading.value = true
                }
            }
        }
    }
}