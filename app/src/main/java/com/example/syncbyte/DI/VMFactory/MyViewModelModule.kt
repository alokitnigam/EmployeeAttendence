package com.example.perpuletask.DI.VMFactory

import androidx.lifecycle.ViewModel
import com.example.syncbyte.DI.VMFactory.ViewModelKey
import com.example.syncbyte.Views.ViewModels.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MyViewModelModule {
    @Binds
    @IntoMap


    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(myViewModel: MainActivityViewModel): ViewModel


}