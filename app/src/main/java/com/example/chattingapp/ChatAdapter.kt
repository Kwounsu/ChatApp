package com.example.chattingapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    private val chat: ArrayList<ChatData> = arrayListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNickname: TextView = itemView.findViewById(R.id.tv_nickname)
        val tvMsg: TextView = itemView.findViewById(R.id.tv_msg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_chat, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = chat.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chat[position]
        holder.tvNickname.text = chat.nickname
        holder.tvMsg.text = chat.msg

        // Align my messages to right
        if(chat.nickname == android.os.Build.MODEL){
            holder.tvMsg.textAlignment = View.TEXT_ALIGNMENT_TEXT_END;
            holder.tvNickname.textAlignment = View.TEXT_ALIGNMENT_TEXT_END;
        }
        else{
            holder.tvMsg.textAlignment = View.TEXT_ALIGNMENT_TEXT_START;
            holder.tvNickname.textAlignment = View.TEXT_ALIGNMENT_TEXT_START;
        }
    }

    fun addChat(chat: ChatData) {
        Log.d("ChatAdapter->addChat", "Enter")
        this.chat.add(chat)
        notifyItemInserted(itemCount - 1) // update
    }
}
