package com.sample.app.smarttasks.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sample.app.smarttasks.domain.model.Task
import com.sample.app.smarttasks.domain.useCase.GetTaskStateUseCase
import com.sample.app.smarttasks.domain.useCase.UpdateTaskStateUseCase
import com.sample.app.smarttasks.presentation.detail.data.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getTaskStateUseCase: GetTaskStateUseCase,
    private val updateTaskStateUseCase: UpdateTaskStateUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var detailState by mutableStateOf(DetailState())
        private set

    private var id: String = "0"

    init {
        savedStateHandle.toRoute<Task>().let {
            id = it.id
        }
        viewModelScope.launch {
            getTaskStateUseCase.invoke(id).collect { task ->
                detailState = detailState.copy(
                    taskState = task?.isResolved,
                    taskComment = task?.comment ?: ""
                )
            }
        }
    }

    fun updateTaskState(id: String, state: Boolean, newComment: String?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                updateTaskStateUseCase.invoke(id, state, comment = newComment)
            }
        }
    }
}
