package com.bailm.vivychallenge.ui.doctorslist.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.bailm.vivychallenge.data.IDoctorsRepository
import com.bailm.vivychallenge.data.NetworkState
import com.bailm.vivychallenge.data.api.model.search.Doctor

class DoctorsDataSourceFactory : DataSource.Factory<String, Doctor>() {
    lateinit var doctorsRepository: IDoctorsRepository
    lateinit var searchKey: String
    var lat: Double = 0.0
    var lng: Double = 0.0
    val sourceData = MutableLiveData<DoctorsDataSource>();
    lateinit var source: DoctorsDataSource
    override fun create(): DataSource<String, Doctor> {
        source = DoctorsDataSource(doctorsRepository, lat, lng, searchKey)
        sourceData.postValue(source)
        return source
    }

    fun setParams(doctorsRepository: IDoctorsRepository, searchKey: String, lat: Double , lng: Double) {
        this.lat = lat
        this.lng = lng
        this.doctorsRepository = doctorsRepository
        this.searchKey = searchKey

    }


}