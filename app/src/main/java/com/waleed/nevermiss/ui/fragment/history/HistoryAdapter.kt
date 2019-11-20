package com.waleed.nevermiss.ui.fragment.history

import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.waleed.nevermiss.R
import com.waleed.nevermiss.model.MyMessage


class HistoryAdapter(var historyFragment: HistoryFragment) :
    RecyclerView.Adapter<HistoryAdapter.MessageViewHolder>() {

    internal var messageList: List<MyMessage>? = ArrayList<MyMessage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.group_row, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {

        holder.textViewName!!.text = messageList!![position].smsMsg

        holder.imageViewDelete!!.setOnClickListener {

            showDialog(position)
        }
    }


    override fun getItemCount(): Int {
        return if (messageList != null) messageList!!.size else 0
    }


    fun setMessageList(messageList: List<MyMessage>) {
        this.messageList = messageList
        notifyDataSetChanged()
    }


    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textViewName: TextView? = itemView.findViewById(R.id.textViewName)
        var imageViewDelete: ImageView? = itemView.findViewById(R.id.imageView)

        init {
            itemView.setOnClickListener {
                //                val intent = Intent(groupFragment.context, AddGroupActivity::class.java)
//                val bundle = Bundle()
//                bundle.putParcelable("currentGroup", groupList!![adapterPosition])
//                intent.putExtras(bundle)
//                groupFragment!!.startActivity(intent)
                Log.d("currentGroup", "group members = $messageList")

            }
        }

    }


    fun showDialog(position: Int) {
        val builder = AlertDialog.Builder(historyFragment.context!!)
        builder.setMessage("Are You sure")
        builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {

                historyFragment.deleteGroup(messageList!![position])
                historyFragment.updateRecyclerView()
            }
        })
        builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                dialog.dismiss()
            }
        })

        builder.show()
    }

}