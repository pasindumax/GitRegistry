package com.example.btest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pasindu.gitregistry.data.api.RetrofitService
import com.pasindu.gitregistry.data.model.Repo
import com.example.btest.data.paging.PagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchVM : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val searchResults: Flow<PagingData<Repo>> = _searchQuery
        .debounce(300)
        .filter { it.isNotBlank() }
        .flatMapLatest { query ->
            Pager(

                config = PagingConfig(

                    pageSize = 20,
                    enablePlaceholders = false,
                    initialLoadSize = 20
                ),

                pagingSourceFactory = {
                    PagingSource(
                        apiService = RetrofitService.api,
                        query = query

                    )
                }
            ).flow
        }
        .cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}
