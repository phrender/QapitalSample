package com.berglund.qapital.util

import java.lang.Exception
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

sealed class Result<out T : Any> {
	data class Success<out T : Any>(val value: T) : Result<T>()
	data class Error(val message: String, val cause: Exception? = null) : Result<Nothing>()
}

infix fun <T : Any, U : Any> Result<T>.into(f: (T) -> Result<U>) =
	when (this) {
		is Result.Success -> f(value)
		is Result.Error -> copy()
	}

suspend infix fun <T : Any, U : Any> Result<T>.suspendInto(f: suspend (T) -> Result<U>) =
	when (this) {
		is Result.Success -> f(value)
		is Result.Error -> copy()
	}

infix fun <T : Any, U : Any> Result<T>.intoFlow(f: (T) -> Flow<Result<U>>) =
	when (this) {
		is Result.Success -> f(value)
		is Result.Error -> flowOf(copy())
	}

fun <T : Any> Result<T>.toShortString() =
	when (this) {
		is Result.Success -> {
			"Result.Success(type: ${value.javaClass.simpleName})"
		}
		is Result.Error -> {
			"Result.Error(message: $message, cause ${cause?.message})"
		}
	}
