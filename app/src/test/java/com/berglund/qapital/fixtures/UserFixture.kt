package com.berglund.qapital.fixtures

import com.berglund.qapital.models.UserModel

class UserFixture

fun UserFixture.userModel(
	userId: Int = 1,
	displayName: String = "Test User",
	avatarUrl: String = "http://www.google.se"
) = UserModel(
	userId,
	displayName,
	avatarUrl
)
