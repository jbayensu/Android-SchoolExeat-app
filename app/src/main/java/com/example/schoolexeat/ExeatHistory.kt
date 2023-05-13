package com.example.schoolexeat

//noinspection SuspiciousImport
import android.R
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.stat
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.schoolexeat.adapters.ExeatHistoryAdapter
import com.example.schoolexeat.databinding.ActivityExeatHistoryBinding
import com.example.schoolexeat.data.Exeat
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ExeatHistory : AppCompatActivity() {
    private lateinit var binding: ActivityExeatHistoryBinding
    private lateinit var exeatAdapter: ExeatHistoryAdapter
    private  var exeatList: ArrayList<Exeat> = ArrayList()
    private var years = arrayOf("2022", "2023")
    private var months = arrayOf("January", "February", "March", "April", "May", "June", "July",
                                "August", "September", "October", "November", "December")
    private var countGranted = 0
    private var countRevoked = 0;

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExeatHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.tvStudentName.text = StudentDetail.student.studentName
        binding.tvHouse.text = StudentDetail.student.house
        binding.tvClass.text = StudentDetail.student.studentClass
        binding.tvGuardianContact.text = StudentDetail.student.guardianPhone

        getExeatHistory()
        exeatAdapter = ExeatHistoryAdapter(exeatList)
        val c: Calendar = Calendar.getInstance()
        val mYear0 = c.get(Calendar.YEAR)
        val mMonth0 = c.get(Calendar.MONTH)

        val yearArrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, years)
        yearArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_item)
        with(binding.spYear){
            adapter = yearArrayAdapter
            setSelection(yearArrayAdapter.getPosition(mYear0.toString()), true)
        }


        binding.spYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                exeatAdapter.filter.filter(binding.spYear.selectedItem.toString())
            }

        }


        val monthsArrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, months)
        monthsArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_item)
        with(binding.spMonth){
            adapter = monthsArrayAdapter
            setSelection(mMonth0, true)
        }

        binding.spMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.spMonth.setSelection(monthsArrayAdapter.getPosition(mMonth0.toString()))
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                exeatAdapter.filter.filter(setMonth(binding.spMonth.selectedItem.toString()))
            }

        }


        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rvExeatDetails.layoutManager = layoutManager

    }




    private fun getExeatHistory() {
//        Toast.makeText(this, binding.spMonth.selectedItem.toString(), Toast.LENGTH_SHORT).show()
        val mUrl = StudentDetail.getUrl("getStudentExeat.php")
        //pbLoading.visibility = View.VISIBLE

        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest = object: StringRequest(Method.POST, mUrl,
            { response ->

                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("Student List")

                for(i in 0 until jsonArray.length()){
                    val jo = jsonArray.getJSONObject(i)
                    val id = jo.get("exeat_id").toString()
                    val reas = jo.get("reason").toString()
                    val sDate = jo.get("start_date").toString()
                    val eDate = jo.get("end_date").toString()
                    val sta = jo.get("status").toString()
                    val ex = Exeat(id, sDate, eDate,reas, "", sta)
                    exeatList.add(ex)
                    countGranted++
                    if(sta != "On Exeat"){
                        countRevoked +=1
                    }

                }
                exeatAdapter = ExeatHistoryAdapter(exeatList)
                binding.rvExeatDetails.adapter = exeatAdapter
                binding.tvTotalGranted.text = countGranted.toString()
                binding.tvTotalRevokedExpired.text = countRevoked.toString()

            }, { error -> }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["student_id"] = StudentDetail.student.studentId
                return map
            }
        }
        requestQueue.add(stringRequest)
    }

    private fun setMonth(month:String):String{
        val mon = when(month){
            "January"-> "01"
            "February"-> "02"
            "March"-> "03"
            "April"-> "04"
            "May"-> "05"
            "June"-> "06"
            "July"-> "07"
            "August"-> "08"
            "September"-> "09"
            "October"-> "10"
            "November"-> "11"
            else -> "12"
        }

        return mon
    }

}