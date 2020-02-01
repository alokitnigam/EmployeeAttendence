package com.example.syncbyte.DI.VMFactory

import androidx.lifecycle.ViewModelProvider
import com.example.syncbyte.DI.VMFactory.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory
}