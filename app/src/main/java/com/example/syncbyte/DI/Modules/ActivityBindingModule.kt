package  com.example.syncbyte.DI.Modules


import com.example.syncbyte.Views.DetailsActivity
import com.example.syncbyte.Views.EmployeeActivity
import com.example.syncbyte.Views.LoginActivity
import com.example.syncbyte.Views.Modules.MainActivityModule
import com.example.syncbyte.Views.MainActivity
import com.example.syncbyte.Views.Modules.DetailsActivityModule
import com.example.syncbyte.Views.Modules.EmplyeeActivityModule
import com.example.syncbyte.Views.Modules.LoginActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun contributesLoginActivity(): LoginActivity
    @ContributesAndroidInjector(modules = [DetailsActivityModule::class])
    abstract fun contributesDetailsActivity(): DetailsActivity

    @ContributesAndroidInjector(modules = [EmplyeeActivityModule::class])
    abstract fun contributesEmployeeActivity(): EmployeeActivity

}