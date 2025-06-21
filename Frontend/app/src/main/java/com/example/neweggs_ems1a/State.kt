package com.example.neweggs_ems1a

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class State(
    val name: String,
    val capital: String,
    val location: String,
    val tradition: String,
    val food: String,
    val culturalNote: String,
    val imageResId: Int
) : Parcelable
