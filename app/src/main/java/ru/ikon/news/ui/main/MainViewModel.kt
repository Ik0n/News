package ru.ikon.news.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.ikon.news.data.api.TestRepo
import ru.ikon.news.models.NewsResponse
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: TestRepo): ViewModel() {

    private val _all = MutableLiveData<NewsResponse>()
    val all: LiveData<NewsResponse>
        get() = _all

    init {
        getAll()
    }

    fun getAll() = viewModelScope.launch {
        repo.getAll().let {
            if (it.isSuccessful) {
                _all.postValue(it.body())
            } else {
                Log.d("checkData", "Failed to load articles: ${it.errorBody()}")
            }
        }
    }
}