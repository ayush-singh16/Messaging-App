package com.example.chatbox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
      val ITEM_RECEIVE = 1;
      val ITEM_SENT= 2;


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==1)
        { //inflate receive
            val view : View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return ReceiveViewHolder(view)
        }else{
            //inflate sent
            val view : View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder.javaClass ==  SentViewHolder::class.java){

            val viewHolder= holder as SentViewHolder
            viewHolder.sentMessage?.text = currentMessage.message
        }
        else{
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.receiveMessage?.text = currentMessage.message
        }

    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        return if(FirebaseAuth.getInstance().currentUser?.uid.equals((currentMessage.senderId))){
            ITEM_SENT
        } else{
            ITEM_RECEIVE
        }

    }


    override fun getItemCount(): Int {
        return messageList.size

    }

    class SentViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val sentMessage: TextView? = itemView.findViewById(R.id.txt_sent_message)


    }
    class ReceiveViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val receiveMessage: TextView? = itemView.findViewById(R.id.txt_receive_message)

    }


}