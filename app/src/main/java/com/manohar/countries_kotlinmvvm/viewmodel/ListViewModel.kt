package com.manohar.countries_kotlinmvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
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

                    override fun onSuccess(t: List<Country>) {
                        countries.value = t
                        isloading.value = false
                        loaderror.value = false
                    }

                    override fun onError(e: Throwable) {
                        loaderror.value = true
                        Log.i("error", e.toString())
                        isloading.value=false;
                    }
                })

        )




    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}