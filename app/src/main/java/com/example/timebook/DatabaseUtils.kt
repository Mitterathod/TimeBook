package com.example.timebook

import com.google.firebase.firestore.FirebaseFirestore

fun getDatabaseReference(): FirebaseFirestore {
    return FirebaseFirestore.getInstance()
}
