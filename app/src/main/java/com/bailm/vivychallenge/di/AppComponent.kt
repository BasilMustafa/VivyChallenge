package com.bailm.vivychallenge.di

import android.app.Application
import com.bailm.vivychallenge.App
import com.bailm.vivychallenge.di.module.AppModule
import com.bailm.vivychallenge.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, NetworkModule::class])
public interface AppComponent {
    public fun inject(app: App)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}