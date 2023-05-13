package com.example.schoolexeat


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.schoolexeat.databinding.ActivityExeatInfoBinding
import org.json.JSONObject

class ExeatInfo : AppCompatActivity() {
    private lateinit var binding:ActivityExeatInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExeatInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        StudentDetail.view = 1


        binding.btnSearch.setOnClickListener {
            val intent = Intent(this, SearchStudent::class.java)
            startActivity(intent)
            this.finish()
        }

        binding.tvStudentName.text = StudentDetail.student.studentName
        binding.tvHouse.text = StudentDetail.student.house
        binding.tvClass.text = StudentDetail.student.studentClass
        binding.tvGuardianContact.text = StudentDetail.student.guardianPhone
        getExeatHistory()
    }

    private fun getExeatHistory() {

        val url = StudentDetail.getUrl("getStudentExeat.php")
        //pbLoading.visibility = View.VISIBLE


        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest = @SuppressLint("SetTextI18n")
        object: StringRequest(Method.POST, url,
            { response ->

                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("Student List")
                var sta = ""
                var reas = ""
                var sDate = ""
                var eDate = ""
                for(i in 0 until jsonArray.length()) {
                    val jo = jsonArray.getJSONObject(i)
                    reas = jo.get("reason").toString()
                    sDate = jo.get("start_date").toString()
                    eDate = jo.get("end_date").toString()
                    sta = jo.get("status").toString()
                }
                binding.tvExeatStatusd.text = sta
                binding.tvExeatDetaild.text = "Reason being: '$reas, \nFrom $sDate  \nto $eDate"

            }, { }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["student_id"] = StudentDetail.student.studentId
                return map
            }
        }
        requestQueue.add(stringRequest)
    }
}