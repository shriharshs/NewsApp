package com.shriharsh.newsapp.utils

suspend fun <T : Any> handleNetworkCall(
    responseHandler: ResponseHandler = ResponseHandler(),
    apiCall: suspend () -> T
): Resource<T> {
    return try {
        responseHandler.handleSuccess(apiCall.invoke())
    } catch (exception: Exception) {
        responseHandler.handleException(exception)
    }
}