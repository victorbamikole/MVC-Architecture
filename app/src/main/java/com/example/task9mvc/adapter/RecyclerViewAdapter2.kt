package com.example.task9mvc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task9mvc.R
import com.example.task9mvc.model.CommentsItem

class RecyclerViewAdapter2(private var clistener: onBtnClickListener ): RecyclerView.Adapter<RecyclerViewAdapter2.MyViewHolder>(){
    var comment = mutableListOf<CommentsItem>()
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
//        val CommentBtn = view.findViewById<View>(R.id.reply)

        val commentBody = view.findViewById<TextView>(R.id.commentText)
        val commentMail = view.findViewById<TextView>(R.id.mail)
        val name = view.findViewById<TextView>(R.id.name)
        //        val layoutId = view.findViewById<View>(R.id.recycler_layout)
        fun bind(data: CommentsItem) {
            commentBody.text = data.body
            commentMail.text = data.email
            name.text = data.name


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_comment,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(comment[position])

//        holder.CommentBtn.setOnClickListener {
//            clistener.onBtnClick(position)
//        }
    }

    override fun getItemCount(): Int {
        return comment.size
    }

    interface onBtnClickListener{
        fun onBtnClick(position: Int)
    }
}