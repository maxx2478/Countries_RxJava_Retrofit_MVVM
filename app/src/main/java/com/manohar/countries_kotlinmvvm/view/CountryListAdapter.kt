package com.manohar.countries_kotlinmvvm.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.manohar.countries_kotlinmvvm.R
import com.manohar.countries_kotlinmvvm.model.Country
import com.manohar.countries_kotlinmvvm.model.Languages
import com.manohar.countries_kotlinmvvm.util.getProgressDrawable
import com.manohar.countries_kotlinmvvm.util.loadimage
import java.util.*

class CountryListAdapter:
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>
{
    var context:Context?=null
    var countries:ArrayList<Country>?=null
    constructor(countries: ArrayList<Country>, context: Context): super()
    {
        this.context = context
        this.countries = countries

    }

    fun updateCountryList(newCountries: List<Country>)
    {
        countries!!.clear()
        countries!!.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryViewHolder(
            LayoutInflater.from(parent.context)!!.inflate(R.layout.item_country, parent, false)
        )



    override fun getItemCount() = countries!!.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
       holder.bind(countries!![position])

        holder.itemView.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()

        })
    }

    class CountryViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val countryname = view.findViewById<TextView>(R.id.name)
        val countryflag = view.findViewById<ImageView>(R.id.flag)
        val countrycapital = view.findViewById<TextView>(R.id.capital)
        val countryregions = view.findViewById<TextView>(R.id.regions)
        val countrysubregions = view.findViewById<TextView>(R.id.subregions)
        val countrypopulation = view.findViewById<TextView>(R.id.population)
        val countryborders = view.findViewById<TextView>(R.id.borders)
        val countrylanguages = view.findViewById<TextView>(R.id.languages)

        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(country: Country)
        {
          countryname.text = country.countryname
            countrycapital.text = country.capital
            countryregions.text = country.region
            countrysubregions.text = country.subregion
            countrypopulation.text = country.population.toString()
            var borderstext:String = ""
            for (text:String in country.borders)
            {
                borderstext = borderstext + " " + text
            }
            countryborders.text = borderstext

            var languagestext:String = ""
            for (text:Languages in country.languages) {
                languagestext = languagestext + " " +text.name
            }
            countrylanguages.text = languagestext

            countryflag.loadimage(country.flagPNG, progressDrawable)
        }

    }



}