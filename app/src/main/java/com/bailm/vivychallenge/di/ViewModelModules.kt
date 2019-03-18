package com.bailm.vivychallenge.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bailm.vivychallenge.ui.doctorslist.DoctorListViewModel
import com.bailm.vivychallenge.ui.dorctordetails.DoctorDetailsViewModel
import com.bailm.vivychallenge.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DoctorListViewModel::class)
    abstract fun bindDoctorListViewModel(doctorsListViewModel: DoctorListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashActivityViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DoctorDetailsViewModel::class)
    abstract fun bindDoctorDetailsViewModel(splashActivityViewModel: SplashViewModel): ViewModel


}