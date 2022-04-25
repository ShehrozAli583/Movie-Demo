package com.example.moviesdemo.ui.search

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesdemo.R
import com.example.moviesdemo.adapters.DummyPlaceHolderAdapter
import com.example.moviesdemo.adapters.SearchResultListingAdapter
import com.example.moviesdemo.databinding.ActivitySearchBinding
import com.example.moviesdemo.interfaces.ItemClickListener
import com.example.moviesdemo.models.SearchItemsModel
import com.example.moviesdemo.models.SearchedResult
import com.example.moviesdemo.utils.Extensions.hideKeyboard
import com.example.moviesdemo.ui.detail.DetailPageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var mViewModel: SearchActivityViewModel
    private lateinit var binding:ActivitySearchBinding
    private var mSearchItemResultList = ArrayList<SearchedResult>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindings()
        populateDataInModel()
        setObserver()
        callBacks()
    }

    private fun setBindings() {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mViewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)
    }

    private fun callBacks() {
        binding.imageClearSearch.setOnClickListener {
            if (binding.editTextSearch.text.toString().isNotEmpty()) {
                binding.editTextSearch.setText("")
            } else {
                finish()
            }
        }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {

                if (s.toString().isEmpty()) {
                    binding.recyclerBeforeSearch.visibility = View.VISIBLE
                    binding.linearSearchedResults.visibility = View.GONE
                } else {
                    callSearchApi(s.toString())
                }

            }
        })

        binding.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                if (binding.showEmptyText.visibility == View.VISIBLE) {
                    this.hideKeyboard(view = binding.showEmptyText)
                } else {
                    this.hideKeyboard(view = binding.showEmptyText)
                    binding.linearAfterResultFound.visibility = View.VISIBLE
                    binding.linearBeforeResultFound.visibility = View.GONE
                    binding.txtShowTopResult.visibility = View.GONE
                    binding.viewShowTopResult.visibility = View.GONE
                }
                return@setOnEditorActionListener true
            }
            false
        }

        binding.imageBackFromSearch.setOnClickListener {
            binding.linearAfterResultFound.visibility = View.GONE
            binding.linearBeforeResultFound.visibility = View.VISIBLE
            binding.txtShowTopResult.visibility = View.VISIBLE
            binding.viewShowTopResult.visibility = View.VISIBLE
        }
    }

    private fun callSearchApi(query: String) {
        binding.progressSearch.visibility = View.VISIBLE
        mViewModel.callSearchedMovies(query)

    }


    @SuppressLint("SetTextI18n")
    private fun setObserver() {
        mViewModel.mSearchedMoviesResponse.observe(this, {
            if (it != null) {
                mSearchItemResultList = it.results as ArrayList<SearchedResult>
                binding.searchResultCount.text = it.totalResults.toString() + " Results Found"
                setSearchAdapter()
                binding.progressSearch.visibility = View.VISIBLE
            }
        })

        mViewModel.loading.observe(this, { isError ->
            if (isError) {
                binding.progressSearch.visibility = View.VISIBLE
            }
        })

        mViewModel.loadingError.observe(this, {
            if (it != null) {
                Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setSearchAdapter() {
        if (mSearchItemResultList.size > 0) {
            binding.showEmptyText.visibility = View.GONE
            binding.linearSearchedResults.visibility = View.VISIBLE
            binding.recyclerBeforeSearch.visibility = View.GONE
            binding.recyclerAfterSearch.layoutManager = LinearLayoutManager(this)
            val adapterSecond = SearchResultListingAdapter(this, mSearchItemResultList, this)
            binding.recyclerAfterSearch.adapter = adapterSecond
        } else {
            binding.showEmptyText.visibility = View.VISIBLE
        }
    }

    private fun populateDataInModel() {
        val mSearchItemList = ArrayList<SearchItemsModel>()
        mSearchItemList.add(SearchItemsModel("Friends", R.drawable.search_one, "Comedies"))
        mSearchItemList.add(SearchItemsModel("A Time To Kill", R.drawable.search_two, "Crime"))
        mSearchItemList.add(SearchItemsModel("Family", R.drawable.search_three, "Family"))
        mSearchItemList.add(
            SearchItemsModel(
                "Social Dilemma",
                R.drawable.search_four,
                "Documentary"
            )
        )
        mSearchItemList.add(SearchItemsModel("The King", R.drawable.search_five, "Dramas"))
        mSearchItemList.add(SearchItemsModel("Timeless", R.drawable.search_six, "Fantasy"))
        mSearchItemList.add(SearchItemsModel("Holidays", R.drawable.search_seven, "Holidays"))
        mSearchItemList.add(SearchItemsModel("The Mummy", R.drawable.search_eight, "Horror"))
        mSearchItemList.add(SearchItemsModel("In Time", R.drawable.search_nine, "Sci-Fi"))
        mSearchItemList.add(SearchItemsModel("It", R.drawable.search_ten, "Thriller"))

        val mSearchItemResultList = ArrayList<SearchItemsModel>()
        mSearchItemResultList.add(
            SearchItemsModel(
                "Timeless",
                R.drawable.search_result_one,
                "Fantasy"
            )
        )
        mSearchItemResultList.add(
            SearchItemsModel(
                "In Time",
                R.drawable.search_result_two,
                "Sci-Fi"
            )
        )
        mSearchItemResultList.add(
            SearchItemsModel(
                "A Time To Kill",
                R.drawable.search_result_three,
                "Crime"
            )
        )
        binding.recyclerBeforeSearch.visibility = View.VISIBLE
        binding.linearSearchedResults.visibility = View.GONE
        binding.recyclerBeforeSearch.layoutManager = GridLayoutManager(this, 2)
        val adapter = DummyPlaceHolderAdapter(mSearchItemList)
        binding.recyclerBeforeSearch.adapter = adapter

    }

    override fun watchItemCLicked(movieId: Int) {
        val intent = Intent(this, DetailPageActivity::class.java)
        intent.putExtra("mIntentID", movieId)
        startActivity(intent)
    }

}