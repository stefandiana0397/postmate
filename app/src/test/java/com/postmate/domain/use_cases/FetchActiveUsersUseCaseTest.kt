package com.postmate.domain.use_cases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.postmate.domain.model.User
import com.postmate.domain.repository.IUserRepository
import com.postmate.presentation.common.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchActiveUsersUseCaseTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var userRepository: IUserRepository
    private lateinit var fetchActiveUsersUseCase: FetchActiveUsersUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userRepository = mockk()
        fetchActiveUsersUseCase = FetchActiveUsersUseCase(userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `execute returns active users`() =
        runTest(testDispatcher) {
            val users =
                listOf(
                    User(id = 1, name = "User 1", email = "user1@gmail.com", gender = "female", status = "active"),
                    User(id = 2, name = "User 2", email = "user1@gmail.com", gender = "female", status = "inactive"),
                    User(id = 3, name = "User 3", email = "user1@gmail.com", gender = "female", status = "active"),
                )

            coEvery { userRepository.fetchUsers() } returns flowOf(Resource.Success(users))

            val result = fetchActiveUsersUseCase.execute().toList()

            assertEquals(2, (result[0] as Resource.Success).data?.size)
            assertEquals("User 1", (result[0] as Resource.Success).data?.get(0)?.name)
            assertEquals("User 3", (result[0] as Resource.Success).data?.get(1)?.name)
        }

    @Test
    fun `execute returns loading state`() =
        runTest(testDispatcher) {
            coEvery { userRepository.fetchUsers() } returns flowOf(Resource.Loading(emptyList()))

            val result = fetchActiveUsersUseCase.execute().toList()

            assertTrue(result[0] is Resource.Loading)
        }

    @Test
    fun `execute returns error state`() =
        runTest(testDispatcher) {
            coEvery { userRepository.fetchUsers() } returns flowOf(Resource.Error("Error message", emptyList()))

            val result = fetchActiveUsersUseCase.execute().toList()

            assertTrue(result[0] is Resource.Error)
            assertEquals("Error message", (result[0] as Resource.Error).message)
        }
}
