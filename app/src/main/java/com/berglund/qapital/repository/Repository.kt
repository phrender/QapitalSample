package com.berglund.qapital.repository

import com.berglund.qapital.util.Result

abstract class Repository<Model : Any, in Params : Repository.RepositoryParams> {

	abstract fun fetch(params: Params): Result<Model>

	open class RepositoryParams
}
