package com.bailm.vivychallenge.ui.doctorslist.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.bailm.vivychallenge.data.IDoctorsRepository
import com.bailm.vivychallenge.data.NetworkState
import com.bailm.vivychallenge.data.api.model.search.Doctor
import com.bailm.vivychallenge.data.api.model.search.DoctorsResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DoctorsDataSource(
    var doctorRepository: IDoctorsRepository,
    var lat: Double,
    var lng: Double,
    var searchKey: String
) : ItemKeyedDataSource<String, Doctor>() {
    var networkState = MutableLiveData<NetworkState>()
    var lastKey: String = ""
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Doctor>) {
        networkState.postValue(NetworkState.LOADING)
        loadData(searchKey, lat, lng, lastKey) { doctors ->
            callback.onResult(doctors)
            if (doctors.isEmpty()) {
                networkState.postValue(NetworkState.NO_DATA_AVAILABLE)
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Doctor>) {
        networkState.postValue(NetworkState.LOADING)
        loadData(searchKey, lat, lng, params.key) { doctors ->
            callback.onResult(doctors)

        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Doctor>) {

    }

    override fun getKey(item: Doctor): String {
        return lastKey
    }

    private fun loadData(
        searchKey: String,
        lat: Double,
        lng: Double,
        lastKey: String?,
        //a func to deal with the data after getting it from the pai
        respondingFunc: (List<Doctor>) -> Unit
    ) {
        doctorRepository.loadDoctorsList(searchKey, lat, lng, lastKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DoctorsResponse> {
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(t: DoctorsResponse) {
                    networkState.postValue(NetworkState.LOADED)
                    respondingFunc.invoke(t.doctors)
                    if (t.lastKey == null) {
                        this@DoctorsDataSource.lastKey = INVALID_KEY
                    } else {
                        this@DoctorsDataSource.lastKey = t.lastKey
                    }

                }

                override fun onError(e: Throwable) {


                }

            })
    }


    override fun isInvalid(): Boolean {
        return lastKey == INVALID_KEY
    }


    companion object {
        const val INVALID_KEY = "INVALID_KEY"
    }

    override fun invalidate() {
        lastKey = ""
        super.invalidate()
    }
}