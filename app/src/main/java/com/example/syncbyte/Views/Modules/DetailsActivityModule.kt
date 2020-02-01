package com.example.syncbyte.Views.Modules

import com.example.syncbyte.Views.Adapter.AttendenceAdapter
import dagger.Module
import dagger.Provides

@Module
class DetailsActivityModule {

    @Provides
    internal fun provideViewPagerAdapter(): AttendenceAdapter {
        return AttendenceAdapter()
    }

}