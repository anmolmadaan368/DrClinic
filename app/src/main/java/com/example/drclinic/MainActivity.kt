package com.example.drclinic

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private var REQUEST_CODE = 100
    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener
    private var finalDate = ""
    private var finalTime = ""
    lateinit var myCalendar: Calendar
    private val pickFromGallery:Int = 101
    lateinit var uri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtSetDate.setOnClickListener(this)
        edtSetTime.setOnClickListener(this)
        imgCancelDate.setOnClickListener(this)
        imgCancelTime.setOnClickListener(this)


        imageView.setOnClickListener {
            openGalleryForImage()
        }

        btnAttach.setOnClickListener {
            openFolder()
        }


        btnSend.setOnClickListener {
            val value = editName.text.toString().trim()
            val value1 = editDOB.text.toString().trim()
            val value2 = editGender.text.toString().trim()
            val value3 = editDepartment.text.toString().trim()
            val value4 = editAddress.text.toString().trim()
            val value5 = edtSetDate.text.toString().trim()
            val value6 = edtSetTime.text.toString().trim()
            val bundle = Bundle()
            bundle.putString("value", value)
            bundle.putString("value1", value1)
            bundle.putString("value2", value2)
            bundle.putString("value3", value3)
            bundle.putString("value4", value4)
            bundle.putString("value5", value5)
            bundle.putString("value6", value6)
            val intent = Intent(this@MainActivity, ShowActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)

        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK && requestCode==REQUEST_CODE){
            imageView.setImageURI(data?.data)
        }
        if (requestCode == pickFromGallery && resultCode == RESULT_OK) {
            if (data != null) {
                uri = data.data!!
            }
            txtAttach.text = uri.lastPathSegment
            txtAttach.visibility = View.VISIBLE
        }

    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onClick(view: View?) {

        when (view!!.id) {
            R.id.edtSetDate -> {
                dateAndTime()
                setDate()
            }
            R.id.edtSetTime -> {
                dateAndTime()
                setTime()
            }
            R.id.imgCancelDate -> {
                edtSetDate.setText("")
                finalDate = ""
                imgCancelDate.visibility = View.GONE
                if (relativeLayoutTime.visibility == View.VISIBLE) {
                    relativeLayoutTime.visibility = View.GONE
                    edtSetTime.setText("")
                    finalTime = ""
                    imgCancelTime.visibility = View.GONE
                }

            }
            R.id.imgCancelTime -> {
                edtSetTime.setText("")
                finalTime = ""
                imgCancelTime.visibility = View.GONE
            }

        }

    }

    private fun dateAndTime() {

        myCalendar = Calendar.getInstance()

        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabelDate()
        }

        timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            myCalendar.set(Calendar.MINUTE, minute)
            updateLabelTime()
        }

    }

    /**
     * @DatePickerDialog for selecting date
     * */
    private fun setDate() {

        val datePickerDialog = DatePickerDialog(this, dateSetListener, myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()

    }


    /**
     * @TimePickerDialog for selecting time
     * */
    private fun setTime() {
        val timePickerDialog = TimePickerDialog(this, timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY),
            myCalendar.get(Calendar.MINUTE), false)
        timePickerDialog.show()
    }

    /**
     * UI Update of time
     * */
    private fun updateLabelTime() {

        val myFormat = "HH:mm"  // HH:mm:ss
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        finalTime = sdf.format(myCalendar.time)


        val myFormat2 = "h:mm a"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.US)
        edtSetTime.setText(sdf2.format(myCalendar.time))

        imgCancelTime.visibility = View.VISIBLE
    }


    /**
     * UI Update of time
     * */
    private fun updateLabelDate() {

        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        finalDate = sdf.format(myCalendar.time)


        val myFormat2 = "EEE, d MMM yyyy"
        val sdf2 = SimpleDateFormat(myFormat2, Locale.US)
        edtSetDate.setText(sdf2.format(myCalendar.time))

        relativeLayoutTime.visibility = View.VISIBLE
        imgCancelDate.visibility = View.VISIBLE
    }

    private fun openFolder() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra("return-data", true)
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), pickFromGallery)
    }




}