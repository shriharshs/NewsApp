package com.shriharsh.newsapp.utils

import retrofit2.HttpException
import java.net.SocketTimeoutException

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.Success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.Failure(
                getErrorMessage(e.code())
            )
            is SocketTimeoutException -> Resource.Failure(
                getErrorMessage(ErrorCodes.SocketTimeOut.code)
            )
            else -> Resource.Failure(
                getErrorMessage(Int.MAX_VALUE)
            )
        }
    }

    private fun getErrorMessage(code: Int): Exception {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> Exception("Timeout")
            401 -> Exception("Unauthorised")
            404 -> Exception("Not found")
            else -> Exception("Please check Internet Connection")
        }
    }

}