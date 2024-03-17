package com.ratp.business

import com.ratp.business.home.repository.RepositoryFailure
import com.ratp.business.home.repository.RepositorySuccess

class RepositorySuccessTestModel<T>(override val response: T) : RepositorySuccess<T>
class RepositoryFailureTestModel<T> : RepositoryFailure<T>