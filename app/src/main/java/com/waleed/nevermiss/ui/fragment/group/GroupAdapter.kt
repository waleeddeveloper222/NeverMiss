package com.waleed.nevermiss.ui.fragment.group

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.waleed.nevermiss.R
import com.waleed.nevermiss.model.Groups
import com.waleed.nevermiss.ui.addGroup.AddGroupActivity


class GroupAdapter(internal var groupFragment: GroupFragment) :
    RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    internal var groupList: List<Groups>? = ArrayList<Groups>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.group_row, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {

        holder.textViewName!!.text = groupList!![position].groupName

        holder.imageViewDelete!!.setOnClickListener {

            showDialog(position)
        }
    }


    override fun getItemCount(): Int {
        return if (groupList != null) groupList!!.size else 0
    }


    fun setGroupList(groupList: List<Groups>) {
        this.groupList = groupList
        notifyDataSetChanged()
    }


    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textViewName: TextView? = itemView.findViewById(R.id.textViewName)
        var imageViewDelete: ImageView? = itemView.findViewById(R.id.imageView)

        init {
            itemView.setOnClickListener {
                //                val intent = Intent(groupFragment.context, AddGroupActivity::class.java)
//                val bundle = Bundle()
//                bundle.putParcelable("currentGroup", groupList!![adapterPosition])
//                intent.putExtras(bundle)
//                groupFragment!!.startActivity(intent)
                Log.d("currentGroup", "group members = $groupList")

            }
        }

    }


    fun showDialog(position: Int) {
        val builder = AlertDialog.Builder(groupFragment.context!!)
        builder.setMessage("Are You sure")
        builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {

                groupFragment.deleteGroup(groupList!![position])
                groupFragment.updateRecyclerView()
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