package com.berglund.qapital.mapper

import com.berglund.qapital.entities.UserEntity
import com.berglund.qapital.models.UserModel

class UserMapper : Mapper<UserEntity, UserModel> {

    override fun map(model: UserEntity): UserModel =
        UserModel(
            model.userId,
            model.displayName,
            model.avatarUrl)
}