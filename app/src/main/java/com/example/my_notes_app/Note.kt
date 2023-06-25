package com.example.my_notes_app

import android.icu.text.CaseMap.Title
import java.io.Serializable

data class Note (

    val description: String,
    val isSimpleNote: Boolean,
    val title: String,
        ) : Serializable

