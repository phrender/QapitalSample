package com.berglund.qapital.mapper

interface Mapper<From: Any, To: Any> {

    fun map(model: From): To

    fun mapList(list: List<From>?): List<To> {
        return list?.map {
            map(it)
        } ?: emptyList()
    }
}