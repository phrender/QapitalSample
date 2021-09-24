package com.berglund.qapital.mapper

import com.berglund.qapital.entities.UserEntity
import com.berglund.qapital.models.UserModel

class UserMapper : Mapper<UserEntity, UserModel> {

	override fun map(model: UserEntity): UserModel = model.toModel()

	private fun UserEntity.toModel(): UserModel = UserModel(userId, displayName, avatarUrl)
}
