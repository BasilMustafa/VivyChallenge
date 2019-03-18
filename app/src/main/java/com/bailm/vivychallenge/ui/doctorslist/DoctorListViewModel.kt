package com.bailm.vivychallenge.ui.doctorslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bailm.vivychallenge.data.IDoctorsRepository
import com.bailm.vivychallenge.data.api.model.search.Doctor
import com.bailm.vivychallenge.ui.doctorslist.paging.DoctorsDataSourceFactory
import javax.inject.Inject


class DoctorListViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var doctorsRepository: IDoctorsRepository
    var searchKey = ""
    var doctorsDataSourceFactory: DoctorsDataSourceFactory = DoctorsDataSourceFactory();
    var loadingState = switchMap(doctorsDataSourceFactory.sourceData,{it.networkState})
    var lat: Double = 0.0
    var lng: Double = 0.0
    var userList: LiveData<PagedList<Doctor>> = MutableLiveData<PagedList<Doctor>>()
    fun loadDoctors(lat: Double, lng: Double) {
        this.lat = lat
        this.lng = lng
        doctorsDataSourceFactory.setParams(doctorsRepository, searchKey, lat, lng)
        val pagedListConfig = PagedList.Config.Builder().setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20).build()
        userList = LivePagedListBuilder(doctorsDataSourceFactory, pagedListConfig)
                .build()

    }

    fun doSearch() {
        doctorsDataSourceFactory.source.invalidate()
        loadDoctors(lat, lng)
    }


}