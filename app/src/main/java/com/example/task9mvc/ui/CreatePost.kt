package com.example.task9mvc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.task9mvc.Controller
import com.example.task9mvc.R
import com.example.task9mvc.model.PostListsItem

class CreatePost : AppCompatActivity() {
    lateinit var controller: Controller
    lateinit var btn: Button
    lateinit var body: EditText
    lateinit var title: EditText
    lateinit var userIdPost: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        btn = findViewById(R.id.createNewPost)
        body = findViewById(R.id.postbody2)
        title = findViewById(R.id.editTitle)
        userIdPost = findViewById(R.id.userPostId)
        controller = Controller()

        initViewModel()

        btn.setOnClickListener {
            postCreate()
        }
    }

    private fun postCreate() {
        val post = PostListsItem(body.text.toString(), 1, title.text.toString(), userIdPost.text.toString().toInt())
        controller.postDetails(post)
        RecyclerListFragment.recyclerAdapter.addToListOfPost(post)

    }

    private fun initViewModel() {
        controller.postLiveData.observe(this, Observer {

            if (it.isSuccessful) {
                Toast.makeText(this@CreatePost, "Successfully created Post ${RecyclerListFragment.recyclerAdapter.itemCount}", Toast.LENGTH_LONG)
                    .show()
                Log.d("CreatePost", it.body().toString())
                Log.d("CreatePost", it.code().toString())
                Log.d("CreatePost", it.message().toString())
            } else {
                //{"code":201,"meta":null,"data":{"id":2877,"name":"xxxxxaaaaabbbbb","email":"xxxxxaaaaabbbbb@gmail.com","gender":"male","status":"active"}}
                Toast.makeText(this@CreatePost, "Failed to Create Post ${RecyclerListFragment.recyclerAdapter.itemCount}", Toast.LENGTH_LONG).show()
            }
        })
    }
}