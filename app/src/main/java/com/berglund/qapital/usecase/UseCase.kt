package com.berglund.qapital.usecase

interface UseCase

interface ArgUseCase<ReturnType: Any, Argument: Any> : UseCase {
    fun perform(arg: Argument): ReturnType
}

interface NoArgUseCase<ReturnType: Any> : UseCase {
    fun perform(): ReturnType
}