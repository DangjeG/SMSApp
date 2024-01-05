package com.example.smsapp.present.sms_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.smsapp.R
import com.example.smsapp.data.ChatEntry
import com.example.smsapp.databinding.FragmentChatListBinding
import com.example.smsapp.databinding.FragmentSmsListBinding
import com.example.smsapp.present.chat_list.ChatEntriesAdapter
import com.example.smsapp.present.chat_list.ChatListFragmentDirections


class SMSListFragment : Fragment(R.layout.fragment_sms_list) {

    private val binding: FragmentSmsListBinding by viewBinding()
    private val adapter = SMSEntriesAdapter()
    private var chatEntry: ChatEntry? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: SMSListFragmentArgs by navArgs()
        chatEntry = args.chatEntry
        adapter.submitList(chatEntry!!.messages.reversed())

        initializeRecycler()
        initializeUI()
    }
    private fun initializeRecycler() = with(binding.smsList) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = this@SMSListFragment.adapter
    }

    private fun initializeUI() = with(binding){
        address.text = chatEntry?.messages?.first()?.address
        this.backButton.setOnClickListener {
            obBackClick()
        }

    }
    private fun obBackClick(){
        val action = SMSListFragmentDirections.actionSMSListFragmentToChatListFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }
}
