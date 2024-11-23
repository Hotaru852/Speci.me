package com.example.specime.userrepository

import com.google.firebase.auth.FirebaseUser

data class UserState(
    val currentUser: FirebaseUser? = null,
    val email: String? = null,
    val displayName: String? = null,
    val photoUrl: String? = null,
)