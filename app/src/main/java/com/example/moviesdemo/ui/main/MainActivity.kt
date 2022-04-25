package com.example.moviesdemo.ui.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesdemo.adapters.AllMoviesListingAdapter
import com.example.moviesdemo.databinding.ActivityMainBinding
import com.example.moviesdemo.interfaces.ItemClickListener
import com.example.moviesdemo.models.UpcomingResult
import com.example.moviesdemo.ui.search.SearchActivity
import com.example.moviesdemo.ui.detail.DetailPageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mViewModel: MainActivityViewModel
    private var arrayListUpcomingMovies: ArrayList<UpcomingResult> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setBindings()
        clickListeners()
        if (isNetworkAvailable(this)){
            callMoviesApi()
            setObserver()
        }
    }
    private fun hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
    private fun setBindings() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    private fun setObserver() {
        mViewModel.mUpcomingMoviesResponse.observe(this,  {
            if (it != null) {
                arrayListUpcomingMovies.addAll(it.results)
                setUpcomingMoviesAdapter(arrayListUpcomingMovies)
                binding.progressBar.visibility = View.GONE
            }
        })

        mViewModel.loading.observe(this, { isError ->
            if (isError) {
                binding.progressBar.visibility = View.VISIBLE
            }
        })

        mViewModel.loadingError.observe(this, {
            if (it != null) {
                Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setUpcomingMoviesAdapter(arrayListUpcomingMovies: ArrayList<UpcomingResult>) {
        binding.recyclerWatchFragment.layoutManager = LinearLayoutManager(this)
        val adapter = AllMoviesListingAdapter(this, arrayListUpcomingMovies, this)
        binding.recyclerWatchFragment.adapter = adapter
    }


    private fun callMoviesApi() {
        binding.progressBar.visibility = View.VISIBLE
        mViewModel.callUpcomingMovies()
    }

    private fun clickListeners() {
        binding.imageSearchView.setOnClickListener {
            startActivity(Intent(applicationContext, SearchActivity::class.java))
        }
    }

    override fun watchItemCLicked(movieId : Int) {
        val intent = Intent(this, DetailPageActivity::class.java)
        intent.putExtra("mIntentID", movieId)
        startActivity(intent)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

}
