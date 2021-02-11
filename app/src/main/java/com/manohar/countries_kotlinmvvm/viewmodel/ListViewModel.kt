package com.manohar.countries_kotlinmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manohar.countries_kotlinmvvm.model.CountriesService
import com.manohar.countries_kotlinmvvm.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel:ViewModel()
{
  val countries = MutableLiveData<List<Country>>()
    val loaderror = MutableLiveData<Boolean>()
    val isloading = MutableLiveData<Boolean>()
    private  val countriesService = CountriesService()
    private  val disposable = CompositeDisposable()

    fun refresh()
    {
        loaddata()
    }

    private fun loaddata()
    {

        isloading.value = true

        disposable.add(
            countriesService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>()
                {
                    override fun onSuccess(value: List<Country>?) {
                        countries.value = value
                        isloading.value = false
                        loaderror.value = false
                    }

                    override fun onError(e: Throwable?) {
                         loaderror.value = true
                        isloading.value=false;
                    }
                })

        )


        /*
        var mockdata = listOf(
                Country("country1"),
                Country("country2"),
                Country("country3"),
                Country("country4"),
                Country("country5"),
                Country("country6"),
                Country("country7"),
                Country("country8"),
                Country("country9"),
                Country("country10")

                )


        countries.value = mockdata
        mockdataerror.value = false
        isloading.value = false
*/

    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}