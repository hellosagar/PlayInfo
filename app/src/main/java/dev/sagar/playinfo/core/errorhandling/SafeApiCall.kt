package dev.sagar.playinfo.core.errorhandling

import dev.sagar.playinfo.core.utils.Constants
import dev.sagar.playinfo.domain.Result
import java.io.IOException
import javax.inject.Inject

class SafeApiCall @Inject constructor(
    private val internetChecker: InternetChecker,
) {

    @Suppress("TooGenericExceptionCaught")
    suspend operator fun <T : Any> invoke(
        apiCall: suspend () -> T,
    ): Result<T> {
        return try {
            if (!internetChecker.hasInternetConnection()) {
                return Result.Error(
                    error = IOException(Constants.NETWORK_ERROR)
                )
            }
            val response = apiCall.invoke()
            Result.Success(response)
        } catch (exception: Exception) {
            Result.Error(error = exception)
        }
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun <T : Any> invokeWithHandler(
        apiCall: suspend () -> Result<T>,
    ): Result<T> {
        return try {
            if (!internetChecker.hasInternetConnection()) {
                return Result.Error(error = IOException(Constants.NETWORK_ERROR))
            }
            apiCall.invoke()
        } catch (e: Exception) {
            Result.Error(Exception(Constants.NETWORK_ERROR, e))
        }
    }
}
