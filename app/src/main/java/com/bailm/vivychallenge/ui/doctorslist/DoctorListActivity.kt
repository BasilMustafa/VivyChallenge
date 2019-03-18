package com.bailm.vivychallenge.ui.doctorslist

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bailm.vivychallenge.R
import com.bailm.vivychallenge.base.BaseActivity
import com.bailm.vivychallenge.data.NetworkState
import com.bailm.vivychallenge.data.api.model.search.Doctor
import com.bailm.vivychallenge.databinding.ActivityDoctorListBinding
import com.bailm.vivychallenge.ui.dorctordetails.DoctorDetailsActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task

class DoctorListActivity : BaseActivity<ActivityDoctorListBinding, DoctorListViewModel>() {
    private val TAG = "DoctorsClassName"
    lateinit var fusedLocationClient: FusedLocationProviderClient
    var doctorsAdapter = DoctorsPagingAdapter()

    companion object {
        const val LOCATION_PERMISSION_REQUEST = 1;
        fun getIntent(context: Context): Intent {
            return Intent(context, DoctorListActivity::class.java)
        }
    }

    override fun getLayoutId() = R.layout.activity_doctor_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
        ) {
            listenForLocation()
        } else {
            requestLocationPermission()
        }
        listenForLocation()
    }


    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST && permissions.size == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            listenForLocation()
        }
    }


    override fun init() {
        initMenu()
        binding.viewModel = viewModel
        initLoadingState()
    }

    private fun initLoadingState() {
        viewModel.loadingState.observe(this, Observer {
            if (it == NetworkState.LOADED || it == NetworkState.LOADING) {
                hideNoData()
            } else if (it == NetworkState.NO_DATA_AVAILABLE) {
                showNoData()
            }
            doctorsAdapter.updateNetwokState(it)


        })
    }

    private fun initMenu() {
        binding.list.layoutManager = LinearLayoutManager(this)
        doctorsAdapter.onDoctorClickedListener = object : DoctorsPagingAdapter.DoctorClickedListener {
            override fun onDoctorClicked(doctor: Doctor) {
                val intent = DoctorDetailsActivity.newIntent(this@DoctorListActivity, doctor)
                startActivity(intent)
            }

            override fun onDialDoctorClicked(phoneNumber: String) {
                dialNumber(phoneNumber)
            }
        }
        binding.list.adapter = doctorsAdapter
    }


    fun showNoData() {
        binding.noData.visibility = View.VISIBLE
    }

    fun hideNoData() {
        binding.noData.visibility = View.GONE
    }


    @SuppressLint("MissingPermission")
    private fun listenForLocation() {
        createLocationRequest()?.let {
            val builder = LocationSettingsRequest.Builder()
                    .addLocationRequest(it)
            val client: SettingsClient = LocationServices.getSettingsClient(this)
            val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
            task.addOnSuccessListener {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let { loc ->
                        listenForDoctors(loc)
                    }
                }.addOnFailureListener {
                    OnFailureListener { p0 -> p0.printStackTrace() }
                }
            }
            task.addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        exception.startResolutionForResult(
                                this@DoctorListActivity,
                                1
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                    }
                }

            }

        }


    }


    private fun createLocationRequest(): LocationRequest? {
        return LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun listenForDoctors(location: Location) {
        viewModel.loadDoctors(location.latitude, location.longitude)
        viewModel.userList.observe(this, Observer {
            doctorsAdapter.submitList(it)
        })
    }


    fun dialNumber(phoneNumber: String) {
        val numberUri = Uri.parse("tel:$phoneNumber")
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = numberUri
        startActivity(intent)
    }


}



