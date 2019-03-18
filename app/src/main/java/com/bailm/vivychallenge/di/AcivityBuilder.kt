package com.bailm.vivychallenge.di

import com.bailm.vivychallenge.ui.doctorslist.DoctorListActivity
import com.bailm.vivychallenge.ui.dorctordetails.DoctorDetailsActivity
import com.bailm.vivychallenge.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AcivityBuilder {
    @ContributesAndroidInjector
    internal abstract fun bindDoctorsListActivity(): DoctorListActivity

    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity


    @ContributesAndroidInjector
    internal abstract fun bindDoctorDetailsActivity(): DoctorDetailsActivity
}