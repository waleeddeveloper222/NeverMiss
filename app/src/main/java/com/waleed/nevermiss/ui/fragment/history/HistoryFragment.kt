package com.waleed.nevermiss.ui.fragment.history

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

import com.waleed.nevermiss.R
import com.waleed.nevermiss.model.MyMessage

class HistoryFragment : Fragment() {

    lateinit var historyAdapter: HistoryAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.history_fragment, container, false)

        var history_message_recycler =
            view.findViewById<RecyclerView>(R.id.history_message_recycler)


        layoutManager = LinearLayoutManager(activity)
        historyAdapter = HistoryAdapter(this)
        history_message_recycler.layoutManager = layoutManager
        history_message_recycler.adapter = historyAdapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        historyViewModel =
            ViewModelProviders.of(this, MessageViewModelFactory(this))
                .get(HistoryViewModel::class.java!!)
    }


    override fun onResume() {
        super.onResume()
        updateRecyclerView()
    }

    fun updateRecyclerView() {

        historyViewModel.getUserMessage().observe(this,
            Observer<List<MyMessage>> { myMessages ->
                historyAdapter.setMessageList(myMessages)
            })
    }

    fun deleteGroup(myMessage: MyMessage) {
        historyViewModel.deleteMessage(myMessage)

    }

    inner class MessageViewModelFactory(private val historyFragment: HistoryFragment) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HistoryViewModel(historyFragment) as T
        }
    }

}
