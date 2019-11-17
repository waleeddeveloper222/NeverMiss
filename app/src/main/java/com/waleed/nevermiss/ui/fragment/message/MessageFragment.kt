package com.waleed.nevermiss.ui.fragment.message

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.waleed.nevermiss.R
import com.waleed.nevermiss.ui.sendMessage.SendMessageActivity
import kotlinx.android.synthetic.main.message_fragment.*

class MessageFragment : Fragment() {

    companion object {
        fun newInstance() = MessageFragment()
    }

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


        return view
    }


}
