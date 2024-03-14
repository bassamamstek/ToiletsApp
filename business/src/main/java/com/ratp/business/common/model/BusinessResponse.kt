package com.ratp.business.common.model

/**
 * Business Response Model
 * Success Model with result
 * Failure Model with Error Type
 */
sealed class BusinessResponse<T>
class Success<T>(val result: T) : BusinessResponse<T>()
class Failure<T>(val error: AppError) : BusinessResponse<T>()