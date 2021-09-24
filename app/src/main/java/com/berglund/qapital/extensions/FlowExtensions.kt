package com.berglund.qapital.extensions

import com.berglund.qapital.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

@ExperimentalCoroutinesApi
fun <First: Any, Second: Any, Combined: Any> combineResults(
    flow1: Flow<Result<First>>,
    flow2: Flow<Result<Second>>,
    transformFlow: (result1: First, result2: Second) -> Result<Combined>
): Flow<Result<Combined>> =
    flow1.combine(flow2) { resultFlow1, resultFlow2 ->
        when {
            resultFlow1 is Result.Error -> {
                resultFlow1.copy()
            }
            resultFlow2 is Result.Error -> {
                resultFlow2.copy()
            }
            resultFlow1 is Result.Success && resultFlow2 is Result.Success -> {
                transformFlow(resultFlow1.value, resultFlow2.value)
            }
            else -> {
                Result.Error("Failed to merge flows")
            }
        }
    }