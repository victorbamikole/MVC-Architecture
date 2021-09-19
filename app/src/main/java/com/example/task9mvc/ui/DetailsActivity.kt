package com.example.task9mvc.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task9mvc.Controller
import com.example.task9mvc.R
import com.example.task9mvc.adapter.RecyclerViewAdapter2
import com.example.task9mvc.model.CommentsItem
import com.example.task9mvc.model.PostListsItem

class DetailsActivity : AppCompatActivity(), RecyclerViewAdapter2.onBtnClickListener {
    private lateinit var recyclerAdapter: RecyclerViewAdapter2
    lateinit var controller: Controller
    lateinit var recyclerView2: RecyclerView
    lateinit var replyBtn: Button
    lateinit var postBody: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        controller = Controller()


        val intent: Intent = getIntent()
        var postItems: PostListsItem? = intent.getParcelableExtra("postlists")
        Log.d("mainActivity", "onCreate: $postItems")
        var position = intent.getIntExtra("pos", 1)
//        var body = intent.getStringExtra("postlists")
        val id = position + 1
        postBody = findViewById(R.id.postBody)

        postBody.text  = postItems?.body


        recyclerView2 = findViewById(R.id.recyclerView2)
        recyclerView2.layoutManager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView2.addItemDecoration(decoration)
//        replyBtn = findViewById(R.id.reply)!!

//        replyBtn.setOnClickListener {
//            val i = Intent(this, CreateComment::class.java)
//            startActivity(i)
//        }

        initViewModel2(postItems?.id)

    }

    fun bindDetails(it: List<CommentsItem>) {

    }

    private fun initViewModel(id: Int?): Controller {

//        val viewModel = ViewModelProvider(this).get(DetailPostViewModel::class.java)
        controller.commentsLiveData.observe(this) {
            if (it != null) {
                bindDetails(it)
            } else {
                Toast.makeText(this, "error in getting data", Toast.LENGTH_SHORT).show()
            }
        }
        controller.callComments(id!!)
        return controller

    }

    private fun initViewModel2(id: Int?): Controller {
//        val viewModel = ViewModelProvider(this).get(DetailPostViewModel::class.java)
        controller.getRecyclerListObserver2().observe(this, {
            if (it != null) {
                bindDetails(it)
                recyclerAdapter = RecyclerViewAdapter2(this)
                recyclerAdapter.comment = (it as MutableList<CommentsItem>)
                recyclerAdapter.notifyDataSetChanged()
                recyclerView2.adapter = recyclerAdapter

            } else {
                Toast.makeText(this, "error in getting data", Toast.LENGTH_SHORT).show()
            }
        })
        controller.callComments(id!!)
        return controller
    }

    override fun onBtnClick(position: Int) {
        val intent: Intent = Intent(this, CreateComment::class.java)
//        intent.putExtra("postlists", objectData)
//        intent.putExtra("pos", position)
        startActivity(intent)
    }
}