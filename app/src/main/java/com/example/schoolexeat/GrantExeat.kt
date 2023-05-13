package com.example.schoolexeat

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.schoolexeat.databinding.ActivityGrantExeatBinding
import java.util.*


class GrantExeat : AppCompatActivity() {
    private lateinit var binding: ActivityGrantExeatBinding
    private var mYear: Int = 0
    private  var mMonth: Int = 0
    private  var mDay: Int = 0
    private var stdate: String = ""
    private var enDate: String = ""
    private var exeatNum: String = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGrantExeatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initializing views
        binding.tvStudentName.text = StudentDetail.student.studentName
        binding.tvHouse.text = StudentDetail.student.house
        binding.tvClass.text = StudentDetail.student.studentClass
        binding.tvGuardianContact.text = StudentDetail.student.guardianPhone

        // Get Current Date
        var c: Calendar = Calendar.getInstance()
        val mYear0 = c.get(Calendar.YEAR)
        val mMonth0 = c.get(Calendar.MONTH)
        val mDay0 = c.get(Calendar.DAY_OF_MONTH)
        val sec = c.get(Calendar.SECOND)
        val min = c.get(Calendar.MINUTE)

        val curDate = "$mDay0-${mMonth0+1}-$mYear0"

        binding.tvStartDate.text = curDate
        stdate = "$mYear0-${mMonth0+1}-$mDay0"
        exeatNum = "$stdate$sec$min"

        binding.btnDate.setOnClickListener {
            c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { _, year, monthOfYear, dayOfMonth ->
                    binding.btnDate.text = "$dayOfMonth-${monthOfYear + 1}-$year"
                    enDate = "$year-${monthOfYear + 1}-$dayOfMonth"
                },
                    mYear,
                    mMonth,
                    mDay
            )
            datePickerDialog.datePicker.minDate = c.timeInMillis
            c.add(Calendar.YEAR, 0)
            c.add(Calendar.DAY_OF_MONTH, 2)
            datePickerDialog.datePicker.maxDate = c.timeInMillis
            datePickerDialog.show()
        }

        binding.btnSubmit.setOnClickListener {
            grantExeat()
        }
    }

    private fun grantExeat() {

        val url = StudentDetail.getUrl("insertExeat.php")
        //pbLoading.visibility = View.VISIBLE

        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest = object: StringRequest(Method.POST, url,
            { response ->
                Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT ).show()
                this.finish()
            }, { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT ).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String>{
                val map = HashMap<String, String>()
                map["student_id"] = StudentDetail.student.studentId
                map["exeat_number"] = exeatNum
                map["reason"] = binding.reasonEdt.text.toString()
                map["start_date"] = stdate
                map["end_date"] = enDate
                map["status"] = "On Exeat"
                return map
            }
        }
        requestQueue.add(stringRequest)
    }
}