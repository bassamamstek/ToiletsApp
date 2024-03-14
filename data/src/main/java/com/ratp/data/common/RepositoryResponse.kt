package com.ratp.data.common

import com.ratp.business.common.model.AppError
import com.ratp.business.home.repository.RepositoryFailure
import com.ratp.business.home.repository.RepositorySuccess

class RepositorySuccessImpl<T>(override val response: T) : RepositorySuccess<T>
class RepositoryFailureImpl<T>(val error: AppError) : RepositoryFailure<T>