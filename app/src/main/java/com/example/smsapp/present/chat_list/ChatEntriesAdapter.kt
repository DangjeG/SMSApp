package com.example.smsapp.present.chat_list

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smsapp.data.ChatEntry
import com.example.smsapp.databinding.ChatItemBinding
import java.util.Date

class ChatEntriesAdapter(
    private val onItemClick: (ChatEntry) -> Unit,
): RecyclerView.Adapter<ChatEntriesAdapter.ChatEntryViewHolder>() {

    private val list = mutableListOf<ChatEntry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatEntryViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = ChatItemBinding.inflate(layoutInflater, parent, false)
        return ChatEntryViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ChatEntryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun submitList(list: List<ChatEntry>) = with(this.list){
        clear()
        addAll(list)
        notifyDataSetChanged()
    }

    inner class ChatEntryViewHolder(
        private val binding: ChatItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: ChatEntry) = with(binding) {
            val firstMessage = entry.messages.first()
            val date = Date(firstMessage.date)
            val formatter= SimpleDateFormat("HH:mm")
            sender.text = firstMessage.address
            preview.text = firstMessage.body
            sendTime.text = formatter.format(date)
            root.setOnClickListener{
                onItemClick(entry)
            }
        }
    }
}