package com.example.smsapp.data

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ChatEntry(
    val threadId: Long,
    val messages: List<SMSEntry>
) : Parcelable
