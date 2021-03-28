package com.manohar.countries_kotlinmvvm.view

import android.app.SearchManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.manohar.countries_kotlinmvvm.R
import com.manohar.countries_kotlinmvvm.model.Country
import com.manohar.countries_kotlinmvvm.util.AppDB
import com.manohar.countries_kotlinmvvm.viewmodel.ListViewModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel // gets initialized later time
    private var countriesAdapter:CountryListAdapter?=null
    var db: AppDB? = null
    var list:List<Country>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = ArrayList<Country>()
        countriesAdapter = CountryListAdapter(kotlin.collections.arrayListOf(), this)

        //initialization of viewmodel
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        if (isOnline(this))
        {
            viewModel.refresh()
        }

        findViewById<RecyclerView>(R.id.countriesList).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }


        val slayout = findViewById<SwipeRefreshLayout>(R.id.swiperl)
        slayout.setOnRefreshListener {
            slayout.isRefreshing = false
            viewModel.refresh()
        }

        db= Room.databaseBuilder(applicationContext, AppDB::class.java,"BookDB").build()

        if (isOnline(this))
        {
            observeViewModel()
        }
        else
        {
           fetchfromDB()
        }

    }

     fun observeViewModel() {
        viewModel.countries.observe(this, Observer {countries ->
            findViewById<RecyclerView>(R.id.countriesList).visibility = View.VISIBLE

            countries?.let {


                CoroutineScope(Dispatchers.IO).launch {
                    db!!.countryDao().saveBooks(it)
                    list= db!!.countryDao().getAllBooks()
                    withContext(Dispatchers.Main)
                    {
                        countriesAdapter!!.updateCountryList(list!!)
                    }

                }

            }



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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

            menuInflater.inflate(R.menu.menu, menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.delete -> {


                    CoroutineScope(Dispatchers.IO).launch() {
                        db!!.clearAllTables()
                        withContext(Dispatchers.Main)
                        {
                            fetchfromDB()
                            Toast.makeText(this@MainActivity, "Database Cleared", Toast.LENGTH_SHORT).show()

                        }
                    }




            }
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    fun fetchfromDB()
    {
        CoroutineScope(Dispatchers.IO).launch {
            list= db!!.countryDao().getAllBooks()
            withContext(Dispatchers.Main)
            {
                findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE
                findViewById<TextView>(R.id.list_error).visibility = View.GONE
                findViewById<RecyclerView>(R.id.countriesList).visibility = View.VISIBLE
                countriesAdapter!!.updateCountryList(list!!)
                if (isOnline(this@MainActivity))
                {
                    Toast.makeText(this@MainActivity, "Data Cleared in Online Mode, Swipe down to Reload Data from Server", Toast.LENGTH_SHORT).show()
                }

                else {
                    Toast.makeText(this@MainActivity, "Data Cleared in Offline Mode", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }


}