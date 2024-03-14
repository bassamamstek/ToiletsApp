package com.ratp.business.home.repository

sealed interface RepositoryResponse<T>
interface RepositorySuccess<T> : RepositoryResponse<T> {
    val response: T
}

interface RepositoryFailure<T> : RepositoryResponse<T>