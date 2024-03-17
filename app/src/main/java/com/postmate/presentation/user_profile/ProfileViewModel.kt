package com.postmate.presentation.user_profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.postmate.domain.model.User
import com.postmate.domain.use_cases.FetchPostByUserUseCase
import com.postmate.presentation.common.util.Resource
import com.postmate.presentation.navigation.ArgumentTypeEnum
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
        private val savedStateHandle: SavedStateHandle,
        private val fetchPostsByUserUseCase: FetchPostByUserUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(ProfileState())
        val state = _state.asStateFlow()

        init {
            loadUser(
                savedStateHandle.get<Int>(ArgumentTypeEnum.ITEM_ID.name),
                savedStateHandle.get<String>(ArgumentTypeEnum.NAME.name),
                savedStateHandle.get<String>(ArgumentTypeEnum.EMAIL.name),
            )
        }

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
                    loadUser(event.userId, event.name, event.email)
                }
            }
        }

        private fun loadUser(
            userId: Int?,
            name: String?,
            email: String?,
        ) {
            userId?.let {
                _state.update {
                    it.copy(
                        selectedUser = User(userId, name ?: "", email ?: "", "", ""),
                    )
                }
                fetchPosts()
            }
        }
    }
