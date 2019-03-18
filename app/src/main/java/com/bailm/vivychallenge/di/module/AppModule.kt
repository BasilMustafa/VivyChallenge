package com.bailm.vivychallenge.di.module

import android.content.Context
import com.bailm.vivychallenge.di.AcivityBuilder
import com.bailm.vivychallenge.di.ViewModelModule
import dagger.Module
import dagger.Provides

@Module(includes = [ViewModelModule::class, AcivityBuilder::class])
class AppModule {
    @Provides
    fun providesContext(app:Context):Context{
        return app.applicationContext;
    }


}