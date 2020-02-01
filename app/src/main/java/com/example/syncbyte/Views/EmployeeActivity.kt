package com.example.syncbyte.Views

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.example.syncbyte.Base.BaseActivity
import com.example.syncbyte.DI.Models.AttendenceModel
import com.example.syncbyte.DI.Models.EmployeeModel
import com.example.syncbyte.R
import com.example.syncbyte.Views.ViewModels.EmployeeActivityViewModel
import kotlinx.android.synthetic.main.activity_employee.*
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.CellStyle
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class EmployeeActivity : BaseActivity<EmployeeActivityViewModel>() {
    lateinit var employeesList: List<EmployeeModel>
    lateinit var employeeModel: EmployeeModel

    var attendenceId:Long = 0;
    @Inject
    lateinit var employeeActivityViewModel: EmployeeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        employeeModel = intent.getSerializableExtra("employee") as EmployeeModel
        getViewModel().getAllEmployees()

        setUpListeners()
        setUpObsevers()
    }

    private fun setUpObsevers() {
        getViewModel().employees.observe(this, Observer {
            employeesList = it
        })
    }

    private fun setUpListeners() {
        checkswitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                val attendenceModel = AttendenceModel(entryTime = System.currentTimeMillis(),employeeId = employeeModel.employeeId)
                attendenceId = getViewModel().checkIn(attendenceModel)
            }else{
                getViewModel().checkOut(attendenceId,System.currentTimeMillis())
            }
        }

        edit.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java)
                .putExtra("employee",employeeModel))
            finish()
        }
        view.setOnClickListener {
            startActivity(Intent(this,DetailsActivity::class.java)
                .putExtra("employee",employeeModel))
        }

        getemployeeslist.setOnClickListener {
            if (!DetailsActivity.hasPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
            }else{
                createExcel()
            }
        }

    }

    override fun layoutRes(): Int {
        return R.layout.activity_employee
    }

    override fun getViewModel(): EmployeeActivityViewModel {
        return employeeActivityViewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        if(attendenceId!=0L){
            getViewModel().checkOut(attendenceId,System.currentTimeMillis())

        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
        if(attendenceId!=0L){
            getViewModel().checkOut(attendenceId,System.currentTimeMillis())

        }
    }

    private fun createExcel(){



        val file = File( getEmployeeFilePath(this))

        try {

            val workbook = HSSFWorkbook()
            val spreadSheet = workbook.createSheet("Attendence")
            val cs: CellStyle = workbook.createCellStyle();
            cs.setFillForegroundColor(HSSFColor.LIME.index)
            spreadSheet.setColumnWidth(0, (15 * 500));
            spreadSheet.setColumnWidth(1, (15 * 500));
            cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND)
            val row =spreadSheet.createRow(0)

            var c = row.createCell(0);
            c.setCellValue("Employee Id : ");
            c.setCellStyle(cs);
            c = row.createCell(1);
            c.setCellValue("Name : ");
            c.setCellStyle(cs);

            c = row.createCell(2);
            c.setCellValue("Email : " );
            c.setCellStyle(cs)

            c = row.createCell(3);
            c.setCellValue("Dob: " );
            c.setCellStyle(cs)

            c = row.createCell(4);
            c.setCellValue("Phone : " );
            c.setCellStyle(cs)

            c = row.createCell(5);
            c.setCellValue("Password : " );
            c.setCellStyle(cs)



            for(i in 0 until employeesList.size){
                val row = spreadSheet.createRow(i+1);
                var c = row.createCell(0)
                c.setCellValue(employeesList[i].employeeId)



                c = row.createCell(1)
                c.setCellValue(employeesList[i].name);

                c = row.createCell(2);
                c.setCellValue(employeesList[i].email);

                c = row.createCell(3);
                c.setCellValue(employeesList[i].dob);

                c = row.createCell(4);
                c.setCellValue(employeesList[i].phone);

                c = row.createCell(5);
                c.setCellValue(employeesList[i].password);

            }



            val os =  FileOutputStream(file);
            workbook.write(os)

            Toast.makeText(this,
                "Data Exported in a Excel Sheet and Saved at {${file.absolutePath}}", Toast.LENGTH_SHORT).show()

            val excelIntent =  Intent(Intent.ACTION_VIEW);
            excelIntent.setDataAndType(Uri.fromFile(file) , "application/vnd.ms-excel");
            excelIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                startActivity(excelIntent);

            } catch ( e: ActivityNotFoundException) {
                Toast.makeText(this,"No Application available to viewExcel", Toast.LENGTH_SHORT).show();
            }



        }catch (e:Exception){
            Toast.makeText(this,"No Application available to viewExcel", Toast.LENGTH_SHORT).show();


        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(
                "",
                "Permission: " + permissions[0] + "was " + grantResults[0]
            )

            createExcel()

            //resume tasks needing this permission
        }
    }

    private fun getEmployeeFilePath(context: Context): String? {
        val folder =
            File(Environment.getExternalStorageDirectory().toString(), "employees.xls")
//         folder.mkdirs()
//
//
//        //Save the path as a string value
//        //Save the path as a string value
//        val extStorageDirectory = folder.absolutePath
        val file = File(
            getExternalFilesDir("attendence"),
            "attendence${employeeModel.employeeId}.xls"
        )

        if (!folder.exists()) {
            folder.createNewFile()
        }
        return folder.absolutePath

    }



}
