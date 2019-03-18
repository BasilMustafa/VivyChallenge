package com.bailm.vivychallenge.interceptors

import com.bailm.vivychallenge.data.IDoctorsRepository
import com.bailm.vivychallenge.data.api.model.token.GrantTokenResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenRefreshInterceptor constructor( var repository:IDoctorsRepository):Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request();
        val response = chain.proceed(request)
        if (!response.isSuccessful && response.code()==401 ){ //token needs to be refreshed
            repository.loadToken().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<GrantTokenResponse> {
                        override fun onComplete() {}
                        override fun onSubscribe(d: Disposable) {}
                        override fun onNext(t: GrantTokenResponse) {
                             chain.proceed(updateRequestWithToken(request,t.access_token))
                        }
                        override fun onError(e: Throwable) {
                        }
                    })
        }

        return chain.proceed(request)
    }


    fun updateRequestWithToken(request:Request, token:String):Request{
        var builder:Request.Builder = request.newBuilder()
        builder.addHeader("Authorization","Bearer $token")
        return builder.build()
    }

}