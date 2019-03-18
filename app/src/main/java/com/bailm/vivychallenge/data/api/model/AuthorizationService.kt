package com.bailm.vivychallenge.data.api.model

import com.bailm.vivychallenge.data.api.model.token.GrantTokenResponse
import io.reactivex.Observable
import retrofit2.http.*

interface AuthorizationService {
    @FormUrlEncoded
    @POST("/oauth/token?grant_type=password")
    fun grantToken(@HeaderMap headers: Map<String, String>, @Field("username") username:String, @Field("password") password:String): Observable<GrantTokenResponse>

}

