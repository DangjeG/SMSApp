package com.example.smsapp.present.sms_list

import android.icu.text.SimpleDateFormat
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.smsapp.data.SMSEntry
import com.example.smsapp.databinding.SmsItemBinding
import java.util.Date


class SMSEntriesAdapter: RecyclerView.Adapter<SMSEntriesAdapter.SMSEntryViewHolder>() {

    private val list = mutableListOf<SMSEntry>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSEntriesAdapter.SMSEntryViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = SmsItemBinding.inflate(layoutInflater, parent, false)
        return SMSEntryViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SMSEntriesAdapter.SMSEntryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun submitList(list: List<SMSEntry>) = with(this.list){
        clear()
        addAll(list)
        notifyDataSetChanged()
    }


    inner class SMSEntryViewHolder(
        private val binding: SmsItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: SMSEntry) = with(binding){
            val date = Date(entry.date)
            val formatter = SimpleDateFormat("HH:mm")
            body.text = entry.body
            time.text = formatter.format(date)

            val constraintSet = ConstraintSet()
            constraintSet.clone(container)

            if (entry.type == Telephony.Sms.MESSAGE_TYPE_INBOX) {
                constraintSet.setHorizontalBias(card.id, 0f)
            } else {
                constraintSet.setHorizontalBias(card.id, 1f)
            }
            constraintSet.applyTo(container)
        }

    }
}