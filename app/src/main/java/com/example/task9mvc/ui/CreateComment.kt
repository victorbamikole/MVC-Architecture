package com.example.task9mvc.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.task9mvc.Controller
import com.example.task9mvc.R
import com.example.task9mvc.model.CommentsItem

class CreateComment: AppCompatActivity() {
    lateinit var commentBody: EditText
    lateinit var email: EditText
    lateinit var postId: EditText
    private lateinit var commenterName: EditText
    lateinit var commentButton: Button
    lateinit var controller: Controller


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_comment)
        commentButton = findViewById(R.id.createNewComment)
        commentBody = findViewById(R.id.commentBody)
        commenterName = findViewById(R.id.name)
        email = findViewById(R.id.email)
        postId = findViewById(R.id.postIdInput)
        controller = Controller()

        initViewModel()

        commentButton.setOnClickListener {
            commentCreate()

        }
    }

    private fun commentCreate() {
        val comment = CommentsItem(commentBody.text.toString(), email.text.toString(), 1,  commenterName.text.toString(), postId.text.toString().toInt())
        controller.commentDetails(comment)
    }

    private fun initViewModel() {

        controller.commentLiveData.observe(this, Observer {

            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully created Comment", Toast.LENGTH_LONG)
                    .show()
                Log.d("CreatePost", it.body().toString())
                Log.d("CreatePost", it.code().toString())
                Log.d("CreatePost", it.message().toString())
            } else {
                //{"code":201,"meta":null,"data":{"id":2877,"name":"xxxxxaaaaabbbbb","email":"xxxxxaaaaabbbbb@gmail.com","gender":"male","status":"active"}}
                Toast.makeText(this, "Failed to Create Comment", Toast.LENGTH_LONG).show()
            }
        })
    }
}