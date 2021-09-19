package com.example.task9mvc

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.task9mvc.model.CommentsItem
import com.example.task9mvc.model.PostListsItem
import com.example.task9mvc.network.RetroInstance
import com.example.task9mvc.network.RetroService
import kotlinx.coroutines.*
import retrofit2.Response
import java.net.UnknownHostException

class Controller {
    var commentLiveData: MutableLiveData<Response<CommentsItem>> = MutableLiveData()

    //This function returns the detailsLiveData
    fun createCommentObserver(): MutableLiveData<Response<CommentsItem>> {
        return commentLiveData
    }

    @DelicateCoroutinesApi
    fun commentDetails(comment: CommentsItem) {
        MainScope().launch(Dispatchers.IO) {
            try {
                val retroInstance =
                    RetroInstance.getRetroInstance().create(RetroService::class.java)
                val response = retroInstance.createComment(comment)
                commentLiveData.postValue(response)
            } catch (e: UnknownHostException) {
                e.printStackTrace()
            }
        }
    }

    var postLiveData: MutableLiveData<Response<PostListsItem>> = MutableLiveData()

    //This function returns the detailsLiveData
    fun createPostObserver(): MutableLiveData<Response<PostListsItem>> {
        return postLiveData
    }


    //This function is responsible of making Api call to our Api server in IO instead of main thread
    fun postDetails(post: PostListsItem) {
        MainScope().launch(Dispatchers.IO) {
            try {
                val retroInstance =
                    RetroInstance.getRetroInstance().create(RetroService::class.java)
                val response = retroInstance.createPost(post)
                postLiveData.postValue(response)
            } catch (e: UnknownHostException) {
                e.printStackTrace()
            }
        }
    }


    // Holds the details of the pokemon retrieved from the API
    var recyclerListLiveData: MutableLiveData<List<PostListsItem>> = MutableLiveData()


    //This function returns the recyclerListLiveData
    fun getRecyclerListObserver(): MutableLiveData<List<PostListsItem>> {
        return recyclerListLiveData
    }

    //This function is responsible of making Api call to our Api server in IO instead of main thread
    fun makeApiCall() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val retroInstance =
                    RetroInstance.getRetroInstance().create(RetroService::class.java)
                val response = retroInstance.getDataFromApi()
                recyclerListLiveData.postValue(response)
            } catch (e: UnknownHostException) {
                e.printStackTrace()
            }
        }
    }

    fun searchPosts(idd: Int) {
        MainScope().launch(Dispatchers.IO) {
            try {
                val retroInstance =
                    RetroInstance.getRetroInstance().create(RetroService::class.java)
                val response = retroInstance.filterPost(idd)
                recyclerListLiveData.postValue(response)
            } catch (e: UnknownHostException) {
                e.printStackTrace()
            }
        }
    }

    // This variable Holds the details of the pokemon retrieved from the API
    var commentsLiveData: MutableLiveData<List<CommentsItem>> = MutableLiveData()

    //This function returns the detailsLiveData
    fun getRecyclerListObserver2(): MutableLiveData<List<CommentsItem>> {
        return commentsLiveData
    }


    //This function is responsible of making Api call to our Api server in IO instead of main thread
    fun callComments(id: Int) {
        try {
            MainScope().launch(Dispatchers.IO) {
                val retroInstance =
                    RetroInstance.getRetroInstance().create(RetroService::class.java)
                val response = retroInstance.getComments(id)
                commentsLiveData.postValue(response)
            }
        } catch (e: Exception) {

        }
    }
}