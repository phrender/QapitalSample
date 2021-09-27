package com.berglund.qapital.usecase

import com.berglund.qapital.base.BaseRobolectricTest
import com.berglund.qapital.fixtures.UserFixture
import com.berglund.qapital.fixtures.userModel
import com.berglund.qapital.models.UserModel
import com.berglund.qapital.repository.UserRepository
import com.berglund.qapital.util.Result
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import java.net.UnknownHostException
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@FlowPreview
@ExperimentalCoroutinesApi
class UserUseCaseTest : BaseRobolectricTest() {

	@MockK
	private lateinit var userRepository: UserRepository

	private lateinit var userChannel: MutableStateFlow<Result<UserModel>>

	private lateinit var useCase: UserUseCase

	@Before
	fun setUp() = MockKAnnotations.init(this).also {
		useCase = UserUseCase(userRepository)
	}

	@Test
	fun testGetUserSuccess() = runBlockingTest {
		val expected = UserFixture().userModel()

		userChannel = MutableStateFlow(Result.Success(expected))

		every { userRepository.fetch(any()) } returns userChannel.value

		val result = useCase.perform(UserUseCase.RetrievalParams(1))
			.flowOn(Dispatchers.IO)
			.first()

		when (result) {
			is Result.Success -> {
				Assert.assertEquals(result.value.userId, 1)
				Assert.assertEquals(result.value.displayName, "Test User")
				Assert.assertEquals(result.value.avatarUrl, "http://www.google.se")
			}
			is Result.Error -> {
				TestCase.fail("Expecting a successful response from useCase, not $result")
			}
		}
	}

	@Test
	fun testGetUserFailure() = runBlockingTest {

		val expected = Result.Error("Unknown host", UnknownHostException())

		userChannel = MutableStateFlow(expected)

		every { userRepository.fetch(any()) } returns userChannel.value

		val result = useCase.perform(UserUseCase.RetrievalParams(1))
			.flowOn(Dispatchers.IO)
			.first()

		when (result) {
			is Result.Success -> {
				TestCase.fail("Expected a failure")
			}
			is Result.Error -> {
				Assert.assertEquals(expected, result)
			}
		}
	}
}
