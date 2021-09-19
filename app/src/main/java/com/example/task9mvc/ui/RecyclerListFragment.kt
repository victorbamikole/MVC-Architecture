package com.example.task9mvc.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task9mvc.ConnectivityLiveData
import com.example.task9mvc.Controller
import com.example.task9mvc.R
import com.example.task9mvc.adapter.RecyclerViewAdapter
import com.example.task9mvc.adapter.onItemClickListener
import com.example.task9mvc.adapter.onNewBtnClickListener
import com.example.task9mvc.model.PostListsItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecyclerListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecyclerListFragment : Fragment(), onItemClickListener, onNewBtnClickListener {


    lateinit var btn: FloatingActionButton
    lateinit var controller: Controller
    private lateinit var searchBtn: Button
    lateinit var recyclerView: RecyclerView
    private var idd: Int = 2
    private lateinit var connectivityLiveData: ConnectivityLiveData
//    lateinit var viewModel: MainActivityViewModel
    private lateinit var searchBox: EditText
    var qu: Int = 4


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
//        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)


        val view = inflater.inflate(R.layout.fragment_recycler_list, container, false)
        connectivityLiveData = ConnectivityLiveData(requireActivity().application)
        controller = Controller()
        val firstName = view.findViewById<TextView>(R.id.title)
        searchBtn = view.findViewById(R.id.searchBtn)
        btn = view.findViewById(R.id.createPostBtn)
        searchBox = view.findViewById(R.id.searchBox)
        val name = firstName?.text.toString()
        val bundle = Bundle()
        bundle.putString("keys", name)


        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decoration)

        btn.setOnClickListener {
            val i = Intent(activity, CreatePost::class.java)
            startActivity(i)
        }

        initViewModel()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBtn.setOnClickListener {
            var id = searchBox.text.toString().toInt()
            if (id == 0) {
                Toast.makeText(requireContext(), "0 is not a valid number", Toast.LENGTH_SHORT)
                    .show()
            } else {
                searchPost(id)
            }

        }
    }

    //This function initialize our recycler view or the view model
    private fun initViewModel() {


        connectivityLiveData.observe(viewLifecycleOwner) { isAvailable ->
            //2
            when (isAvailable) {
                true -> {
                    //3
                    controller.makeApiCall()
//                    statusButton.visibility = View.GONE
//                    recyclerView?.visibility = View.VISIBLE
//                    searchBox.visibility = View.VISIBLE
                }
                false -> {
//                    statusButton.visibility = View.VISIBLE
//                    recyclerView?.visibility = View.GONE
//                    searchBox.visibility = View.GONE
//                    searchBtn.visibility = View.GONE
                }
            }
        }

        //Observer<Posts>
        controller.getRecyclerListObserver().observe(viewLifecycleOwner) {
            if (it != null) {
//                recyclerAdapter.setUpdatedData(it.results as ArrayList<Result>)

                recyclerAdapter = RecyclerViewAdapter(this, this)
                recyclerAdapter.items = (it as MutableList<PostListsItem>)
                recyclerAdapter.notifyDataSetChanged()
                recyclerView.adapter = recyclerAdapter

            } else {
                Toast.makeText(activity, "error in getting data", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun searchPost(num: Int) {
        searchBtn.setOnClickListener {
            if (!TextUtils.isEmpty(searchBtn.text)) {
                controller.searchPosts(num)

            } else {
                controller.getRecyclerListObserver().observe(viewLifecycleOwner) {

                    controller.makeApiCall()
                }
            }
        }

    }

    companion object {

        lateinit var recyclerAdapter: RecyclerViewAdapter
        @JvmStatic
        fun newInstance() =
            RecyclerListFragment()
    }

    override fun onItemClick(position: Int, objectData: PostListsItem) {

        val intent: Intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra("postlists", objectData)
        intent.putExtra("pos", position)
        startActivity(intent)
    }

    override fun onNewBtnClick(position: Int, objectData: PostListsItem) {
        val intent = Intent(activity, CreateComment::class.java)
        startActivity(intent)
    }
}


//private fun ViewModelProvider.get(modelClass: Class<Controller>): MainActivityViewModel {
//
//    return MainActivityViewModel()
//}