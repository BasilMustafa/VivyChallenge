package com.bailm.vivychallenge.ui.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bailm.vivychallenge.data.DoctorsRepository
import com.bailm.vivychallenge.data.IDoctorsRepository
import com.bailm.vivychallenge.data.api.model.token.GrantTokenResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var doctorsRepository: IDoctorsRepository;

    var token: MutableLiveData<String> = MutableLiveData();
    fun grantToken() {
        doctorsRepository.loadToken().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<GrantTokenResponse> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(grantTokenResponse: GrantTokenResponse) {
                    Log.d("TOKEN_RECEIVED", grantTokenResponse.access_token)
                    DoctorsRepository.token = grantTokenResponse.access_token
                    token.value = grantTokenResponse.access_token
                }

                override fun onError(e: Throwable) {
                    Log.d("ERROR:OBSERVER", e.message)

                }

                override fun onComplete() {
                }
            })
    }


}