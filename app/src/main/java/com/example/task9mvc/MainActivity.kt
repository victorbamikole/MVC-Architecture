package com.example.task9mvc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.task9mvc.ui.RecyclerListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var connectivityLiveData: ConnectivityLiveData//new
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectivityLiveData = ConnectivityLiveData(application)//new


        //The function is called to add fragment to the fragment manager
        setupFragment()
    }

    //This function adds fragment to out fragment manager
    private fun setupFragment() {
        val fragment = RecyclerListFragment.newInstance()
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(android.R.id.content, fragment)
        fragmentTransaction.commit()
    }

    //This function overrides the android back key press
    override fun onBackPressed() {
        finishAffinity()
        finish()
    }
}