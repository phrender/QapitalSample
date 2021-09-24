package com.berglund.qapital.extensions

import com.berglund.qapital.mapper.Mapper
import com.berglund.qapital.util.Result
import java.io.IOException
import java.lang.RuntimeException
import retrofit2.Call

inline fun
<reified Success : Any, MappedResult : Any> apiCall(
	mapper: Mapper<Success, MappedResult>,
	block: () -> Call<Success>
): Result<MappedResult> =
	try {
		val data = block().execute()
		if (data.isSuccessful) {
			data.body()?.let { Result.Success(mapper.map(it)) }
				?: Result.Error("Empty response body")
		} else {
			Result.Error(data.errorBody()?.string() ?: "Obtained ${data.code()} from backend", null)
		}
	} catch (exception: IOException) {
		Result.Error(exception.localizedMessage ?: "IOException", exception)
	} catch (exception: RuntimeException) {
		Result.Error("Runtime exception, check mapping.", exception)
	}
