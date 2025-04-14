package com.example.specime.firebase

import android.util.Log
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase

fun sendCustomEmail(
    recipient: String,
    subject: String,
    text: String,
    html: String? = null
) {
    val data = hashMapOf(
        "recipient" to recipient,
        "subject" to subject,
        "text" to text,
        "html" to html.orEmpty()
    )

    Firebase.functions
        .getHttpsCallable("sendCustomEmail")
        .call(data)
        .addOnSuccessListener { result ->
            Log.d("Email", "Email sent: ${result.data}")
        }
        .addOnFailureListener { e ->
            // Handle error
            Log.e("Email", "Failed to send email", e)
        }
}
