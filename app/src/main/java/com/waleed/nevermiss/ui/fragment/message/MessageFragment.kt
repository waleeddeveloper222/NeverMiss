package com.waleed.nevermiss.ui.fragment.message

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.waleed.nevermiss.R
import com.waleed.nevermiss.model.MyMessage
import com.waleed.nevermiss.ui.sendMessage.SendMessageActivity
import com.waleed.nevermiss.utils.Utils

class MessageFragment : Fragment() {

    lateinit var messageAdapter: MessageAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var messageViewModel: MessageViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MessageViewModel::class.java)
    }

    private lateinit var viewModel: MessageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.message_fragment, container, false)

        val message_fab = view.findViewById<FloatingActionButton>(R.id.message_fab)
        message_fab.setOnClickListener {
            startActivity(
                Intent(
                    activity,
                    SendMessageActivity::class.java
                )
            )
        }

        var pending_message_recycler =
            view.findViewById<RecyclerView>(R.id.pending_message_recycler)

        messageViewModel =
            ViewModelProviders.of(this, MessageViewModelFactory(this))
                .get(MessageViewModel::class.java)

        layoutManager = LinearLayoutManager(activity)
        messageAdapter = MessageAdapter(this)
        pending_message_recycler.layoutManager = layoutManager
        pending_message_recycler.adapter = messageAdapter

        return view
    }

    override fun onResume() {
        super.onResume()
        updateRecyclerView()
    }

    fun updateRecyclerView() {

        messageViewModel.getUserMessage().observe(this,
            Observer<List<MyMessage>> { myMessages ->
                messageAdapter.setMessageList(myMessages)
            })
    }

    fun deleteGroup(myMessage: MyMessage) {
        messageViewModel.deleteMessage(myMessage)

    }

    inner class MessageViewModelFactory(private val messageFragment: MessageFragment) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MessageViewModel(messageFragment) as T
        }
    }

}


