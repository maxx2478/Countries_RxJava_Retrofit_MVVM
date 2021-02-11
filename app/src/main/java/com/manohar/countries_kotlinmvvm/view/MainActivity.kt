package com.manohar.countries_kotlinmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.manohar.countries_kotlinmvvm.R
import com.manohar.countries_kotlinmvvm.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel // gets initialized later time
    private var countriesAdapter:CountryListAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        countriesAdapter = CountryListAdapter(kotlin.collections.arrayListOf(), this)

        //initialization of viewmodel
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        findViewById<RecyclerView>(R.id.countriesList).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }


        val slayout = findViewById<SwipeRefreshLayout>(R.id.swiperl)
        slayout.setOnRefreshListener {
            slayout.isRefreshing = false
            viewModel.refresh()
        }

        observeViewModel()

    }

     fun observeViewModel() {
        viewModel.countries.observe(this, Observer {countries ->
            findViewById<RecyclerView>(R.id.countriesList).visibility = View.VISIBLE

            countries?.let { countriesAdapter!!.updateCountryList(it) } //'it' refers to list of the countries
        })

         viewModel.loaderror.observe(this, Observer {iserror->
             iserror?.let { findViewById<TextView>(R.id.list_error).visibility = if (it) View.VISIBLE else View.GONE}
         })

         viewModel.isloading.observe(this, Observer {isLoading ->
             isLoading?.let { findViewById<ProgressBar>(R.id.progressbar).visibility = if (it) View.VISIBLE else View.GONE
                 if (it) {
                     findViewById<TextView>(R.id.list_error).visibility = View.GONE
                     findViewById<RecyclerView>(R.id.countriesList).visibility = View.GONE

                 }
             }

        })

    }
}