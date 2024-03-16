package com.postmate.presentation.user_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.postmate.domain.model.User
import com.postmate.domain.use_cases.FetchActiveUsersUseCase
import com.postmate.presentation.common.util.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var fetchActiveUsersUseCase: FetchActiveUsersUseCase
    private lateinit var userListViewModel: UserListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fetchActiveUsersUseCase = mockk(relaxed = true)
        userListViewModel = UserListViewModel(fetchActiveUsersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchUsers calls execute on FetchActiveUsersUseCase`() =
        runTest(testDispatcher) {
            val users =
                listOf(
                    User(id = 1, name = "User 1", email = "user1@gmail.com", gender = "female", status = "active"),
                    User(id = 3, name = "User 3", email = "user1@gmail.com", gender = "female", status = "active"),
                )
            coEvery { fetchActiveUsersUseCase.execute() } returns flowOf(Resource.Success(users))

            userListViewModel.onEvent(UserEvent.SwipeToRefresh)

            coVerify { fetchActiveUsersUseCase.execute() }
            val currentState = userListViewModel.state.value
            assert(currentState.users.isNotEmpty())
            assertEquals(2, currentState.users.size)
            assertNull(currentState.error)
            assertFalse(currentState.isLoading)
        }

    @Test
    fun `fetchUsers updates state with error when FetchActiveUsersUseCase returns error`() =
        runTest(testDispatcher) {
            // Arrange
            val errorMessage = "Network error"
            coEvery { fetchActiveUsersUseCase.execute() } returns flowOf(Resource.Error(errorMessage, null))

            // Act
            userListViewModel.onEvent(UserEvent.SwipeToRefresh)

            // Assert
            coVerify { fetchActiveUsersUseCase.execute() }
            val currentState = userListViewModel.state.value
            assertEquals(errorMessage, currentState.error)
            assertFalse(currentState.isLoading)
        }

    @Test
    fun `fetchUsers updates state with isLoading when FetchActiveUsersUseCase returns loading`() =
        runTest(testDispatcher) {
            // Arrange
            coEvery { fetchActiveUsersUseCase.execute() } returns flowOf(Resource.Loading(null))

            // Act
            userListViewModel.onEvent(UserEvent.SwipeToRefresh)

            // Assert
            coVerify { fetchActiveUsersUseCase.execute() }
            val currentState = userListViewModel.state.value
            assertTrue(currentState.isLoading)
            assertNull(currentState.error)
        }

    @Test
    fun `onEvent SelectUser updates state with selected user`() =
        runTest(testDispatcher) {
            // Arrange
            val selectedUser = User(id = 1, name = "User 1", email = "user1@gmail.com", gender = "female", status = "active")

            // Act
            userListViewModel.onEvent(UserEvent.SelectUser(selectedUser))

            // Assert
            val currentState = userListViewModel.state.value
            assertEquals(selectedUser, currentState.selectedUser)
            assertFalse(currentState.isLoading)
            assertNull(currentState.error)
        }
}
