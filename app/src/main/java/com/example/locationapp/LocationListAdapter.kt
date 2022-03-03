package com.example.locationapp

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LocationListAdapter(private val context: Context,private val mList: ArrayList<LocationModel>) : RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // sets the text to the textview from our itemHolder class
        holder.tvName.text = mList[position].locatinname

        holder.btnEdit.setOnClickListener {
            showDialog(context, position)
        }

        holder.btnDelete.setOnClickListener { removeAt(position) }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvName: TextView = itemView.findViewById(R.id.list_tvLocation)
        val btnEdit: Button = itemView.findViewById(R.id.list_btnEdit)
        val btnDelete: Button = itemView.findViewById(R.id.list_btnDelete)
    }

    private fun showDialog(context: Context, position: Int) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_addedit_location)

        val body = dialog.findViewById(R.id.list_etName) as EditText

        val yesBtn = dialog.findViewById(R.id.list_btnDone) as Button

        yesBtn.setOnClickListener {

            mList[position].locatinname = body.text.toString()
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, mList.size)

            dialog.dismiss()
        }

        dialog.show()

    }

    fun removeAt(position: Int) {
        mList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mList.size)
    }
}