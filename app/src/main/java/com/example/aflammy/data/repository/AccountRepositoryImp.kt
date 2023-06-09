package com.example.aflammy.data.repository

import com.example.aflammy.data.DataClassParser
import com.example.aflammy.data.local.AppConfiguration
import com.example.aflammy.data.remote.response.account.AccountDto
import com.example.aflammy.data.remote.response.login.ErrorResponse
import com.example.aflammy.data.remote.service.MovieService
import javax.inject.Inject


class AccountRepositoryImp @Inject constructor(
    private val service: MovieService,
    private val appConfiguration: AppConfiguration,
    private val dataClassParser: DataClassParser,
) : AccountRepository, BaseRepository() {

    override fun getSessionId(): String? {
        return appConfiguration.getSessionId()
    }


    override suspend fun loginWithUserNameANdPassword(
        userName: String,
        password: String
    ): Boolean {
        return try {
            val token = getRequestToken()
            val body = mapOf<String, Any>(
                "username" to userName,
                "password" to password,
                "request_token" to token,
            ).toMap()

            val validateRequestTokenWithLogin = service.validateRequestTokenWithLogin(body)
            if (validateRequestTokenWithLogin.isSuccessful) {
                validateRequestTokenWithLogin.body()?.requestToken?.let { createSession(it) }
                true
            } else {
                val errorResponse = dataClassParser.parseFromJson(
                    validateRequestTokenWithLogin.errorBody()?.string(), ErrorResponse::class.java
                )
                throw Throwable(errorResponse.statusMessage)
            }
        } catch (e: Exception) {
            throw Throwable(e)
        }
    }

    override suspend fun logout() {
        appConfiguration.saveSessionId("")
    }

    override suspend fun getAccountDetails(): AccountDto? {
        return service.getAccountDetails().body()
    }

    private suspend fun getRequestToken(): String {
        val tokenResponse = service.getRequestToken()
        return tokenResponse.body()?.requestToken.toString()
    }


    private suspend fun createSession(requestToken: String) {
        val sessionResponse = service.createSession(requestToken).body()
        if (sessionResponse?.success == true) {
            saveSessionId(sessionResponse.sessionId.toString())
        }
    }

    private suspend fun saveSessionId(sessionId: String) {
        appConfiguration.saveSessionId(sessionId)
    }

}