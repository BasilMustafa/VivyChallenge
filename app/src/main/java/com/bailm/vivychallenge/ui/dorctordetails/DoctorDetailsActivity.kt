package com.bailm.vivychallenge.ui.dorctordetails

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bailm.vivychallenge.R
import com.bailm.vivychallenge.base.BaseActivity
import com.bailm.vivychallenge.data.api.model.search.Doctor
import com.bailm.vivychallenge.util.MapUtils
import com.bailm.vivychallenge.util.TimeUtilsParser

class DoctorDetailsActivity :
        BaseActivity<com.bailm.vivychallenge.databinding.ActivityDoctorDetailsBinding, DoctorDetailsViewModel>() {

    lateinit var mDoctor: Doctor

    companion object {
        const val EXTRA_DOCTOR = "EXTRA_DOCTOR"
        fun newIntent(context: Context, doctor: Doctor): Intent {
            val intent = Intent(context, DoctorDetailsActivity::class.java)
            intent.putExtra(EXTRA_DOCTOR, doctor)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDoctor = getDoctorFromIntent(intent)
        binding.doctor = mDoctor
        setUI()


    }

    override fun init() {
        super.init()
        binding.address.setOnClickListener {
            startActivity(MapUtils.getMapIntentForAddress(mDoctor.address))
        }
        binding.website.setOnClickListener(View.OnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mDoctor.website)))
        })

    }


    fun setUI(){
        for (hour in mDoctor.openingHours){
            binding.times.text = "${binding.times.text} \n${TimeUtilsParser.parsePair(hour)}";
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_doctor_details
    }


    private fun getDoctorFromIntent(intent: Intent): Doctor {
        val doctor: Doctor? = intent.getSerializableExtra(EXTRA_DOCTOR) as Doctor
        doctor?.let {
            return it
        }
        throw IllegalArgumentException("Doctor must be set for DoctorDetailsActivity")
    }


}
