package com.example.m15_room


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn


class MainViewModel(val dictionaryDao: DictionaryDao) : ViewModel() {

    val allDictionarys = this.dictionaryDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    suspend fun onAdd(dictionary: Dictionary) {
        dictionaryDao.insert(dictionary)
    }

    suspend fun onUpdate(dictionary: Dictionary) {
        dictionaryDao.update(dictionary)
    }

    suspend fun onDelete() {
        dictionaryDao.delete()
    }

    fun onContains(word: String): List<Dictionary> {
        return dictionaryDao.contains(word)
    }

}
