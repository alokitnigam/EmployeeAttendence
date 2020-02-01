package com.example.syncbyte.Views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.syncbyte.Base.BaseActivity
import com.example.syncbyte.R
import com.example.syncbyte.Views.ViewModels.LoginActivityViewModel
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity<LoginActivityViewModel>() {
    @Inject
    lateinit var loginActivityViewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpListeners()
        setUpObservers()

    }

    private fun setUpObservers() {
        getViewModel().check.observe(this, Observer {
            if(it !=null){
                startActivity(Intent(this,EmployeeActivity::class.java)
                    .putExtra("employee",it))
                finish()

            }else{
                Toast.makeText(this,"EmployeeId or password incorrect", Toast.LENGTH_SHORT).show()

            }
        })
    }

    private fun setUpListeners() {
        save.setOnClickListener {
            if(employeeid.text.isNullOrBlank() && password.text.isNullOrBlank()){
                Toast.makeText(this,"Enter all Fields", Toast.LENGTH_SHORT).show()
            }else{
                getViewModel().loginEmployee(employeeid.text.toString(),password.text.toString())

            }
        }
    }

    override fun layoutRes(): Int {
        return R.layout.activity_login
    }

    override fun getViewModel(): LoginActivityViewModel {
        return loginActivityViewModel
    }
}
