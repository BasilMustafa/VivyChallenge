package com.bailm.vivychallenge.data

import com.bailm.vivychallenge.data.api.model.AuthorizationService
import com.bailm.vivychallenge.data.api.model.token.GrantTokenResponse
import com.bailm.vivychallenge.data.api.model.search.DoctorsResponse
import com.bailm.vivychallenge.data.api.model.imagedetails.ImageDetailResponse
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class DoctorsRepository @Inject constructor(
    var doctorsApiService: DoctorsApiService,
    var authService: AuthorizationService
) : IDoctorsRepository {

    companion object {
        var token: String = ""
    }

    override fun loadDoctorsList(
        searchKey: String?,
        lat: Double,
        lng: Double,
        lastKey: String?
    ): Observable<DoctorsResponse> {
        return doctorsApiService.searchDoctors(getHeaderWithToken(), searchKey, lat, lng, lastKey)
    }

    override fun loadDoctorDetails(doctorId: Int) {
    }

    override fun loadDoctorAvatar(photoId: String): Observable<ImageDetailResponse> {
        return doctorsApiService.requestImage(getHeaderWithToken(), photoId)
    }

    override fun loadToken(): Observable<GrantTokenResponse> {
        return authService.grantToken(getBasicHeaders(), "androidChallenge@vivy.com", "88888888")
    }


    private fun getBasicHeaders(): Map<String, String> {
        var headers = HashMap<String, String>()
        headers["Authorization"] = "Basic aXBob25lOmlwaG9uZXdpbGxub3RiZXRoZXJlYW55bW9yZQ=="
        headers["Accept"] = "application/json"
        headers["Content-Type"] = "application/x-www-form-urlencoded"
        return headers
    }

    private fun getHeaderWithToken(): Map<String, String> {
        var headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"
        headers["Accept"] = "application/json"
        headers["Content-Type"] = "application/x-www-form-urlencoded"
        return headers
    }


}