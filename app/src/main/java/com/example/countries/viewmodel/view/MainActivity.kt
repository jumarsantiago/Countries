package com.example.countries.viewmodel.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countries.R
import com.example.countries.model.Country
import com.example.countries.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_country.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : ListViewModel
    val countriesAdapter = CountryListAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        recyclerViewCountries.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, Observer{countries ->
            countries.let {
                recyclerViewCountries.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it) }
        })

        viewModel.countryLoadError.observe(this, Observer{isError ->
            isError?.let { tv_error.visibility = if (it) View.VISIBLE else View.GONE }
        })
        viewModel.loading.observe(this, Observer{isLoading ->
            isLoading.let { progressBarLoading.visibility = if (it) View.VISIBLE else View.GONE
                if (it){
                    tv_error.visibility = View.GONE
                    recyclerViewCountries.visibility = View.GONE
                }
            }
        })
    }
}