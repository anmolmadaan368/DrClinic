package com.example.drclinic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        val bundle = intent.extras
        if(bundle!=null){
            val value = bundle.getString("value")
            val value1 = bundle.getString("value1")
            val value2 = bundle.getString("value2")
            val value3 = bundle.getString("value3")
            val value4 = bundle.getString("value4")
            val value5 = bundle.getString("value5")
            val value6 = bundle.getString("value6")
            editName.text = value
            editDOB.text = value1
            editGender.text = value2
            editGender.text = value2
            editDepartment.text = value3
            editAddress.text = value4
            edtSetDate.text = value5
            edtSetTime.text = value6
        }
    }
}