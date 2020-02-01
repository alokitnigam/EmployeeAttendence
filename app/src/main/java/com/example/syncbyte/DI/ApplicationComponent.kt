package com.example.syncbyte.DI

import android.app.Application
import com.app.nasatask.DI.Modules.ContextModule
import com.example.syncbyte.DI.Modules.ActivityBindingModule
import com.example.perpuletask.DI.VMFactory.MyViewModelModule
import com.example.syncbyte.DI.VMFactory.ViewModelFactoryModule
import com.example.syncbyte.DI.database.RoomDBModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class,AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    ViewModelFactoryModule::class, MyViewModelModule::class, RoomDBModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

}