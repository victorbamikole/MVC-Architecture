package com.example.task9mvc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task9mvc.R
import com.example.task9mvc.model.PostListsItem

//This class creates the adapter for the recycler view
class RecyclerViewAdapter(private var mlistener: onItemClickListener, private var blistener: onNewBtnClickListener  ): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){

    //var items : ArrayList<PostListsItem>,
    var items = mutableListOf<PostListsItem>()

    // , listener: onItemClickListener
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val CommentNewBtn = view.findViewById<View>(R.id.newBtn)

        val postTitle = view.findViewById<TextView>(R.id.title)
        val postDetails = view.findViewById<TextView>(R.id.post)
        val layoutId = view.findViewById<View>(R.id.recycler_layout)

        //This function binds the recycler view to the recycler data
        fun bind(data: PostListsItem) {
            postTitle.text = data.title
            postDetails.text = data.body
        }

    }

    //In this function, We inflate the recycler view layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row,parent,false)
        return MyViewHolder(view)
    }

    //This function
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
        val userId = items[position].userId
        val postId = items[position].id
        val userBody = items[position].body
        val userTitle = items[position].title

        val objectData = PostListsItem(userBody,postId,userTitle,userId)

        holder.layoutId.setOnClickListener {
            mlistener.onItemClick(position,objectData)
        }

        holder.CommentNewBtn.setOnClickListener {
            blistener.onNewBtnClick(position,objectData)
        }
    }

    //This function returns the number of each items in the recyclerView
    override fun getItemCount(): Int {
        return items.size
    }
    fun addToListOfPost(post:PostListsItem){
        items.add(post)
        notifyDataSetChanged()
    }
}

interface onItemClickListener{
    fun onItemClick(position: Int, objectData:PostListsItem)
}

interface onNewBtnClickListener{
    fun onNewBtnClick(position: Int, objectData:PostListsItem)
}
