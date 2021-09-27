package com.berglund.qapital.fixtures

import com.berglund.qapital.models.ActivitiesModel
import com.berglund.qapital.models.ActivityModel
import org.threeten.bp.LocalDateTime

class ActivitiesFixture

fun ActivitiesFixture.activitiesModel(
	oldest: LocalDateTime = LocalDateTime.now().minusWeeks(2),
	activities: List<ActivityModel> = activityList()
) = ActivitiesModel(
	oldest,
	activities
)

fun ActivitiesFixture.addActivity(
	message: String = "You spent monies",
	amount: Double = 15.0,
	userId: Int = 1,
	timestamp: LocalDateTime = LocalDateTime.now().minusWeeks(2)
) = ActivityModel(
	message,
	amount,
	userId,
	timestamp
)

fun ActivitiesFixture.activityList(
	vararg activities: ActivityModel = arrayOf(
		firstActivity(),
		secondActivity()
	)
) = activities.toList()

fun ActivitiesFixture.firstActivity(
	message: String = "Activity_1",
	amount: Double = 15.0,
	userId: Int = 1,
	timestamp: LocalDateTime = LocalDateTime.now().minusDays(12)
) = ActivityModel(
	message,
	amount,
	userId,
	timestamp
)

fun ActivitiesFixture.secondActivity(
	message: String = "Activity_2",
	amount: Double = 1.0,
	userId: Int = 3,
	timestamp: LocalDateTime = LocalDateTime.now().minusDays(7)
) = ActivityModel(
	message,
	amount,
	userId,
	timestamp
)
