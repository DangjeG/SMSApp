package com.example.smsapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SMSEntry(
    val address: String,
    val body: String,
    val date: Long,
    val type: Int,
) : Parcelable
