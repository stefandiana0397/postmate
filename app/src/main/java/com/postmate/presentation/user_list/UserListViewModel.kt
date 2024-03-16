package com.postmate.presentation.user_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.postmate.domain.use_cases.FetchActiveUsersUseCase
import com.postmate.presentation.common.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel
    @Inject
    constructor(
        private val fetchActiveUsersUseCase: FetchActiveUsersUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(UserState())
        val state = _state.asStateFlow()

        init {
            fetchUsers()
        }

        private fun fetchUsers() {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }
            viewModelScope.launch {
                fetchActiveUsersUseCase.execute().collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    users = result.data ?: emptyList(),
                                    isLoading = false,
                                    error = null,
                                )
                            }
                        }
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    users = result.data ?: emptyList(),
                                    isLoading = false,
                                    error = result.message,
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _state.update {
                                it.copy(
                                    users = result.data ?: emptyList(),
                                    isLoading = true,
                                    error = null,
                                )
                            }
                        }
                    }
                }
            }
        }

        fun onEvent(event: UserEvent) {
            when (event) {
                is UserEvent.SwipeToRefresh -> {
                    fetchUsers()
                }
                is UserEvent.SelectUser -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            selectedUser = event.data,
                        )
                    }
                }
            }
        }
    }
