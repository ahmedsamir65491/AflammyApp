package com.example.aflammy.ui.profile.watchhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aflammy.domain.mappers.WatchHistoryMapper
import com.example.aflammy.domain.usecases.GetWatchHistoryUseCase
import com.example.aflammy.utilities.Constants
import com.example.aflammy.utilities.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WatchHistoryViewModel @Inject constructor(
    private val getWatchHistoryUseCase: GetWatchHistoryUseCase,
    private val watchHistoryMapper: WatchHistoryMapper
) : ViewModel(), WatchHistoryInteractionListener {

    private val _uiState = MutableStateFlow(WatchHistoryUiState())
    val uiState = _uiState.asStateFlow()

    private val _watchHistoryUIEvent: MutableStateFlow<Event<WatchHistoryUIEvent?>> =
        MutableStateFlow(Event(null))
    val watchHistoryUIEvent = _watchHistoryUIEvent.asStateFlow()

    init {
        getWatchHistoryData()
    }

    private fun getWatchHistoryData() {
        viewModelScope.launch {
            try {
                getWatchHistoryUseCase().collect { list ->
                    _uiState.update { watchHistoryUiState ->
                        watchHistoryUiState.copy(allMedia = list.map { watchHistoryMapper.map(it) })
                    }
                }
            } catch (t: Throwable) {
                _uiState.update { it.copy(error = listOf(Error(400, t.message.toString()))) }
            }

        }
    }

    override fun onClickMovie(item: MediaHistoryUiState) {
        if (item.mediaType.equals(Constants.MOVIE, true)) {
            _watchHistoryUIEvent.update { Event(WatchHistoryUIEvent.MovieEvent(item.id)) }
        } else {
            _watchHistoryUIEvent.update { Event(WatchHistoryUIEvent.TVShowEvent(item.id)) }
        }
    }

}