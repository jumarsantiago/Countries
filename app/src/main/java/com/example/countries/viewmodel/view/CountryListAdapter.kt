package com.example.countries.viewmodel.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.model.Country
import com.example.countries.util.getProgressDrawable
import com.example.countries.util.loadImage
import kotlinx.android.synthetic.main.item_country.view.*

class CountryListAdapter(var countries :ArrayList<Country>):
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val countryName = view.tv_country_name
    private val capital = view.tv_capital
    private val image = view.img_flag
    private val progressDrawable = getProgressDrawable(view.context)
    fun bind(country: Country){
            countryName.text  = country.countryName
            capital.text = country.capital
            image.loadImage(country.flag, progressDrawable)
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= CountryViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
    )

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    fun updateCountries(newCountries : List<Country>){
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

}
