/*
 * Copyright 2024 Jeliuc.com S.R.L. and Turso SDK contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package com.jeliuc.turso.sdk.resource

import com.jeliuc.turso.sdk.TursoClient
import com.jeliuc.turso.sdk.model.ListAuditLogsResponse
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

val TursoClient.auditLogs: AuditLogs
    get() = AuditLogs(this)

/**
 * Turso AuditLogs API Resource
 *
 * [See official documentation page](https://docs.turso.tech/api-reference/audit-logs)
 *
 * Example usage:
 *
 * ```kotlin
 * // client: TursoClient
 * val auditLogs = client.auditLogs.list("organizationName")
 * ```
 */
class AuditLogs(private val client: TursoClient) : ResponseHandler() {
    /**
     * Lists audit logs
     *
     * @see <a href="https://docs.turso.tech/api-reference/audit-logs/list">API Reference</a>
     */
    suspend fun list(organizationName: String) =
        this.client.httpClient.get(Path.logs(organizationName)) {
            contentType(ContentType.Application.Json)
        }.let { response ->
            handleResponse<ListAuditLogsResponse>(response)
        }

    internal object Path {
        fun logs(organization: String) = "/v1/organizations/$organization/audit-logs"
    }
}
