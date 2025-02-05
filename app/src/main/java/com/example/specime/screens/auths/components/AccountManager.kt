package com.example.specime.screens.auths.components

import android.app.Activity
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import com.example.specime.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption

class AccountManager(
    private val activity: Activity
) {
    private val credentialManager = CredentialManager.create(activity)

    suspend fun signUp(email: String, password: String) {
        try {
            credentialManager.createCredential(
                context = activity,
                request = CreatePasswordRequest(
                    id = email,
                    password = password
                )
            )
        } catch (_: Exception) {
        }
    }

    suspend fun signIn(): PasswordCredential? {
        try {
            val credentialResponse = credentialManager.getCredential(
                context = activity,
                request = GetCredentialRequest(
                    credentialOptions = listOf(
                        GetPasswordOption(),
                    )
                )
            )

            return credentialResponse.credential as PasswordCredential
        } catch (_: Exception) {
            return null
        }
    }

    suspend fun googleSignin(): CustomCredential? {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(activity.getString(R.string.default_web_client_id))
            .build()

        try {
            val credentialResponse = credentialManager.getCredential(
                context = activity,
                request = GetCredentialRequest(
                    credentialOptions = listOf(googleIdOption)
                )
            )

            return credentialResponse.credential as? CustomCredential
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}