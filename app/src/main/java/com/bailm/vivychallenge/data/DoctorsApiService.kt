package com.bailm.vivychallenge.data

import com.bailm.vivychallenge.data.api.model.search.DoctorsResponse
import com.bailm.vivychallenge.data.api.model.imagedetails.ImageDetailResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

interface DoctorsApiService {
    @GET("/api/users/me/doctors")
    fun searchDoctors(@HeaderMap headers:Map<String,String>,@Query("search") searchKey: String?, @Query("lat") lat: Double, @Query("lng") lng: Double, @Query("lastKey") lastKey: String?): Observable<DoctorsResponse>

    @GET("/api/doctors/{doctorId}/keys/profilepictures")
    fun requestImage(@HeaderMap headers: Map<String, String>,@Path("doctorId") doctorId:String):Observable<ImageDetailResponse>

}