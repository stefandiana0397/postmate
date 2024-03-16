package com.postmate.presentation.user_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.postmate.domain.model.User
import com.postmate.domain.use_cases.FetchPostsByUserUseCase
import com.postmate.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(
        private val fetchPostsByUserUseCase: FetchPostsByUserUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(ProfileState())
        val state = _state.asStateFlow()

        private fun fetchPosts() {
            state.value.selectedUser?.let { selectedUser ->
                _state.update {
                    it.copy(
                        isLoading = true,
                        error = null,
                    )
                }
                viewModelScope.launch {
                    fetchPostsByUserUseCase.execute(selectedUser).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        posts = result.data ?: emptyList(),
                                        isLoading = false,
                                        error = null,
                                    )
                                }
                            }

                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        posts = result.data ?: emptyList(),
                                        isLoading = false,
                                        error = result.message,
                                    )
                                }
                            }

                            is Resource.Loading -> {
                                _state.update {
                                    it.copy(
                                        posts = result.data ?: emptyList(),
                                        isLoading = true,
                                        error = null,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        fun onEvent(event: ProfileEvent) {
            when (event) {
                is ProfileEvent.SwipeToRefresh -> {
                    fetchPosts()
                }
                is ProfileEvent.LoadUser -> {
                    loadUser(event.userId, event.name, event.email, event.displayPhoto)
                }
            }
        }

        private fun loadUser(
            userId: Int?,
            name: String?,
            email: String?,
            displayPhoto: Boolean?,
        ) {
            _state.update {
                it.copy(
                    selectedUser = User(userId ?: 0, name ?: "", email ?: "", "", ""),
                    displayPhoto = displayPhoto ?: true,
                )
            }
            fetchPosts()
        }
    }
