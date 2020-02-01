package com.example.syncbyte.Views

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.format.DateFormat
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.syncbyte.Base.BaseActivity
import com.example.syncbyte.DI.Models.AttendenceModel
import com.example.syncbyte.DI.Models.EmployeeModel
import com.example.syncbyte.R
import com.example.syncbyte.Views.Adapter.AttendenceAdapter
import com.example.syncbyte.Views.ViewModels.DetailsActivityViewModel
import kotlinx.android.synthetic.main.activity_details.*
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.CellStyle
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DateFormatSymbols
import java.util.*
import javax.inject.Inject


class DetailsActivity : BaseActivity<DetailsActivityViewModel>() {

    lateinit var employeeModel: EmployeeModel
    lateinit var attendencelist: List<AttendenceModel>
    @Inject
    lateinit var detailsActivityViewModel: DetailsActivityViewModel

    @Inject
    lateinit var attendenceAdapter: AttendenceAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         employeeModel = intent.getSerializableExtra("employee")as EmployeeModel
        getViewModel().getAllAttendence(employeeModel.employeeId)
        attendencerv.layoutManager = LinearLayoutManager(this)
        attendencerv.adapter = attendenceAdapter

        attendenceAdapter.setListener(object :AttendenceAdapter.OnItemDeleteListener{
            override fun onItemDeleted(attendenceModel: AttendenceModel) {
                getViewModel().deleteAttendence(attendenceModel)
            }
        })
        setUpObservers()

        print.setOnClickListener {
            if (!hasPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
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

    private fun setUpObservers() {
        getViewModel().attendencedata.observe(this, Observer {

            attendenceAdapter.setList(it)
            attendencelist = it

        })
    }

    override fun layoutRes(): Int {
        return  R.layout.activity_details
    }

    override fun getViewModel(): DetailsActivityViewModel {
        return detailsActivityViewModel
    }

    private fun createExcel(){

//        try{
//            val file =File( getVideoFilePath(this))
//            val wbSettings = WorkbookSettings()
//            wbSettings.locale = Locale("en", "EN")
//            val workbook: WritableWorkbook
//            workbook = Workbook.createWorkbook(file, wbSettings)
//            val sheet = workbook.createSheet("attendence_{${employeeModel.name}}", 0)
//            // column and row
//            // column and row
//            sheet.addCell(Label(0, 0, "Entry Time"))
//            sheet.addCell(Label(1, 0, "Exit Time"))
//
//            for (i in 0 until attendencelist.size) {
//
//                sheet.addCell(Label(0, i+1, getFormattedDate(attendencelist[i].entryTime)))
//                sheet.addCell(Label(1, i+1, getFormattedDate(attendencelist[i].exitTime)))
//            }
//
//            workbook.write();
//            workbook.close();
//            Toast.makeText(getApplication(),
//                "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();
//
//            val excelIntent =  Intent(Intent.ACTION_VIEW);
//            excelIntent.setDataAndType(FileProvider.getUriForFile(this, applicationContext.packageName + ".provider"
//                ,file) , "application/vnd.ms-excel");
//            excelIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            try {
//                 startActivity(excelIntent);
//            } catch ( e:ActivityNotFoundException) {
//                  Toast.makeText(this,"No Application available to viewExcel", Toast.LENGTH_SHORT).show();
//            }
//
//
//        }catch (exception:Exception){
//
//            exception.printStackTrace()
//
//        }

        val file =File( getVideoFilePath(this))

        try {

            val workbook = HSSFWorkbook()
            val spreadSheet = workbook.createSheet("Attendence")
            val cs:CellStyle = workbook.createCellStyle();
            cs.setFillForegroundColor(HSSFColor.LIME.index)
            spreadSheet.setColumnWidth(0, (15 * 500));
            spreadSheet.setColumnWidth(1, (15 * 500));
            cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND)
                val row =spreadSheet.createRow(0)

            var c = row.createCell(0);
            c.setCellValue("Id : ");
            c.setCellStyle(cs);
            c = row.createCell(1);
            c.setCellValue("Entry time : ");
            c.setCellStyle(cs);

            c = row.createCell(2);
            c.setCellValue("Exit Time : " );
            c.setCellStyle(cs)

            for(i in 0 until attendencelist.size){
                val row = spreadSheet.createRow(i+1);
                var c = row.createCell(0)
                c.setCellValue("$i");
                c = row.createCell(1)
                c.setCellValue(getFormattedDate(attendencelist[i].entryTime));

                c = row.createCell(2);
                if(attendencelist[i].exitTime != null)
                    c.setCellValue(getFormattedDate(attendencelist[i].exitTime!!));
                else
                    c.setCellValue("-")

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

            } catch ( e:ActivityNotFoundException) {
                  Toast.makeText(this,"No Application available to viewExcel", Toast.LENGTH_SHORT).show();
            }



        }catch (e:Exception){
            Toast.makeText(this,"No Application available to viewExcel", Toast.LENGTH_SHORT).show();


        }

    }



    companion object{
        fun getFormattedDate(smsTimeInMilis: Long): String? {
            val postTime = Calendar.getInstance()
            postTime.timeInMillis = smsTimeInMilis
            val now = Calendar.getInstance()
            val symbols =
                DateFormatSymbols(Locale.getDefault())
            // OVERRIDE SOME symbols WHILE RETAINING OTHERS
            symbols.amPmStrings = arrayOf("am", "pm")
            val timeFormatString = "h:mm aa"
            return if (now[Calendar.DATE] == postTime[Calendar.DATE]) {
                "Today, " + DateFormat.format(
                    "h:mm:ss aa",
                    postTime
                ).toString().replace("AM", "am").replace("PM", "pm")
            } else if (now[Calendar.DATE] - postTime[Calendar.DATE] == 1) {
                "Yesterday, " + DateFormat.format(
                    "h:mm:ss aa",
                    postTime
                ).toString().replace("AM", "am").replace("PM", "pm")
            } else {
                DateFormat.format("MMMM dd , h:mm:ss aa", postTime).toString()
                    .replace("AM", "am").replace("PM", "pm")
            }
        }
        fun hasPermissions( context:Context,  permissions:Array<String>): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (i in 0 until permissions.size) {
                    if (ActivityCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                        return false;
                    }
                }
            }
            return true;
        }

    }

    @Throws(IOException::class)
    private fun getVideoFilePath(context:Context): String? {
        val folder =
            File(Environment.getExternalStorageDirectory().toString(), "attendence${employeeModel.employeeId}.xls")
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
}
