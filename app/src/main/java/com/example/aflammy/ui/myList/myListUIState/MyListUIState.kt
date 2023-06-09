package com.example.aflammy.ui.myList.myListUIState

import com.example.aflammy.ui.category.uiState.CategoryErrorUIState

data class MyListUIState(
    val createdList: List<CreatedListUIState> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val error: List<CategoryErrorUIState> = emptyList()
)