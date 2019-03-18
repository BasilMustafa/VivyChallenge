package com.bailm.vivychallenge.data

import com.bailm.vivychallenge.data.api.model.token.GrantTokenResponse
import com.bailm.vivychallenge.data.api.model.search.DoctorsResponse
import com.bailm.vivychallenge.data.api.model.imagedetails.ImageDetailResponse
import io.reactivex.Observable

interface IDoctorsRepository {
    fun loadDoctorsList(searchKey: String?,
                        lat: Double,
                        lng: Double,
                        lastKey: String?):Observable<DoctorsResponse>

    fun loadDoctorDetails(doctorId: Int)

    fun loadDoctorAvatar(photoId: String):Observable<ImageDetailResponse>

    fun loadToken(): Observable<GrantTokenResponse>
}