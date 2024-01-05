package com.example.smsapp.present.chat_list

import android.content.ContentResolver
import android.provider.Telephony
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smsapp.data.ChatEntry
import com.example.smsapp.data.SMSEntry

class ChatListViewModel : ViewModel() {

    private val _chatMessageEntries = MutableLiveData<List<ChatEntry>>()
    val chatMessageEntries: LiveData<List<ChatEntry>>
        get() = _chatMessageEntries

    fun loadChats(contentResolver: ContentResolver) {
        val cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val result = AddressAndMessageList(mutableListOf())

        if (cursor?.moveToFirst() == true) {
            do {
                val address = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                val body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
                val date = cursor.getLong(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
                val type = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE))
                val threadId = cursor.getLong(cursor.getColumnIndexOrThrow(Telephony.Sms.THREAD_ID))

                val sms = SMSEntry(address, body, date, type)
                result.list.add(threadId to sms)

            } while (cursor.moveToNext())
        }
        cursor?.close()

        _chatMessageEntries.postValue(result.toSmsChatEntriesList())
    }

    @JvmInline
    value class AddressAndMessageList(
         val list: MutableList<Pair<Long, SMSEntry>>
    ) {
        fun toSmsChatEntriesList(): List<ChatEntry> =
            list
                .groupBy { it.first }
                .map { it ->
                    val messages = it.value.map { it.second }
                    ChatEntry(it.key, messages)
                }
        }
}
