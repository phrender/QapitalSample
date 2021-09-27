package com.berglund.qapital.usecase

import com.berglund.qapital.base.BaseRobolectricTest
import com.berglund.qapital.fixtures.ActivitiesFixture
import com.berglund.qapital.fixtures.activitiesModel
import com.berglund.qapital.models.ActivitiesModel
import com.berglund.qapital.repository.ActivitiesRepository
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
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

@FlowPreview
@ExperimentalCoroutinesApi
class ActivitiesUseCaseTest : BaseRobolectricTest() {

	@MockK
	private lateinit var activitiesRepository: ActivitiesRepository

	private lateinit var activitiesChannel: MutableStateFlow<Result<ActivitiesModel>>

	private lateinit var useCase: ActivitiesUseCase

	private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00+00:00")
	private val requestFromDate: LocalDateTime = LocalDateTime.now(ZoneId.systemDefault()).minusWeeks(2)
	private val requestToDate: LocalDateTime = LocalDateTime.now(ZoneId.systemDefault())

	@Before
	fun setUp() = MockKAnnotations.init(this).also {
		useCase = ActivitiesUseCase(activitiesRepository)
	}

	@Test
	fun getActivityListSuccess() = runBlockingTest {

		val expected = ActivitiesFixture().activitiesModel()

		activitiesChannel = MutableStateFlow(Result.Success(expected))

		every { activitiesRepository.fetch(any()) } returns activitiesChannel.value

		val result = useCase.perform(ActivitiesUseCase.RetrievalParams(dateFormatter.format(requestFromDate), dateFormatter.format(requestToDate)))
			.flowOn(Dispatchers.IO)
			.first()

		when (result) {
			is Result.Success -> {
				val activities = result.value.activities
				Assert.assertEquals(expected.oldest, result.value.oldest)
				Assert.assertEquals(expected.activities.size, activities.size)
				Assert.assertEquals(expected.activities[0].userId, activities[0].userId)
				Assert.assertEquals(expected.activities[0].amount, activities[0].amount, 0.0)
				Assert.assertEquals(expected.activities[0].timestamp, activities[0].timestamp)
			}
			is Result.Error -> {
				TestCase.fail("Expected a successful response from useCase, not $result")
			}
		}
	}

	@Test
	fun getActivityListFailure() = runBlockingTest {
		val expected = Result.Error("Unknown host", UnknownHostException())

		activitiesChannel = MutableStateFlow(expected)

		every { activitiesRepository.fetch(any()) } returns activitiesChannel.value

		val result = useCase.perform(ActivitiesUseCase.RetrievalParams(dateFormatter.format(requestFromDate), dateFormatter.format(requestToDate)))
			.flowOn(Dispatchers.IO)
			.first()

		when (result) {
			is Result.Success -> {
				TestCase.fail("Expected a failure from useCase, not $result")
			}
			is Result.Error -> {
				Assert.assertEquals(expected, result)
			}
		}
	}
}
