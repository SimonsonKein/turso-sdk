/*
 * Copyright 2024 Jeliuc.com S.R.L. and Turso SDK contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package com.jeliuc.turso.sdk.resource

import com.jeliuc.turso.sdk.model.ApiError
import com.jeliuc.turso.sdk.model.UnexpectedResultError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.ContentConvertException
import kotlinx.serialization.SerializationException

abstract class ResponseHandler {
    /**
     * Handles the response and returns the body T
     *
     * @throws ApiError if the response is an error
     * @throws UnexpectedResultError if the response is not expected
     */
    internal suspend inline fun <reified T> handleResponse(response: HttpResponse): T =
        try {
            response.body<T>()
        } catch (e: SerializationException) {
            response.body<ApiError>().let { throw ApiError(it.message) }
        } catch (e: ContentConvertException) {
            response.body<ApiError>().let { throw ApiError(it.message) }
        } catch (e: Throwable) {
            throw UnexpectedResultError(e.message)
        }
}
