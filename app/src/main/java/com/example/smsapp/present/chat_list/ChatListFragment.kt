package com.example.smsapp.present.chat_list

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.smsapp.R
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.smsapp.data.ChatEntry
import com.example.smsapp.databinding.FragmentChatListBinding
import com.example.smsapp.requirePermission


class ChatListFragment : Fragment(R.layout.fragment_chat_list) {

    private val binding: FragmentChatListBinding by viewBinding()
    private val viewModel: ChatListViewModel by viewModels()
    private val adapter = ChatEntriesAdapter( ::onChatItemClick)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.chatMessageEntries.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        initializeRecycler()
        requireSmsPermission()
    }


    private fun requireSmsPermission() {
        requirePermission(
            permission = Manifest.permission.READ_SMS,
            successDelegate = {
                viewModel.loadChats(requireContext().contentResolver)
            },
            failureDelegate = {

            }
        )
    }

    private fun onChatItemClick(entry: ChatEntry) {
        val action = ChatListFragmentDirections.actionChatListFragmentToSMSListFragment(entry)
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun initializeRecycler() = with(binding.chatList) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = this@ChatListFragment.adapter
        addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
    }
}